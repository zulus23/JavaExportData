package ru.zhukov.fee;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.zhukov.config.ApplicationContextConfig;
import ru.zhukov.domain.*;
import ru.zhukov.dto.CurrentUser;
import ru.zhukov.repository.*;
import ru.zhukov.service.TariffIncreaseServiceable;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationContextConfig.class})

public class FeeTest {
    private CurrentUser currentUser;

    @Autowired
    private KindPayRepository kindPayRepository;
    @Autowired
    private IncreaseKindPayRepository increaseKindPayRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TariffIncreaseServiceable tariffIncreaseServiceable;

    @Autowired
    private EmployeeFeeRepository employeeFeeRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public FeeTest() {
        currentUser = new CurrentUser("report","report",new Database("ИНВЕСТ","g_invst_tarif",""));
        ApplicationContextConfig.setCurrentUser(currentUser);

    }
    @Before
    public void setUp(){
        /*createKindPay008();*/

    }



    @Test
    public void selectMustNotContainsKindPayGreath500(){
        List<KindPay> pays = kindPayRepository.queryKindPayByCodeIsLessThan();
        KindPay kindPay =  new KindPay();
        kindPay.setCode("001");
        kindPay.setName("");
        assertTrue(pays.contains(kindPay));
        kindPay =  new KindPay();
        kindPay.setCode("500");
        kindPay.setName("");
        assertFalse(pays.contains(kindPay));

    }
    @Test
    public void tableForInreaseKindPayMustExist(){
         List<IncreaseKindPay> kindPays =  increaseKindPayRepository.findAll();
         assertTrue(kindPays.size() == 1);



    }

    @Test(expected = org.springframework.transaction.TransactionSystemException.class)
    public void cannotSaveTwoSameIncreaseKindPay(){
        createKindPay008();


    }
    @Test
    public void makeIncreaseFee(){
        Employee e = employeeRepository.findByTabelNumber("0401");
        KindPay kindPay = kindPayRepository.findOne("017");
        LocalDateTime workDate = LocalDateTime.now();
        assertNotNull(e);
        assertNotNull(kindPay);
        EmployeeFee employeeFee = new EmployeeFee();
        employeeFee.setDay(new BigDecimal(0));
        employeeFee.setFinanceOperation(kindPay.getFinanceOperation());
        employeeFee.setGraphWork(e.getGraphWork());
        employeeFee.setHour(new BigDecimal(0));
        employeeFee.setKindPay(kindPay);
        employeeFee.setMonth(workDate.getMonthValue());
        employeeFee.setYear(workDate.getYear());
        employeeFee.setProcent(new BigDecimal(100));
        employeeFee.setTip(kindPay.getTip());
        employeeFee.setDepartment(e.getDepartment());
        employeeFee.setEmployee(e);
        employeeFee.setEmployeeBase(e);
        employeeFee.setCategory(e.getCategory());
        employeeFee.setStatus(Status.ACCRUE);
        employeeFee.setActive(Active.ACTIVE);
        employeeFee.setCodeProfit(kindPay.getCodeProfit());
        employeeFee.setCreateDate(Date.from(workDate.atZone(ZoneId.systemDefault()).toInstant()));
        employeeFee.setSumma(new BigDecimal(250.45));

        employeeFeeRepository.save(employeeFee);
        assertNotNull(employeeFee.getId());
        System.out.println(" id = "+employeeFee.getId());
    }


    @Test
    public void selectIncreaseFee(){
          List<IncreaseKindPay> increaseKindPays =  increaseKindPayRepository.findAll();
          assertNotNull(increaseKindPays);

    }
    @Test
    public void distinctPayFee(){
        List<KindPay> kindPays =  tariffIncreaseServiceable.distinctPayIntoIncreaseFee();
        assertNotNull(kindPays);
        assertEquals(3,kindPays.size());
    }




    private void createKindPay008() {
        KindPay kindPay =  new KindPay();
        kindPay.setCode("008");
        kindPay.setName("");
        IncreaseKindPay increaseKindPay = new IncreaseKindPay();
        increaseKindPay.setKindPay(kindPay);

        increaseKindPayRepository.save(increaseKindPay);
    }


    @After
    public void tearDown(){
       //increaseKindPayRepository.deleteAll();
    }

}
