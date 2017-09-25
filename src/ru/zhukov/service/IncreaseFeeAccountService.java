package ru.zhukov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zhukov.domain.IncreaseKindPay;
import ru.zhukov.domain.KindPay;
import ru.zhukov.repository.IncreaseKindPayRepository;
import ru.zhukov.repository.KindPayRepository;

import java.util.List;

@Service
public class IncreaseFeeAccountService {

    @Autowired
    private KindPayRepository kindPayRepository;
    @Autowired
    private IncreaseKindPayRepository increaseKindPayRepository;


    public List<KindPay> kindPayList(){
        return kindPayRepository.queryKindPayByCodeIsLessThan();
    }
    public List<IncreaseKindPay> increaseKindPayList(){
        return increaseKindPayRepository.findAll();
    }



}
