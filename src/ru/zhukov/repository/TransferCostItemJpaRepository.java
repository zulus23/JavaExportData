package ru.zhukov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zhukov.domain.SetupCostItemTransfer;

/**
 * Created by Gukov on 27.04.2017.
 */
@Repository
public interface TransferCostItemJpaRepository  extends JpaRepository<SetupCostItemTransfer,Long>{



}
