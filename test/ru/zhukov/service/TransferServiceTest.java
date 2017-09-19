package ru.zhukov.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.zhukov.config.ApplicationContextConfig;
import ru.zhukov.domain.Database;
import ru.zhukov.domain.Employee;
import ru.zhukov.domain.StaffTable;
import ru.zhukov.domain.TransferAccount;
import ru.zhukov.dto.CurrentUser;
import ru.zhukov.repository.EmployeeRepository;
import ru.zhukov.repository.StaffTableRepository;
import ru.zhukov.service.TransferService;


import javax.jdo.annotations.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * Created by Gukov on 19.04.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationContextConfig.class})

public class TransferServiceTest {

    @Autowired
    private TransferService transferService;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private StaffTableRepository staffTableRepository;


    private CurrentUser currentUser;
    public TransferServiceTest(){

        currentUser = new CurrentUser("report","report",new Database("ИНВЕСТ","g_invst_tarif",""));
        ApplicationContextConfig.setCurrentUser(currentUser);
    }
    @Before
    public  void  setUp(){

    }


    @Test
    public void createTransferAccount(){

        List<TransferAccount> transferAccounts =  transferService.getAllTransferAccount(2017,2);

        assertNotNull(transferAccounts);

    }

    @Test
    public void oneEmployee(){

       Employee employee =  employeeRepository.findOne("M422");


        System.out.println("employee.getFirstName().length() = " + employee.getFirstName().length());
        assertEquals ("Жуков", employee.getName().trim());
        assertEquals(29440,employee.getStaffTable().get(0).getSalary().intValue());

    }
    @Test

    public void  selectEmployee(){
        List<Employee> employees  = employeeRepository.findByNameIsContaining("%Жуков%");
        assertEquals(1,employees.size());
    }

    @Test
    public void selectStaff(){
        StaffTable staffTable =  staffTableRepository.findOne("qu429");
        assertEquals("0401", staffTable.getEmployee().getTabelNumber());
    }







}
