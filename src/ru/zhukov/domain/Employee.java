package ru.zhukov.domain;



import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collection;
import java.util.List;
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

    public Integer getCurrentRank(){
        Pattern pattern = Pattern.compile("\\d");
        return  Optional.ofNullable(position).map(r ->  {
            Matcher matcher = pattern.matcher(r.getDescription());
            if (matcher.find()){
                return Integer.valueOf(matcher.group());
            }

            return  -100;
        }).orElse(-100);
    }

}
