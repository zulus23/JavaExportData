package ru.zhukov.repository;

import ru.zhukov.domain.AccountRecord;
import ru.zhukov.domain.AccountRecordExport;
import ru.zhukov.dto.ExportJournal;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Gukov on 28.03.2016.
 */
public interface AccountRepository {

    List<AccountRecord> listAccountRecordsByMonthAndYear(int month, int year);

    void createAccountRecord();


    List<AccountRecordExport> listAccountRecordForExportByMonthAndYear(int month, int year);


    void exportAccountRecord(int month, int year, ExportJournal journal);



}
