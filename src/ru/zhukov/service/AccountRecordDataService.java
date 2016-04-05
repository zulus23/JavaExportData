package ru.zhukov.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import ru.zhukov.domain.AccountRecord;
import ru.zhukov.repository.AccountRepository;

import java.util.List;

/**
 * Created by Gukov on 29.03.2016.
 */

public class AccountRecordDataService {

  @Autowired
  private AccountRepository accountRepository;


  public AccountRecordDataService(AccountRepository repository){
      this.accountRepository = repository;
  }

  public ObservableList<AccountRecord> accountRecordListByMonthAndYear(int month, int year) {

        ObservableList<AccountRecord> accountRecords = FXCollections.observableArrayList();
        accountRecords.addAll(accountRepository.listAccountRecordsByMonthAndYear(month,year));

        return  accountRecords;
    }


  public List<AccountRecord> createAccountRecord(){
      return accountRepository.createAccountRecord();
  }
}
