package ru.zhukov.fee;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.zhukov.config.ApplicationContextConfig;
import ru.zhukov.domain.Database;
import ru.zhukov.domain.IncreaseKindPay;
import ru.zhukov.domain.KindPay;
import ru.zhukov.dto.CurrentUser;
import ru.zhukov.repository.IncreaseKindPayRepository;
import ru.zhukov.repository.KindPayRepository;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationContextConfig.class})

public class FeeTest {
    private CurrentUser currentUser;

    @Autowired
    private KindPayRepository kindPayRepository;
    @Autowired
    private IncreaseKindPayRepository increaseKindPayRepository;

    public FeeTest() {
        currentUser = new CurrentUser("report","report",new Database("ИНВЕСТ","g_invst_tarif",""));
        ApplicationContextConfig.setCurrentUser(currentUser);

    }
    @Before
    public void setUp(){
        createKindPay008();

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
       increaseKindPayRepository.deleteAll();
    }

}
