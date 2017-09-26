package ru.zhukov.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zhukov.domain.Employee;
import ru.zhukov.domain.Tariff;
import ru.zhukov.domain.TariffId;
import ru.zhukov.repository.EmployeeRepository;
import ru.zhukov.repository.TariffRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TariffIncreaseService {

    @Autowired
    private EmployeeRepository employeeRepository;


    @Autowired
    private TariffRepository tariffRepository;

    public List<Employee> employeeListNeedIncreaseTarif(){
        List<Employee>  employees = employeeRepository.findBySalaryBetweenAndDateDissmissalIsNullAndTariffIsNotNull(new BigDecimal(1.0),new BigDecimal(1000.0));
        List<Employee> employeeIncrease = employees.stream().filter(e -> e.getTariff().getRank() != e.getCurrentRank()).collect(Collectors.toList());
        return  employeeIncrease;
    }
    public List<Employee> employeeListNeedIncreaseTarif(LocalDateTime dateMonthCalculate){
        String category = "Рабочие";
        LocalDateTime localDateBegin = LocalDateTime.from(dateMonthCalculate).with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime localDateEnd = LocalDateTime.from(dateMonthCalculate).with(TemporalAdjusters.lastDayOfMonth());
        List<Employee>  employees = employeeRepository.findByCategory(category, Date.from(localDateBegin.atZone(ZoneId.systemDefault()).toInstant())
                                                                              ,Date.from(localDateEnd.atZone(ZoneId.systemDefault()).toInstant()));
        List<Employee> employeeIncrease = employees.stream().filter(e -> e.getTariff().getRank() < e.getCurrentRank()).collect(Collectors.toList());
        employeeIncrease.stream().forEach(e -> {
            TariffId tariffId = new TariffId(e.getTariff().getId().getNumber(),e.getCurrentRank());
            Tariff tariffNext = tariffRepository.findOne(tariffId);
            e.setNextTariff(tariffNext);
        });
        return  employeeIncrease;
    }

}
