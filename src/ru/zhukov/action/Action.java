package ru.zhukov.action;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.WindowEvent;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.zhukov.ApplicationController;
import ru.zhukov.config.ApplicationContextConfig;
import ru.zhukov.domain.KindPay;
import ru.zhukov.repository.JDBCAccountRepository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

/**
 * Created by Gukov on 25.03.2016.
 */
public class Action {


    public static  void exit(Event actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Вы действительно хотите закрыть программу?");
        alert.setTitle("Подтверждение закрытия");
        alert.setHeaderText("");
        alert.showAndWait().ifPresent(response ->{
            if(response== ButtonType.CANCEL && actionEvent.getEventType().getName()== WindowEvent.WINDOW_CLOSE_REQUEST.getName()){
                actionEvent.consume();

            }
            if(response== ButtonType.OK && actionEvent.getEventType().getName() == ActionEvent.ACTION.getName()){
                Platform.exit();
            }

        });
    }

    public static Optional<ButtonType> deleteMessage( String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, String.format("Вы действительно хотите удалить %s ?",message));
        alert.setTitle("Подтверждение удаления");
        alert.setHeaderText("");
        return alert.showAndWait();
    }

    public static Optional<KindPay> selectKindPay(List<KindPay> kindPayList) {
        ChoiceDialog<KindPay> dialog = new ChoiceDialog(kindPayList.get(0), kindPayList);
        dialog.setTitle("Выбор вида начисления");
        dialog.setHeaderText("");
        dialog.setContentText("Каким кодом произвести начисление:");
        return dialog.showAndWait();
    }
    public static Optional<ButtonType> confirmationAction( String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, String.format("Вы действительно хотите выполнить  %s ?",message));
        alert.setTitle("Подтверждение выполнения");
        alert.setHeaderText("");
        return alert.showAndWait();
    }






    public static  void  showErrorInformation(String s){
        Alert alert = new Alert(Alert.AlertType.ERROR, s);
        alert.setTitle("Ошибка");
        alert.setHeaderText("");
        alert.show();
    }



}
