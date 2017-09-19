package ru.zhukov.fee;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.zhukov.config.ApplicationContextConfig;
import ru.zhukov.domain.Database;
import ru.zhukov.domain.KindPay;
import ru.zhukov.dto.CurrentUser;
import ru.zhukov.repository.KindPayRepository;

import static org.junit.Assert.*;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationContextConfig.class})

public class FeeTest {
    private CurrentUser currentUser;

    @Autowired
    private KindPayRepository kindPayRepository;

    public FeeTest() {
        currentUser = new CurrentUser("report","report",new Database("ИНВЕСТ","g_invst_tarif",""));
        ApplicationContextConfig.setCurrentUser(currentUser);

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
}
