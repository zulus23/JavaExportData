package ru.zhukov.repository;

import ru.zhukov.domain.AccountRecord;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Gukov on 28.03.2016.
 */
public interface AccountRepository {

    List<AccountRecord> listAccountRecordsByMonthAndYear(int month, int year);

    void createAccountRecord();


    List<AccountRecord> listAccountRecordForExportByMonthAndYear(int month, int year);




}
