package ru.zhukov.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zhukov.domain.Employee;
import ru.zhukov.repository.EmployeeRepository;
import ru.zhukov.repository.TariffRepository;

import java.math.BigDecimal;
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


}
