package ru.zhukov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zhukov.domain.KindPay;

import java.util.List;

public interface KindPayRepository  extends JpaRepository<KindPay,String>{

    List<KindPay> queryKindPayByCodeIsLessThan(String code);

}
