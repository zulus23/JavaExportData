package ru.zhukov.service;

import ru.zhukov.domain.Employee;
import ru.zhukov.domain.KindPay;

import java.time.LocalDateTime;
import java.util.List;

public interface TariffIncreaseServiceable {
    List<Employee> employeeListNeedIncreaseTarif();
    List<Employee> employeeListNeedIncreaseTarif(LocalDateTime dateMonthCalculate);
    List<Employee> employeeNotNeedIncreaseTarif(LocalDateTime dateMonthCalculate);
    List<KindPay>  selectKindPayLess500();
    List<KindPay> listKindPayIncreaseFee();
    void makeIncreaseFeeByEmployee(List<Employee> employeeIncreaseFee,KindPay kindPay,LocalDateTime workDate);
    void makeIncreaseFeeByEmployee(List<Employee> employeeIncreaseFee,LocalDateTime workDate);
    void calculateIncreaseFee(List<Employee> employeeIncreaseFee,LocalDateTime calculateDate);
    List<KindPay> distinctPayIntoIncreaseFee();
}
