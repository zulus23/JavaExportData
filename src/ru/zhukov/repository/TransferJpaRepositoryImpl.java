package ru.zhukov.repository;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import ru.zhukov.domain.TransferAccount;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gukov on 19.04.2017.
 */
@Component
public class TransferJpaRepositoryImpl implements TransferJpaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TransferAccount> createAccountsForTransfer(@Param("v_year") int year, @Param("v_month") int month){
           List<TransferAccount> accounts = new ArrayList<>();

           StoredProcedureQuery procedureQuery = entityManager.createNamedStoredProcedureQuery("createAccountsForTransfer");

           procedureQuery.setParameter("v_year",year);
           procedureQuery.setParameter("v_month",month);
           accounts.addAll(procedureQuery.getResultList());



         return accounts;
    };


}
