package ru.zhukov.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.zhukov.domain.*;
import ru.zhukov.repository.*;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.zhukov.share.ConstForIncreaseFee.CODE_PAY_324;
import static ru.zhukov.share.ConstForIncreaseFee.CODE_PAY_326;

@Service
@Component
public class TariffIncreaseService implements TariffIncreaseServiceable {

    private static final String AN_OBJECT = "326";
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
                    .map(t -> {
                        e.setNextTariff(t);
                        e.setCoefficient(t.getSumma().divide(e.getSalary(), BigDecimal.ROUND_HALF_UP));
                        return t;
                    }).orElseThrow(()->new RuntimeException(String.format("Загрузка не возможна. Отсутствует тариф у сотрудника %s.",e.getFullName())));
                    /*.ifPresent(t -> {
                        e.setNextTariff(t);
                        e.setCoefficient(t.getSumma().divide(e.getSalary(), BigDecimal.ROUND_HALF_UP));
                    })*/;

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
    public void makeIncreaseFeeByEmployee(List<Employee> employeeIncreaseFee,KindPay kindPayIn,LocalDateTime workDate) {
        Set<KindPay> kindPays = this.groupKindPayByCode().keySet();
        employeeIncreaseFee.stream().forEach(e ->{
            kindPays.forEach(kindPay -> {
                BigDecimal summaFee =  kindPay.getCode().equals(CODE_PAY_324) ? e.getIncreaseSummaFeeOne() : e.getIncreaseSummaFeeTwo();
                EmployeeFee employeeFee = getEmployeeFee(kindPay, workDate, e,summaFee);
                employeeFeeRepository.save(employeeFee);
            });

        });
    }

    @Transactional
    @Override
    public void makeIncreaseFeeByEmployee(List<Employee> employeeIncreaseFee, LocalDateTime workDate) {
        this.makeIncreaseFeeByEmployee(employeeIncreaseFee,null,workDate);
    }


    public void calculateIncreaseFee(List<Employee> employeeIncreaseFee,LocalDateTime calculateDate){
        /*List<KindPay> kindPays = listKindPayIncreaseFee();*/
        Map<KindPay,Set<KindPay>> kindPaySetMap =  this.groupKindPayByCode();
        employeeIncreaseFee.stream().forEach(e -> {

            kindPaySetMap.forEach((k,v) ->{

                calculatePartIncreaseFee(calculateDate, v, e,k);
            });

        });

    }

    private void calculatePartIncreaseFee(LocalDateTime calculateDate, Set<KindPay> kindPays, Employee e,KindPay key) {
        double s = e.getCaclucateFees().stream()
                .filter(code -> kindPays.contains(code.getKindPay()) &&
                                                  code.getMonth() == calculateDate.getMonthValue() &&
                                                  code.getYear() == calculateDate.getYear())
                .mapToDouble(emp -> {
                    Double summa = 0.0;
                    if(emp.getKindPay().getCode().equals("060") && key.getCode().equals(CODE_PAY_326)) {
                        summa += calculateCodeNumber060(e,emp.getSumma());
                    } else {
                        summa += emp.getSumma().doubleValue();
                    }
                    return summa;
                })
                .sum();
         if(key.getCode().equals(CODE_PAY_326)) {
             e.setIncreaseSummaFeeTwo(new BigDecimal(s).multiply(e.getCoefficient()
                     .remainder(new BigDecimal(1)))
                     .round(new MathContext(4, RoundingMode.HALF_UP)));
         }
        if(key.getCode().equals(CODE_PAY_324)) {
            e.setIncreaseSummaFeeOne(new BigDecimal(s).multiply(e.getCoefficient()
                    .remainder(new BigDecimal(1)))
                    .round(new MathContext(4, RoundingMode.HALF_UP)));
        }
    }

    @Override
    public List<KindPay> distinctPayIntoIncreaseFee() {
        List<KindPay> pays = increaseKindPayRepository.findAll()
                .stream().map(e -> e.getKindPayIn()).distinct().collect(Collectors.toList());

        return pays;
    }

    private Map<KindPay,Set<KindPay>>  groupKindPayByCode(){
        Map<KindPay, Set<KindPay>> pays = increaseKindPayRepository.findAll()
                .stream()
                .filter(ik -> Objects.nonNull(ik.getKindPayIn()))
                .collect(Collectors.groupingBy(IncreaseKindPay::getKindPayIn,Collectors.mapping(IncreaseKindPay::getKindPay,Collectors.toSet())));
        return pays;

    }


    private Double calculateCodeNumber060(Employee employee, BigDecimal  summaBy060) {
        Double summa003 =  employee.getCaclucateFees().stream()
                                   .filter(e -> e.getKindPay().getCode().equals("003"))
                                   .mapToDouble(e -> e.getSumma().doubleValue())
                                   .sum() ;

        BigDecimal newSumma =  new BigDecimal(summa003).multiply(employee.getConstForTime().divide(new BigDecimal(100.0)));
        Double summa046 =  employee.getCaclucateFees().stream()
                .filter(e -> e.getKindPay().getCode().equals("046"))
                .mapToDouble(e -> e.getSumma().doubleValue())
                .sum() ;
        BigDecimal newSumma046 =  new BigDecimal(summa046).multiply(employee.getConstForTime().divide(new BigDecimal(100.0)));



        return summaBy060.subtract(newSumma).subtract(newSumma046).doubleValue();
    }

    private EmployeeFee getEmployeeFee(KindPay kindPay, LocalDateTime workDate, Employee employee,BigDecimal summaFee) {
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
        /*employeeFee.setSumma(employee.getIncreaseSummaFeeOne());*/
        employeeFee.setSumma(summaFee);
        return employeeFee;
    }
}
