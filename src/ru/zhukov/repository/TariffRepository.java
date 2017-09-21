package ru.zhukov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zhukov.domain.Tariff;
import ru.zhukov.domain.TariffId;

public interface TariffRepository extends JpaRepository<Tariff,TariffId>{
}
