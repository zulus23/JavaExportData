package ru.zhukov.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.zhukov.config.ApplicationContextConfig;
import ru.zhukov.domain.Database;
import ru.zhukov.domain.TransferAccount;
import ru.zhukov.dto.CurrentUser;
import ru.zhukov.service.TransferService;


import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Gukov on 19.04.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationContextConfig.class})

public class TransferServiceTest {

    @Autowired
    private TransferService transferService;

    private CurrentUser currentUser;
    @Before
    public  void  setUp(){
        currentUser = new CurrentUser("report","report",new Database("ГОТЭК","ait_db",""));
        ApplicationContextConfig.setCurrentUser(currentUser);
    }


    @Test
    public void createTransferAccount(){

        List<TransferAccount> transferAccounts =  transferService.getAllTransferAccount(2017,2);

        assertNotNull(transferAccounts);
    }

}
