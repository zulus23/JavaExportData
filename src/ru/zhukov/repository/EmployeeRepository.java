package ru.zhukov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.zhukov.domain.Employee;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public interface EmployeeRepository extends JpaRepository<Employee,String> {

       List<Employee> findByNameIsContaining(String name);

       List<Employee> findBySalaryBetweenAndDateDissmissalIsNullAndTariffIsNotNull(BigDecimal salary, BigDecimal salaryEnd);

       @Query("select e from Employee  e  join e.category c  join  e.department d  join  e.tariff t where c.group in (?1) and " +
               "(e.dateDissmissal is null or e.dateDissmissal between ?2 and ?3) and t.id.number is not null ")

       List<Employee> findByCategory(String categories, Date dateStart, Date dateEnd);

       Employee findByTabelNumber(String tabel);


}
