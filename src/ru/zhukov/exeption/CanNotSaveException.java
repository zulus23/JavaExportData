package ru.zhukov.exeption;

import org.springframework.transaction.TransactionSystemException;

public class CanNotSaveException extends RuntimeException {
    public CanNotSaveException(TransactionSystemException ex) {
        super(ex);
    }
}
