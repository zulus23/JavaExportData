package ru.zhukov.utils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.zhukov.domain.TransferAccount;

import java.io.*;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/**
 * Created by Gukov on 18.04.2017.
 */
public class XLSFileTransferTo1c {

    private final  Path fileName;
    private Workbook workBook;
    private Sheet firstSheet;
    private List<TransferAccount> accountList;

    public XLSFileTransferTo1c(Path fileName, List<TransferAccount> accountList) {

        this.fileName = fileName;
        this.accountList = accountList;
    }

    public  Sheet openXlsFile(){
      try(BufferedInputStream stream = new BufferedInputStream(new FileInputStream(fileName.toFile()))){

          workBook = new XSSFWorkbook(stream);
          firstSheet = workBook.getSheetAt(0);



          return firstSheet;
      }catch(IOException ex){
          ex.printStackTrace();
      }
      return null;
    }

    public void writeFile(Path pathOut){
        try(BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(pathOut.toFile()))){

           writeRow(firstSheet);
           workBook.write(stream);

        }catch(IOException ex){

        }
    }


    public Row firstRowForWriteXLSFile(Sheet sheet){

        return sheet.getRow(0);
    }



    private void createFile(){

    }


    public void writeRow(Sheet sheet) {

        int rowCount = 1;
        for( TransferAccount account: accountList){
          Row row = Optional.ofNullable(sheet.getRow(rowCount)).orElseGet(() -> sheet.createRow( sheet.getLastRowNum()+1));
          row.createCell(0).setCellValue(account.getNumberOrder());
          row.createCell(1).setCellValue(account.getAccountDebit());
          row.createCell(2).setCellValue(account.getDepartmentDebit());
          row.createCell(3).setCellValue(account.getAnalyticsOneDebit());
          row.createCell(4).setCellValue(account.getAnalyticsTwoDebit());
          row.createCell(5).setCellValue(account.getAnalyticsThreeDebit());
          row.createCell(6).setCellValue(account.getAccountCredit());
          row.createCell(7).setCellValue(account.getDepartmentCredit());
          row.createCell(8).setCellValue(account.getAnalyticsOneCredit());
          row.createCell(9).setCellValue(account.getAnalyticsTwoCredit());
          row.createCell(10).setCellValue(account.getAnalyticsThreeCredit());
          row.createCell(11).setCellValue(account.getSummaAccount());
          row.createCell(12).setCellValue(account.getSummaTaxDebit());
          row.createCell(15).setCellValue(account.getSummaTaxCredit());

            rowCount++;
         }



    }
}
