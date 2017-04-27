package ru.zhukov.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import ru.zhukov.config.ApplicationContextConfig;
import ru.zhukov.domain.Database;
import ru.zhukov.dto.CurrentUser;
import ru.zhukov.repository.AccountImport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * Created by Gukov on 20.04.2017.
 */


public class UpdateAccount91 {

    private AnnotationConfigApplicationContext ctx;
    private CurrentUser currentUser;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private AccountImport accountImportRepository;

    public UpdateAccount91() {
        currentUser = new CurrentUser("report","report",new Database("","ait_cpu",""));
        ApplicationContextConfig.setCurrentUser(currentUser);

        this.ctx = new AnnotationConfigApplicationContext(ApplicationContextConfig.class);
        ctx.scan("ru.zhukov","ru.zhukov.utils");
        ApplicationContextConfig.entityManagerFactory(ApplicationContextConfig.dataSource(currentUser));
        entityManager = ctx.getBean(EntityManager.class);
       accountImportRepository = ctx.getBean(AccountImport.class);

    }
   @Transactional
    public void selectCretid(){

       List<Account> list =  accountImportRepository.findAll();
       List<Account> list1 =  list.stream().filter(e -> e.getK().trim().length() > 10).collect(Collectors.toList());
       Map<String, List<Account>>  map1 =  list1.stream().collect(groupingBy(e -> e.getK()));
      // list1.forEach(accountImportRepository::delete);

       map1.forEach((k,v) -> {
           String[] accounts = k.split(",");

              for (int i = 0; i < accounts.length; i++){

                  for (Account accountget:v.stream().collect(Collectors.toList())) {
                      Account account = new Account();
                      account.setCode(accountget.getCode());
                      account.setName(accountget.getName());
                      account.setZatr(accountget.getZatr());
                      account.setZatr_1C(accountget.getZatr_1C());
                      account.setK(accounts[i].trim());
                      account.setD(accountget.getD());
                      account.setId((int)accountImportRepository.count()+1);

                       accountImportRepository.save(account);

                  }
              }


           });






    }



    public static void main(String[] args) {
       new UpdateAccount91().selectCretid();
    }

}
