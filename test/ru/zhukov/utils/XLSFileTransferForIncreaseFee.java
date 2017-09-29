package ru.zhukov.utils;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.zhukov.AbstractTest;
import ru.zhukov.config.ApplicationContextConfig;
import ru.zhukov.domain.Employee;
import ru.zhukov.service.TariffIncreaseService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import static  org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationContextConfig.class})

public class XLSFileTransferForIncreaseFee extends AbstractTest {

    private Path path;
    private Path pathOut;

    @Autowired
    private TariffIncreaseService increaseService;

    @Before
    public void setUp(){
        path = Paths.get(".").toAbsolutePath().resolve(Paths.get("template/CalculateIncreaseFee.xlsx"));
        pathOut = Paths.get(".").toAbsolutePath().resolve(Paths.get("outxls/CalculateIncreaseFee_.xlsx"));

    }

    @Test
    public void fileMustWillCreated(){
        LocalDateTime dateCalculate = LocalDateTime.of(2017, Month.SEPTEMBER,01,0,0);
        List<Employee> employees = increaseService.employeeListNeedIncreaseTarif(dateCalculate);
        increaseService.calculateIncreaseFee(employees,dateCalculate);
        CreateExcelFileForIncreaseFee report = new CreateExcelFileForIncreaseFee();
        Path path =  report.generateReportFromData(employees);

        assertTrue(Files.exists(path));

    }

}
