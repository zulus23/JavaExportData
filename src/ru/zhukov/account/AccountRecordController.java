package ru.zhukov.account;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import ru.zhukov.service.AccountRecordDataService;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Gukov on 29.03.2016.
 */
public class AccountRecordController  implements Initializable{

    private AccountRecordDataService dataService;


    @FXML
    TableView accountRecordTable;

    @FXML
    Label lMy;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
          this.lMy.setText("Создано окно");
          //this.accountRecordTable.getItems().addAll()
    }
}
