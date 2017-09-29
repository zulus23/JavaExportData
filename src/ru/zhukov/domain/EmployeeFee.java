package ru.zhukov.domain;




import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "vari")
public class EmployeeFee {
    @TableGenerator(name = "max_ids",table = "max_ids",pkColumnName = "table_name",valueColumnName = "max_id",pkColumnValue = "vari")
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "max_ids")

    @Column(name = "key_field")

    private String id;

    @Column(name = "prc")
    private BigDecimal procent;
    @Column(name = "days")
    private BigDecimal day;
    @Column(name = "hours")
    private BigDecimal hour;
    @Column(name = "mm")
    private Integer month;
    @Column(name = "yy")
    private Integer year;
    @Column(name = "fin_op")
    private String financeOperation;
    @Column(name = "tip")
    private String tip;
    @OneToOne
    @JoinColumn(name = "code")
    private KindPay kindPay;
    @Column(name = "brv")
    private String graphWork;

    @Column(name = "summa")
    private BigDecimal summa;

    @ManyToOne
    @JoinColumn(name = "podr")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "pers_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "pers_id_2")
    private Employee employeeBase;

    @Column(name = "sost")
    private Status status;
    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @Column(name = "fl_enable")
    private Active active;

    @Column(name = "d_nach")
    private Date createDate;
    @Column(name = "vid_dohods")
    private String codeProfit;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public BigDecimal getProcent() {
        return procent;
    }

    public void setProcent(BigDecimal procent) {
        this.procent = procent;
    }

    public BigDecimal getDay() {
        return day;
    }

    public void setDay(BigDecimal day) {
        this.day = day;
    }

    public BigDecimal getHour() {
        return hour;
    }

    public void setHour(BigDecimal hour) {
        this.hour = hour;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getFinanceOperation() {
        return financeOperation;
    }

    public void setFinanceOperation(String financeOperation) {
        this.financeOperation = financeOperation;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public KindPay getKindPay() {
        return kindPay;
    }

    public void setKindPay(KindPay kindPay) {
        this.kindPay = kindPay;
    }

    public String getGraphWork() {
        return graphWork;
    }

    public void setGraphWork(String graphWork) {
        this.graphWork = graphWork;
    }

    public BigDecimal getSumma() {
        return summa;
    }

    public void setSumma(BigDecimal summa) {
        this.summa = summa;

    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployeeBase() {
        return employeeBase;
    }

    public void setEmployeeBase(Employee employeeBase) {
        this.employeeBase = employeeBase;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Active getActive() {
        return active;
    }

    public void setActive(Active active) {
        this.active = active;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCodeProfit() {
        return codeProfit;
    }

    public void setCodeProfit(String codeProfit) {
        this.codeProfit = codeProfit;
    }
}
