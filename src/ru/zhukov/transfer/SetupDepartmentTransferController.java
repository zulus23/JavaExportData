package ru.zhukov.transfer;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.zhukov.ApplicationController;
import ru.zhukov.domain.SetupDepartmentForTransfer;
import ru.zhukov.service.TransferDepartmentService;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Gukov on 10.04.2017.
 */

public class SetupDepartmentTransferController implements Initializable {
    private TransferDepartmentService service;
    @FXML
    private TableView setupDepartmentTransfer;
    @FXML
    private TableColumn fromDepartment;
    @FXML
    private TableColumn intoDepartment;


    public  SetupDepartmentTransferController(){
        this.service = ApplicationController.getInstance().getCtx().getBean(TransferDepartmentService.class);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
         //departmentAit.getCellFactory(ComboBoxTableCell.forTableColumn())

        ObjectProperty<SetupDepartmentForTransfer> transferObjectProperty = new SimpleObjectProperty<>();
        fromDepartment.setCellValueFactory(new PropertyValueFactory<SetupDepartmentForTransfer,Object>("departmentTransferKey.fromDepartment"));
        intoDepartment.setCellValueFactory(new PropertyValueFactory<SetupDepartmentForTransfer,Object>("departmentTransferKey"));

        setupDepartmentTransfer.getItems().addAll(service.findAll());
    }
}
