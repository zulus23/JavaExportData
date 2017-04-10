package ru.zhukov.transfer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Gukov on 10.04.2017.
 */
public class SetupDepartmentTransferController implements Initializable {


    @FXML
    private TableView setupDepartmentTransfer;
    @FXML
    private TableColumn departmentAit;
    @FXML
    private TableColumn departmentTransfer;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
         //departmentAit.getCellFactory(ComboBoxTableCell.forTableColumn())
    }
}
