package ru.zhukov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import ru.zhukov.domain.IncreaseKindPay;
import ru.zhukov.domain.KindPay;
import ru.zhukov.exeption.CanNotSaveException;
import ru.zhukov.repository.IncreaseKindPayRepository;
import ru.zhukov.repository.KindPayRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class IncreaseFeeAccountService {

    @Autowired
    private KindPayRepository kindPayRepository;
    @Autowired
    private IncreaseKindPayRepository increaseKindPayRepository;


    public List<KindPay> kindPayList(){
        return kindPayRepository.queryKindPayByCodeIsLessThan();
    }
    @Transactional
    public List<IncreaseKindPay> increaseKindPayList(){
        increaseKindPayRepository.flush();
        return increaseKindPayRepository.findAll();
    }

    public CompletableFuture<IncreaseKindPay> saveIncreaseKindPay( IncreaseKindPay increaseKindPay){
        return CompletableFuture.supplyAsync( () -> {
            try {
                increaseKindPayRepository.saveAndFlush(increaseKindPay);

            }catch (TransactionSystemException ex){
                throw new CanNotSaveException(ex);
            }
            return increaseKindPay;
        });
    }

    public CompletableFuture<IncreaseKindPay> deleteIncreaseKindPay( IncreaseKindPay increaseKindPay){
        return CompletableFuture.supplyAsync(()->{
            try {
                increaseKindPayRepository.delete(increaseKindPay);
            }catch (TransactionSystemException ex){
                throw new CanNotSaveException(ex);
            }
            return increaseKindPay;
        });
    }




}
