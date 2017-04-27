package ru.zhukov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zhukov.domain.SetupCostItemTransfer;
import ru.zhukov.repository.TransferCostItemJpaRepository;

import java.util.List;

/**
 * Created by Gukov on 27.04.2017.
 */
@Service
public class TransferCostItemService  {

    @Autowired
    private TransferCostItemJpaRepository costItemJpaRepository;


    public List<SetupCostItemTransfer> findAll(){
        return  costItemJpaRepository.findAll();
    }



}
