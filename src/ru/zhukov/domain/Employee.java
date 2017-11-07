package ru.zhukov.domain;



import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "const")
public class Employee {
    @Id
    @Column(name = "pers_id")
    private String id;
    @Column(name = "tabel_n")
    private String tabelNumber;
    @Column(name = "name")
    private String name;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "sec_name")
    private String secondName;
    @Column(name = "oklad")
    private BigDecimal salary;

    @Column(name = "date_out")
    private Date dateDissmissal;


    @ManyToOne
    @JoinColumn(name = "n_otd")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "dolsn")
    private Position position;

    @OneToOne
    @JoinColumns({@JoinColumn(name = "ets_r",referencedColumnName = "level_row"),
                 @JoinColumn(name = "ets_c",referencedColumnName = "step_column")})
    private Tariff tariff;

    @OneToMany
    @JoinColumn(name = "pers_id")
    private List<CaclucateFee> caclucateFees;

    @OneToMany
    @JoinColumn(name = "pers_id")
    private List<EmployeeFee> employeeFees;



    @OneToOne
    @JoinColumn(name = "category")
    private Category category;

    @Transient
    private String fullName;

    @Transient
    private Tariff nextTariff;

    @Transient
    private BigDecimal coefficient;

    @Transient
    private BigDecimal increaseSummaFeeOne;



    @Transient
    private BigDecimal increaseSummaFeeTwo;


    @Column(name = "brv")
    private String graphWork;

    @Column(name = "konst1")
    private BigDecimal const1;

    @Column(name = "konst2")
    private BigDecimal const2;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTabelNumber() {
        return tabelNumber;
    }

    public void setTabelNumber(String tabelNumber) {
        this.tabelNumber = tabelNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }


    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Date getDateDissmissal() {
        return dateDissmissal;
    }

    public void setDateDissmissal(Date dateDissmissal) {
        this.dateDissmissal = dateDissmissal;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<CaclucateFee> getCaclucateFees() {
        return caclucateFees;
    }

    public void setCaclucateFees(List<CaclucateFee> caclucateFees) {
        this.caclucateFees = caclucateFees;
    }

    public List<EmployeeFee> getEmployeeFees() {
        return employeeFees;
    }

    public void setEmployeeFees(List<EmployeeFee> employeeFees) {
        this.employeeFees = employeeFees;
    }

    public Integer getCurrentRank(){
        Pattern pattern = Pattern.compile("\\d");
        return  Optional.ofNullable(position).map(r ->  {
            Matcher matcher = pattern.matcher(r.getDescription());
            if (matcher.find()){
                return Integer.valueOf(matcher.group());
            }

            return  0;
        }).orElse(0);
    }

    public Integer getRankByTariff(){
        return tariff.getRank();
    }


    public String getFullName(){
        return String.format("%s %s %s",name.trim(),firstName.trim(),secondName.trim());
    }

    public String getDepartmentName(){
        return String.format("%s - %s",department.getBriefName(), department.getName().trim());
    }
    public String getPositionName(){
        return String.format("%s",position.getDescription().trim());
    }

    public Tariff getNextTariff() {
        return nextTariff;
    }

    public void setNextTariff(Tariff nextTariff) {
        this.nextTariff = nextTariff;
    }

    public BigDecimal getSalaryByPosition(){
        return  Optional.ofNullable(nextTariff).map(e ->e.getSumma()).orElse(new BigDecimal(0.0));
        //return nextTariff.getSumma();
    }

    public BigDecimal getCoefficient() {
        return coefficient;
    }


    public BigDecimal getIncreaseSummaFeeOne() {
        return increaseSummaFeeOne;
    }

    public void setIncreaseSummaFeeOne(BigDecimal increaseSummaFeeOne) {
        this.increaseSummaFeeOne = increaseSummaFeeOne;
    }

    public BigDecimal getIncreaseSummaFeeTwo() {
        return increaseSummaFeeTwo;
    }

    public void setIncreaseSummaFeeTwo(BigDecimal increaseSummaFeeTwo) {
        this.increaseSummaFeeTwo = increaseSummaFeeTwo;
    }
    public String getGraphWork() {
        return graphWork;
    }

    public void setGraphWork(String graphWork) {
        this.graphWork = graphWork;
    }

    public void setCoefficient(BigDecimal coefficient) {
        this.coefficient = coefficient;
    }


    public BigDecimal getConstForTime() {
        return Optional.ofNullable(this.const1).orElse(this.const2);
    }

    public void setConst1(BigDecimal const1) {
        this.const1 = const1;
    }

    public void setConst2(BigDecimal const2) {
        this.const2 = const2;
    }

    /*public void setConstForTime(BigDecimal constForTime) {
        this.constForTime = constForTime;
    }*/
}
