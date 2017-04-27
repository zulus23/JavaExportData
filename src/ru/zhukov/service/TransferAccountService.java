package ru.zhukov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zhukov.domain.SetupAccountForTransfer;
import ru.zhukov.repository.TransferAccountJpaRepository;

import java.util.List;

/**
 * Created by Gukov on 17.04.2017.
 */
@Service
public class TransferAccountService {
    @Autowired
    private TransferAccountJpaRepository repository;


    @Transactional(readOnly = true)
    public List<SetupAccountForTransfer> findAll(){
        return repository.findAll();
    }

    @Transactional
    public SetupAccountForTransfer save(SetupAccountForTransfer accountForTransfer){
        return repository.save(accountForTransfer);
    }


}
