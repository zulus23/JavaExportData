package ru.zhukov.action;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.WindowEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.zhukov.ApplicationController;
import ru.zhukov.config.ApplicationContextConfig;
import ru.zhukov.repository.JDBCAccountRepository;

import javax.sql.DataSource;

/**
 * Created by Gukov on 25.03.2016.
 */
public class Action {

  /* private static  EventHandler exitEvent = (e) -> {
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Закрыть программу?");
       alert.setTitle("Внимание");
       alert.setHeaderText("Закрыть программу");
       alert.showAndWait().ifPresent(response ->{
           if(response== ButtonType.CANCEL && e.getEventType().getName()== WindowEvent.WINDOW_CLOSE_REQUEST.getName()){
               e.consume();

           }
           if(response== ButtonType.OK && e.getEventType().getName() == ActionEvent.ACTION.getName()){
                   Platform.exit();
           }

       });
    };

*/
    public static <T extends Event> void exit(T actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Закрыть программу?");
        alert.setTitle("Внимание");
        alert.setHeaderText("Закрыть программу");
        alert.showAndWait().ifPresent(response ->{
            if(response== ButtonType.CANCEL && actionEvent.getEventType().getName()== WindowEvent.WINDOW_CLOSE_REQUEST.getName()){
                actionEvent.consume();

            }
            if(response== ButtonType.OK && actionEvent.getEventType().getName() == ActionEvent.ACTION.getName()){
                Platform.exit();
            }

        });
    }

    public static void createAccountRecord(ActionEvent event){

        try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationContextConfig.class)) {

            DataSource  dataSource = (DataSource) context.getBean("dataSource");
            JDBCAccountRepository accountDao = new JDBCAccountRepository();
            accountDao.setDataSource(dataSource);

            System.out.println(accountDao.listAccountRecords().size());

        }

    }

    public static void showAccountRecord(ActionEvent actionEvent) {
        ApplicationController.getInstance().showAccountRecord();
    }
}
