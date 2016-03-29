package ru.zhukov;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.zhukov.account.AccountRecordController;
import ru.zhukov.action.Action;
import ru.zhukov.base.BasicApplicationController;
import ru.zhukov.domain.Database;
import ru.zhukov.dto.CurrentUser;
import ru.zhukov.login.LoginController;
import ru.zhukov.service.DBAuthenticationService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Zhukov on 18.03.2016.
 */
@Configuration
public class ApplicationController  {
    private static final ApplicationController applicationController = new ApplicationController();

    private Stage loginStage;
    private LoginController loginController;
    private BasicApplicationController baseWindowController;
    private CurrentUser currentUser;


    public void createLoginWindow(){
        try {

            FXMLLoader fxmlLoginLoader = new FXMLLoader(ApplicationController.class.getResource("login/LoginView.fxml"));
            loginController = new LoginController(applicationController);
            fxmlLoginLoader.setController(loginController);
             AnchorPane login = fxmlLoginLoader.load();
            //fx:controller="ru.zhukov.login.LoginController"
          ///  loginController.setMainController(applicationController);


            loginStage = new Stage();
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
    public void createApplicationWindow(){
        try {
            FXMLLoader fxmlAppLoader = new FXMLLoader(ApplicationController.class.getResource("base/BasicApplicationView.fxml"));
            fxmlAppLoader.setResources(ResourceBundle.getBundle("Application", Locale.getDefault()));
            baseWindowController = new BasicApplicationController();
            fxmlAppLoader.setController(baseWindowController);
            VBox app = fxmlAppLoader.load();
            Stage stage = new Stage();
            stage.setOnCloseRequest(Action::exit);
            stage.initOwner(null);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setMaximized(true);
            stage.getIcons().add(new Image(getClass().getResource("/ru/zhukov/assests/image/proforma-to_employee.png").toExternalForm()));
            Scene scene = new Scene(app);
            stage.setTitle("Передача данных из АиТ");

            stage.setScene(scene);
            stage.show();
            loginStage.close();
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
               Platform.runLater(()-> exceptionReporter(new SQLException("Ошибка базы данных")));
           } else{
               currentUser = (CurrentUser) user;

               Platform.runLater(()->this.createApplicationWindow());
           }
            return "";

        }).exceptionally(this::exceptionReporter);

        //Platform.runLater(()->this.createApplicationWindow());





    }

    private Stage getLoginStage(){
        return  loginStage;
    }

    public String exceptionReporter(Throwable t) {
        loginController.setTextError(t.getMessage());
        return t.getMessage();
    }

    public void  showAccountRecord(){
        try {
            FXMLLoader fxmlAccountLoader = new FXMLLoader(ApplicationController.class.getResource("/ru/zhukov/account/AccountRecordView.fxml"));
            TabPane tabPane =applicationController.baseWindowController.getTpWindowContainer();
            AccountRecordController accountRecordController = new AccountRecordController();
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

    @Bean
    public CurrentUser currentUser(){
        return  applicationController.currentUser;
    }







}
