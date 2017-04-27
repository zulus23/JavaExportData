package ru.zhukov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zhukov.domain.SetupAccountForTransfer;

/**
 * Created by Gukov on 17.04.2017.
 */
@Repository
public interface TransferAccountJpaRepository extends JpaRepository<SetupAccountForTransfer,Integer> {
}
