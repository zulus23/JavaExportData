package ru.zhukov.action;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

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
}
