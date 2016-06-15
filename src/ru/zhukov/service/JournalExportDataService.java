package ru.zhukov.service;

import ru.zhukov.domain.JournalExportDetail;
import ru.zhukov.domain.JournalExportHeader;
import ru.zhukov.repository.JournalExportRepository;

import java.util.List;

/**
 * Created by Gukov on 18.04.2016.
 */
public class JournalExportDataService {

    private JournalExportRepository exportRepository;


    public JournalExportDataService(JournalExportRepository repository){
        this.exportRepository = repository;
    }

    public List<JournalExportHeader> allJournal(String dimension) {

        return  exportRepository.loadJournal(dimension);

    };

    public List<JournalExportDetail> listDetailJournal(String parentJournal){
        return exportRepository.loadDetailJournal(parentJournal);
    }

    public void deleteJournal(String parentRecId,String dimension){
        exportRepository.delete(parentRecId,dimension);
    }

}
