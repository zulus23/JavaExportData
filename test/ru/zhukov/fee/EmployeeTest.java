package ru.zhukov.fee;

import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import ru.zhukov.AbstractTest;
import ru.zhukov.domain.Employee;
import ru.zhukov.domain.Tariff;
import ru.zhukov.domain.TariffId;
import ru.zhukov.repository.EmployeeRepository;
import ru.zhukov.repository.TariffRepository;
import ru.zhukov.service.TariffIncreaseService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class EmployeeTest extends AbstractTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TariffIncreaseService increaseService;

    @Autowired
    private TariffRepository tariffRepository;

    @Test
    public void findEmployeeWithSallaryLessThan1000() throws Exception {
        List<Employee> employees = employeeRepository.findBySalaryBetweenAndDateDissmissalIsNullAndTariffIsNotNull( new BigDecimal(1.0), new BigDecimal(1000.0));
        System.out.println(employees.size());
        assertTrue(employees.size() > 0);

    }
    @Test
    public void findPeopleWhoNeedIncreaseSalary(){
        List<Employee> employees =  increaseService.employeeListNeedIncreaseTarif();
        assertTrue(employees.size() > 0);


    }




    @Test
    public void findNextRankForPay(){
        List<Employee> employees =  increaseService.employeeListNeedIncreaseTarif();
        Employee employee =  employees.get(0);

        Tariff tariff =  tariffRepository.findOne( employee.getTariff().getId());

        assertNotNull(tariff);
        TariffId tariffId = new TariffId(tariff.getId().getNumber(),employee.getCurrentRank());
        Tariff tariffNext = tariffRepository.findOne(tariffId);
        System.out.println("tariff.getSumma() = " + tariff.getSumma());
        System.out.println("tariffNext.getSumma() = " + tariffNext.getSumma());
        assertTrue(tariffNext.getSumma().doubleValue() > tariff.getSumma().doubleValue());
    }
    @Test
    public void findEmployeeByCategory(){
        LocalDateTime localDateBegin = LocalDateTime.of(2017, Month.SEPTEMBER,30,0,0,0).with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime localDateEnd = LocalDateTime.of(2017, Month.SEPTEMBER,30,0,0,0).with(TemporalAdjusters.lastDayOfMonth());
        List<Employee> employeeList =  employeeRepository.findByCategory(
                                                               "Рабочие",
                                                               Date.from(localDateBegin.atZone(ZoneId.systemDefault()).toInstant()) ,
                                                               Date.from(localDateEnd.atZone(ZoneId.systemDefault()).toInstant())
                                                               );
        assertTrue(employeeList.size() > 0);
    }




}