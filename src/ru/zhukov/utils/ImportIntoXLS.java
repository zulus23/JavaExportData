package ru.zhukov.utils;


import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTable;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumn;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumns;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableStyleInfo;
import org.springframework.format.annotation.DateTimeFormat;
import ru.zhukov.domain.AccountRecord;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by Gukov on 05.04.2016.
 */
public class ImportIntoXLS {
    Workbook wb;
    XSSFSheet sheet;
    public void CreateXLS(List<AccountRecord> accountRecordList){
         wb = new XSSFWorkbook();
        sheet = (XSSFSheet) wb.createSheet();



//        //Style configurations
//        CTTableStyleInfo style = cttable.addNewTableStyleInfo();
//        style.setName("TableStyleMedium2");
//        style.setShowColumnStripes(false);
//        style.setShowRowStripes(true);
//
//        //Set which area the table should be placed in
//        AreaReference reference = new AreaReference(new CellReference(0, 0),
//                new CellReference(2,2));
//        cttable.setRef(reference.formatAsString());
//        cttable.setId(1);
//        cttable.setName("Test");
//        cttable.setTotalsRowCount(1);
//

//        columns.setCount(3);
        CTTableColumn column;
        XSSFRow row;
        XSSFCell cell;
//        for(int i=0; i<3; i++) {
//            //Create column
//            column = columns.addNewTableColumn();
//            column.setName("Column");
//            column.setId(i+1);
//            //Create row
//            row = sheet.createRow(i);
//            for(int j=0; j<3; j++) {
//                //Create cell
//                cell = row.createCell(j);
//                if(i == 0) {
//                    cell.setCellValue("Column"+j);
//                } else {
//                    cell.setCellValue("0");
//                }
//            }
//        }
//
        int i = 1;
        for (AccountRecord account : accountRecordList) {

             row = sheet.createRow(i++);
             cell  = row.createCell(1);
             cell.setCellValue(account.getDepartment());
             cell  = row.createCell(2);
             cell.setCellValue(account.getEmployee());
            cell  = row.createCell(3);
            cell.setCellValue(account.getDebit());
            cell  = row.createCell(4);
            cell.setCellValue(account.getCredit());
            cell  = row.createCell(4);
            cell.setCellValue(account.getSumma());

        }


        try(FileOutputStream fileOut = new FileOutputStream(String.format("%s_Provodki.xlsx", LocalTime.now().format(DateTimeFormatter.ofPattern("hh_mm_ss") )))){
           wb.write(fileOut);

        }catch (IOException ex){
             ex.printStackTrace();
        }
        finally {

        }
    }



}
