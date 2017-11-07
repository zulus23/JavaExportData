package ru.zhukov.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.zhukov.domain.Employee;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class CreateExcelFileForIncreaseFee {


    private final Path templateFile;
    private final Path fileOut;

    private Workbook workBook;

    public CreateExcelFileForIncreaseFee() {
        templateFile = Paths.get(".").toAbsolutePath().resolve(Paths.get("template/CalculateIncreaseFee.xlsx"));
        fileOut = Paths.get(".").toAbsolutePath().resolve(Paths.get(String.format("outxls/CalculateIncreaseFee_%s.xlsx",System.nanoTime())));
    }


    public Path generateReportFromData(List<Employee> employees){
        try(BufferedInputStream streamTemplate = new BufferedInputStream(new FileInputStream(templateFile.toFile()));
            BufferedOutputStream streamOutFile = new BufferedOutputStream(new FileOutputStream(fileOut.toFile()))){

            workBook = new XSSFWorkbook(streamTemplate);
            writeRow(workBook.getSheetAt(0),employees);
            workBook.write(streamOutFile);


        } catch (IOException ex){
            ex.printStackTrace();
        }

        return  fileOut;
    }


    public void writeRow(Sheet sheet,List<Employee> employees) {
        CellStyle centerStyle = workBook.createCellStyle();
                  centerStyle.setAlignment(CellStyle.ALIGN_CENTER);
                  centerStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);

        CellStyle leftCenterStyle = workBook.createCellStyle();
        leftCenterStyle.setAlignment(CellStyle.ALIGN_LEFT);
        leftCenterStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);

        CellStyle rightCenterStyle = workBook.createCellStyle();
        rightCenterStyle.setAlignment(CellStyle.ALIGN_RIGHT);
        rightCenterStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);


        int rowCount = 1;
        for( Employee employee: employees){
            Row row = Optional.ofNullable(sheet.getRow(rowCount)).orElseGet(() -> sheet.createRow( sheet.getLastRowNum()+1));
            Cell cell =  row.createCell(0);
            cell.setCellValue(employee.getTabelNumber());
            cell.setCellStyle(centerStyle);

            cell =  row.createCell(1);
            cell.setCellValue(employee.getFullName());
            cell.setCellStyle(leftCenterStyle);

            cell =  row.createCell(2);
            cell.setCellStyle(leftCenterStyle);
            cell.setCellValue(employee.getDepartmentName());

            cell = row.createCell(3);
            cell.setCellStyle(leftCenterStyle);
            cell.setCellValue(employee.getPositionName());

            cell = row.createCell(4);
            cell.setCellStyle(centerStyle);
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(employee.getCurrentRank());

            cell = row.createCell(5);
            cell.setCellStyle(centerStyle);
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(employee.getRankByTariff());

            cell = row.createCell(6);
            cell.setCellStyle(rightCenterStyle);
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(Optional.ofNullable(employee.getNextTariff()).map(v -> v.getSumma().doubleValue()).orElse(0.0));

            cell = row.createCell(7);
            cell.setCellStyle(rightCenterStyle);
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(Optional.ofNullable(employee.getTariff()).map(v-> v.getSumma().doubleValue()).orElse(0.0));

            cell = row.createCell(8);
            cell.setCellStyle(centerStyle);
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(Optional.ofNullable(employee.getCoefficient()).map(v -> v.doubleValue()).orElse(0.0));

            cell = row.createCell(9);
            cell.setCellStyle(rightCenterStyle);
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(Optional.ofNullable(employee.getIncreaseSummaFeeOne()).map(v -> v.doubleValue()).orElse(0.0));

            rowCount++;
        }



    }

}
