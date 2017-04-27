package ru.zhukov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zhukov.domain.SetupDepartmentForTransfer;

/**
 * Created by Zhukov on 10.04.2017.
 */
@Repository

public interface TransferDepartmentJpaRepository extends JpaRepository<SetupDepartmentForTransfer,Long> {



}
