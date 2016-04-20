package ru.zhukov.repository;

import ru.zhukov.domain.JournalExportDetail;
import ru.zhukov.domain.JournalExportHeader;

import java.util.List;

/**
 * Created by Gukov on 18.04.2016.
 */
public interface JournalExportRepository {


    List<JournalExportHeader>  loadJournal(String dimension);

    void delete(String parentRecId);

    List<JournalExportDetail> loadDetailJournal(String parentJournal);
}
