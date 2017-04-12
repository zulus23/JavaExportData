package ru.zhukov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zhukov.domain.SetupDepartmentForTransfer;
import ru.zhukov.repository.TransferJpaRepository;

import java.util.List;

/**
 * Created by Gukov on 12.04.2017.
 */
@Service
public class TransferDepartmentService {

    @Autowired
    private TransferJpaRepository repository;

    @Transactional(readOnly = true)
    public List<SetupDepartmentForTransfer> findAll(){
        return repository.findAll();
    }
}
