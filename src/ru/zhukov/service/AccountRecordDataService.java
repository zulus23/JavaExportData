package ru.zhukov.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.zhukov.domain.AccountRecord;
import ru.zhukov.repository.AccountRepository;

/**
 * Created by Gukov on 29.03.2016.
 */
public class AccountRecordDataService {
  private AccountRepository accountRepository;


  public AccountRecordDataService(AccountRepository repository){
      this.accountRepository = repository;
  }

  public ObservableList<AccountRecord> accountRecordList() {
        ObservableList<AccountRecord> accountRecords = FXCollections.observableArrayList();
        accountRecords.addAll(accountRepository.listAccountRecords());

        return  accountRecords;
    }

}
