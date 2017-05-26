package ru.zhukov.exeption;

/**
 * Created by Gukov on 10.05.2017.
 */
public class ExcelFileTransferException extends RuntimeException {

    private String name;
    public ExcelFileTransferException(String s) {
        super(s);
        this.name = s;
    }
}
