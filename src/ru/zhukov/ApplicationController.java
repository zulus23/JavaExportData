package ru.zhukov;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.zhukov.base.BaseApplicationController;
import ru.zhukov.domain.Database;
import ru.zhukov.login.LoginController;
import ru.zhukov.service.DBAuthenticationService;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Zhukov on 18.03.2016.
 */
public class ApplicationController  {
    private static final ApplicationController applicationController = new ApplicationController();

    private Stage loginStage;
    private LoginController loginController;
    private BaseApplicationController baseWindowController;
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
            loginStage.getIcons().add(new Image(getClass().getResource("res/proforma-to_employee.png").toExternalForm()));
            loginStage.setScene(scene);
            loginStage.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    public void createApplicationWindow(){
        try {
            FXMLLoader fxmlAppLoader = new FXMLLoader(ApplicationController.class.getResource("base/BasicApplicationView.fxml"));
            fxmlAppLoader.setResources(ResourceBundle.getBundle("ru.zhukov.base.BasicApplication", Locale.getDefault()));
            baseWindowController = new BaseApplicationController();
            fxmlAppLoader.setController(baseWindowController);
            VBox app = fxmlAppLoader.load();
            Stage stage = new Stage();
            stage.setOnCloseRequest(e -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Закрыть программу?");
                alert.setTitle("Внимание");
                alert.setHeaderText("Закрыть программу");
                alert.showAndWait().ifPresent(response ->{
                    if(response== ButtonType.CANCEL){
                        e.consume();
                    }
                });


            });
            stage.initOwner(null);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setMaximized(true);
            stage.getIcons().add(new Image(getClass().getResource("res/proforma-to_employee.png").toExternalForm()));
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

    protected static Executor FX_PLATFORM_EXECUTOR = Platform::runLater;

    protected final static ExecutorService PARENT_CREATION_POOL = getExecutorService();


    public void authentication(String username, String password, Database database){

      /*  new Thread(()->{

            SQLServerDataSource sqlServerDataSource = new SQLServerDataSource();
            sqlServerDataSource.setDatabaseName(database.getNameInDB());
            sqlServerDataSource.setServerName("Zhukov-PC");
            sqlServerDataSource.setInstanceName("MSSQLSERVER2012");
            sqlServerDataSource.setUser(username);
            sqlServerDataSource.setPassword(password);
            try(Connection connection = sqlServerDataSource.getConnection()){

                Platform.runLater(()->this.createApplicationWindow());

            } catch(SQLException e ){
                Platform.runLater(()-> exceptionReporter(new SQLException("Ошибка базы данных")));
            }

        }).start();

*/

        new DBAuthenticationService().authentication(username,password,database)
                                     .handle((u,ex)->{
           if(u==null){
               Platform.runLater(()-> exceptionReporter(new SQLException("Ошибка базы данных")));
           } else{
               Platform.runLater(()->this.createApplicationWindow());
           }
            return "";

        });

        //Platform.runLater(()->this.createApplicationWindow());





    }

    private Stage getLoginStage(){
        return  loginStage;
    }

    public Void exceptionReporter(Throwable t) {
        loginController.setTextError(t.getMessage());
        return null;
    }


    static ExecutorService getExecutorService() {
        return Executors.newCachedThreadPool((r) -> {
            Thread thread = Executors.defaultThreadFactory().newThread(r);
            String name = thread.getName();
            thread.setName("test-" + name);
            //thread.setDaemon(true);
            return thread;
        });
    }




}
