package ru.zhukov;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import ru.zhukov.action.Action;
import ru.zhukov.base.BasicApplicationController;
import ru.zhukov.config.ApplicationContextConfig;
import ru.zhukov.domain.Database;
import ru.zhukov.dto.CurrentUser;
import ru.zhukov.login.LoginController;
import ru.zhukov.repository.AccountRepository;
import ru.zhukov.repository.JDBCAccountRepository;
import ru.zhukov.repository.TransferDepartmentJpaRepository;
import ru.zhukov.service.AccountRecordDataService;
import ru.zhukov.service.DBAuthenticationService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Zhukov on 18.03.2016.
 */

public class ApplicationController  {
    private static final ApplicationController applicationController = new ApplicationController();
    private AnnotationConfigApplicationContext ctx;

    @Autowired
    private TransferDepartmentJpaRepository transferDepartmentJpaRepository;
    //private Stage loginStage;
    private LoginController loginController;
    private BasicApplicationController baseWindowController;
    private CurrentUser currentUser;

    private AccountRepository accountRepository;
    private Locale localeRU  = new Locale("ru","RU");

    private AccountRecordDataService getDataService(){
        accountRepository = new JDBCAccountRepository(ApplicationContextConfig.dataSource(currentUser),
                                                      ApplicationContextConfig.dataSourceAxapta());
        ApplicationContextConfig.setCurrentUser(currentUser);

        this.ctx = new AnnotationConfigApplicationContext(ApplicationContextConfig.class);
        ctx.scan("ru.zhukov");


        ApplicationContextConfig.entityManagerFactory(ApplicationContextConfig.dataSource(currentUser));


         return new AccountRecordDataService(accountRepository);
    }






    public void createLoginWindow(){
        try {

            FXMLLoader fxmlLoginLoader = new FXMLLoader(ApplicationController.class.getResource("login/LoginView.fxml"));
            loginController = new LoginController(applicationController);
            fxmlLoginLoader.setController(loginController);
             AnchorPane login = fxmlLoginLoader.load();
            //fx:controller="ru.zhukov.login.LoginController"
          ///  loginController.setMainController(applicationController);


            Stage loginStage = new Stage();
            loginStage.initOwner(null);
            loginStage.initModality(Modality.APPLICATION_MODAL);
            loginStage.setResizable(false);
            loginStage.setTitle("Передача данных из АиТ");
            Scene scene = new Scene(login);
            loginStage.getIcons().add(new Image(getClass().getResource("/ru/zhukov/assests/image/proforma-to_employee.png").toExternalForm()));
            loginStage.setScene(scene);
            loginStage.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    public void createApplicationWindow(String nameEnterprise){
        try {
            FXMLLoader fxmlAppLoader = new FXMLLoader(ApplicationController.class.getResource("base/BasicApplicationView.fxml"));
            fxmlAppLoader.setResources(ResourceBundle.getBundle("Application", localeRU));
            baseWindowController = new BasicApplicationController(this.getDataService(),this.currentUser);
            fxmlAppLoader.setController(baseWindowController);
            AnchorPane app = fxmlAppLoader.load();
            Stage stage = new Stage();
            stage.setOnCloseRequest(Action::exit);
            stage.initOwner(null);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setMinWidth(800);
            stage.setMinHeight(400);
            stage.setMaximized(true);
            stage.getIcons().add(new Image(getClass().getResource("/ru/zhukov/assests/image/proforma-to_employee.png").toExternalForm()));
            Scene scene = new Scene(app);
            stage.setTitle(String.format("Передача данных из АиТ - %s",nameEnterprise));

            stage.setScene(scene);
            stage.show();
            loginController.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    private Boolean next;
    public static ApplicationController getInstance(){
        return applicationController;
    }

    public void authentication(String username, String password, Database database){
        DBAuthenticationService dbAuthenticationService = new DBAuthenticationService();

       dbAuthenticationService.authentication(username,password,database)

                                     .handleAsync((user,ex)->{
           if(user==null){
               Platform.runLater(()-> exceptionReporter(new SQLException("Ошибка идентификации.\nПопробуйте еще раз.")));
           } else{
               currentUser = (CurrentUser) user;

               Platform.runLater(()->this.createApplicationWindow(database.getName()));
           }
            return "";

        }).exceptionally(this::exceptionReporter);

        //Platform.runLater(()->this.createApplicationWindow());





    }


    public String exceptionReporter(Throwable t) {
        loginController.setTextError(t.getMessage());
        return t.getMessage();
    }

    /*public void  showAccountRecord(){
        try {
            FXMLLoader fxmlAccountLoader = new FXMLLoader(ApplicationController.class.getResource("/ru/zhukov/account/AccountRecordView.fxml"));
            TabPane tabPane =applicationController.baseWindowController.getTpWindowContainer();
            AccountRecordController accountRecordController = new AccountRecordController(this.getDataService());
            fxmlAccountLoader.setController(accountRecordController);

            AnchorPane app = fxmlAccountLoader.load();
          //  app.setMinWidth(tabPane.getTabMaxWidth());
           // app.setMinHeight(tabPane.getTabMaxHeight());
            AnchorPane anchorPane = new AnchorPane();
            AnchorPane.setTopAnchor(app, 0.0);
            AnchorPane.setLeftAnchor(app, 0.0);
            AnchorPane.setRightAnchor(app, 0.0);
            AnchorPane.setBottomAnchor(app, 0.0);

            anchorPane.getChildren().add(app);

            Tab tabAccount = new Tab();

            tabAccount.setText("Проводки за период...");
            tabAccount.setContent(anchorPane);

            tabPane.getTabs().addAll(tabAccount);

        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
*/
  /*  @Bean
    public CurrentUser currentUser(){
        return  applicationController.currentUser;
    }
*/

@Bean
 public CurrentUser currentUser(){
     return  currentUser;
 }

    public  AnnotationConfigApplicationContext getCtx() {
        return ctx;
    }
}
