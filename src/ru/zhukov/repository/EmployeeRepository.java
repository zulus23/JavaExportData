package ru.zhukov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.zhukov.domain.Employee;

import java.math.BigDecimal;
import java.util.List;


public interface EmployeeRepository extends JpaRepository<Employee,String> {

       List<Employee> findByNameIsContaining(String name);

       List<Employee> findBySalaryBetweenAndDateDissmissalIsNullAndTariffIsNotNull(BigDecimal salary, BigDecimal salaryEnd);


}
