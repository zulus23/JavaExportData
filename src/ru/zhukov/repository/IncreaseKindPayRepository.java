package ru.zhukov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zhukov.domain.IncreaseKindPay;
import ru.zhukov.domain.KindPay;

public interface IncreaseKindPayRepository extends JpaRepository<IncreaseKindPay,String> {

}
