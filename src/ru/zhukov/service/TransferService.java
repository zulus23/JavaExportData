package ru.zhukov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zhukov.domain.TransferAccount;
import ru.zhukov.repository.TransferJpaRepository;

import java.util.List;

/**
 * Created by Gukov on 19.04.2017.
 */
@Service
public class TransferService {

    @Autowired
    private TransferJpaRepository repository;



    public List<TransferAccount> getAllTransferAccount(int year, int month){
         return  repository.createAccountsForTransfer(year,month);
    }

}
