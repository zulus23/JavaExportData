package ru.zhukov.repository;

import org.springframework.data.repository.query.Param;
import ru.zhukov.domain.TransferAccount;

import java.util.List;

/**
 * Created by Gukov on 21.04.2017.
 */
public interface TransferJpaRepository {
    List<TransferAccount> createAccountsForTransfer(@Param("v_year") int year, @Param("v_month") int month);
}
