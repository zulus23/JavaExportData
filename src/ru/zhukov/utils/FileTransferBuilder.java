package ru.zhukov.utils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.zhukov.domain.TransferAccount;
import ru.zhukov.exeption.ExcelFileTransferException;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by Gukov on 10.05.2017.
 */
public class FileTransferBuilder {
    private XLSFileTransferTo1c xlsFileTransferTo1c;


    public FileTransferBuilder(){
        this.xlsFileTransferTo1c =  new XLSFileTransferTo1c();
    }


    private class  XLSFileTransferTo1c implements  ExcelFileTransferable{
        private Path templateFile;
        private List<? extends TransferAccount> accountList;
        private Path fileOut;

        private Workbook workBook;


        private XLSFileTransferTo1c(){
             templateFile = Paths.get(".").toAbsolutePath().resolve(Paths.get("template/PayFromAit_To_1C.xlsx"));
             fileOut = Paths.get(".").toAbsolutePath().resolve(Paths.get(String.format("outxls/PayFromAit_To_1C_%s.xlsx",System.nanoTime())));
        }

        public Path getTemplateFile() {
            return templateFile;
        }

        public void setTemplateFile(Path templateFile) {
            this.templateFile = templateFile;
        }

        public List<? extends TransferAccount> getAccountList() {
            return accountList;
        }

        public void setAccountList(List<? extends TransferAccount> accountList) {
            this.accountList = accountList;
        }

        public Path getFileOut() {
            return fileOut;
        }

        public void setFileOut(Path fileOut) {
            this.fileOut = fileOut;
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
                row.createCell(13).setCellValue(account.getSummaPRDebit() );
                row.createCell(15).setCellValue(account.getSummaTaxCredit());

                rowCount++;
            }



        }



        @Override
        public Path createFile() {
            try(BufferedInputStream streamTemplate = new BufferedInputStream(new FileInputStream(templateFile.toFile()));
                BufferedOutputStream streamOutFile = new BufferedOutputStream(new FileOutputStream(fileOut.toFile()))){

                workBook = new XSSFWorkbook(streamTemplate);
                writeRow(workBook.getSheetAt(0));
                workBook.write(streamOutFile);


            } catch (IOException ex){
               ex.printStackTrace();
            }

            return  fileOut;
        }
    }



    public  FileTransferBuilder withDatasource(List<? extends TransferAccount> accountList){
        xlsFileTransferTo1c.setAccountList(accountList);
        return this;

    }


    public ExcelFileTransferable build(){
        if(Objects.isNull(xlsFileTransferTo1c.accountList)){
            throw new ExcelFileTransferException("Отсутствуют данные");
        }

        return  xlsFileTransferTo1c;
    }









}
