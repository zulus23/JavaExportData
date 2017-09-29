package ru.zhukov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zhukov.domain.EmployeeFee;

public interface EmployeeFeeRepository extends JpaRepository<EmployeeFee,String> {
}
