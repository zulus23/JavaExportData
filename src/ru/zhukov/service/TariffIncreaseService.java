package ru.zhukov.service;


import javafx.scene.control.TableView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zhukov.domain.*;
import ru.zhukov.repository.*;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class TariffIncreaseService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private KindPayRepository kindPayRepository;

    @Autowired
    private IncreaseKindPayRepository increaseKindPayRepository;

    @Autowired
    private EmployeeFeeRepository employeeFeeRepository;



    @Autowired
    private TariffRepository tariffRepository;

    public List<Employee> employeeListNeedIncreaseTarif(){
        List<Employee>  employees = employeeRepository.findBySalaryBetweenAndDateDissmissalIsNullAndTariffIsNotNull(new BigDecimal(1.0),new BigDecimal(1000.0));
        List<Employee> employeeIncrease = employees.stream().filter(e -> e.getTariff().getRank() != e.getCurrentRank()).collect(Collectors.toList());
        return  employeeIncrease;
    }
    public List<Employee> employeeListNeedIncreaseTarif(LocalDateTime dateMonthCalculate){
        Predicate<Employee> greatRank = e -> e.getTariff().getRank() < e.getCurrentRank();
        return getEmployees(dateMonthCalculate,greatRank );
    }

    public List<Employee> employeeNotNeedIncreaseTarif(LocalDateTime dateMonthCalculate){
        Predicate<Employee> greatRank = e -> e.getTariff().getRank() > e.getCurrentRank();
        return getEmployees(dateMonthCalculate,greatRank );
    }

    private List<Employee> getEmployees(LocalDateTime dateMonthCalculate, Predicate<Employee> selectRank) {
        String category = "Рабочие";
        LocalDateTime localDateBegin = LocalDateTime.from(dateMonthCalculate).with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime localDateEnd = LocalDateTime.from(dateMonthCalculate).with(TemporalAdjusters.lastDayOfMonth());
        List<Employee>  employees = employeeRepository.findByCategory(category, Date.from(localDateBegin.atZone(ZoneId.systemDefault()).toInstant())
                                                                              ,Date.from(localDateEnd.atZone(ZoneId.systemDefault()).toInstant()));
        List<Employee> employeeIncrease = employees.stream()
                                                   .filter(selectRank)
                                                   .sorted(Comparator.comparing(Employee::getFullName)).collect(Collectors.toList());
        employeeIncrease.stream().forEach(e -> {
            TariffId tariffId = new TariffId(e.getTariff().getId().getNumber(),e.getCurrentRank());
            //Tariff tariffNext = tariffRepository.findOne(tariffId);
            Optional.ofNullable(tariffRepository.findOne(tariffId))
                    .ifPresent(t -> {
                        e.setNextTariff(t);
                        e.setCoefficient(t.getSumma().divide(e.getSalary(), BigDecimal.ROUND_HALF_UP));
                    });

        });
        return  employeeIncrease;
    }

    public List<KindPay>  selectKindPayLess500(){
        return kindPayRepository.queryKindPayByCodeIsLessThan();
    }

    public List<KindPay> listKindPayIncreaseFee(){
        return increaseKindPayRepository.findAll().stream().map(e -> e.getKindPay()).collect(Collectors.toList());
    }


    @Transactional
    public void makeIncreaseFeeByEmployee(List<Employee> employeeIncreaseFee,KindPay kindPay,LocalDateTime workDate) {
        employeeIncreaseFee.stream().forEach(e ->{
            EmployeeFee employeeFee = getEmployeeFee(kindPay, workDate, e);
            employeeFeeRepository.save(employeeFee);
        });
    }



    public void calculateIncreaseFee(List<Employee> employeeIncreaseFee,LocalDateTime calculateDate){
        List<KindPay> kindPays = listKindPayIncreaseFee();
        employeeIncreaseFee.stream().forEach(e -> {
            double s = e.getCaclucateFees().stream()
                    .filter(code -> kindPays.contains(code.getKindPay()) &&
                                                      code.getMonth() == calculateDate.getMonthValue() &&
                                                      code.getYear() == calculateDate.getYear())
                    .mapToDouble(emp -> {
                        Double summa = 0.0;
                        if(emp.getKindPay().getCode().equals("060")) {
                            summa += calculateCodeNumber060(e,emp.getSumma());
                        } else {
                            summa += emp.getSumma().doubleValue();
                        }
                        return summa;
                    })
                    .sum();
            e.setIncreaseSummaFee(new BigDecimal(s).multiply(e.getCoefficient()
                                                   .remainder(new BigDecimal(1)))
                                                   .round(new MathContext(4, RoundingMode.HALF_UP)));
        });

    }

    private Double calculateCodeNumber060(Employee employee, BigDecimal  summaBy060) {
        Double summa003 =  employee.getCaclucateFees().stream()
                                   .filter(e -> e.getKindPay().getCode().equals("003"))
                                   .mapToDouble(e -> e.getSumma().doubleValue())
                                   .sum() ;

        BigDecimal newSumma =  new BigDecimal(summa003).multiply(employee.getConstForTime().divide(new BigDecimal(100.0)));




        return summaBy060.subtract(newSumma).doubleValue();
    }

    private EmployeeFee getEmployeeFee(KindPay kindPay, LocalDateTime workDate, Employee employee) {
        EmployeeFee employeeFee = new EmployeeFee();
        employeeFee.setDay(new BigDecimal(0));
        employeeFee.setFinanceOperation(kindPay.getFinanceOperation());
        employeeFee.setGraphWork(employee.getGraphWork());
        employeeFee.setHour(new BigDecimal(0));
        employeeFee.setKindPay(kindPay);
        employeeFee.setMonth(workDate.getMonthValue());
        employeeFee.setYear(workDate.getYear());
        employeeFee.setProcent(new BigDecimal(100));
        employeeFee.setTip(kindPay.getTip());
        employeeFee.setDepartment(employee.getDepartment());
        employeeFee.setEmployee(employee);
        employeeFee.setEmployeeBase(employee);
        employeeFee.setCategory(employee.getCategory());
        employeeFee.setStatus(Status.ACCRUE);
        employeeFee.setActive(Active.ACTIVE);
        employeeFee.setCodeProfit(kindPay.getCodeProfit());
        employeeFee.setCreateDate(java.sql.Date.from(workDate.atZone(ZoneId.systemDefault()).toInstant()));
        employeeFee.setSumma(employee.getIncreaseSummaFee());
        return employeeFee;
    }
}
