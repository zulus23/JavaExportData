package ru.zhukov.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.zhukov.config.ApplicationContextConfig;
import ru.zhukov.domain.TransferAccount;
import ru.zhukov.service.TransferService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Gukov on 18.04.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationContextConfig.class})

public class XLSFileTransferTo1CTest {

    @Autowired
    private TransferService transferService;

    private Path path;
    private Path pathOut;
    private List<TransferAccount> accountList;
    @Before
    public void setUp(){
        path = Paths.get(".").toAbsolutePath().resolve(Paths.get("template/PayFromAit_To_1C.xlsx"));
        pathOut = Paths.get(".").toAbsolutePath().resolve(Paths.get("outxls/PayFromAit_To_1C_.xlsx"));
        accountList =  transferService.getAllTransferAccount(2017,2);
    }






}
