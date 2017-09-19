package ru.zhukov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.zhukov.domain.KindPay;


import java.util.List;

public interface KindPayRepository  extends JpaRepository<KindPay,String>{

    @Query("select k from KindPay k where k.code < '500' ")
    List<KindPay> queryKindPayByCodeIsLessThan();

}
