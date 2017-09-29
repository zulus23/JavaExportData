package ru.zhukov;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.zhukov.config.ApplicationContextConfig;
import ru.zhukov.domain.Database;
import ru.zhukov.dto.CurrentUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationContextConfig.class})
public abstract class AbstractTest {
    private CurrentUser currentUser;


    public AbstractTest() {
        currentUser = new CurrentUser("report","report",new Database("ИНВЕСТ","ait_db_tarif",""));
        ApplicationContextConfig.setCurrentUser(currentUser);
    }
}
