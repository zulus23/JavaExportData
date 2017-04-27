package ru.zhukov.transfer;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import ru.zhukov.ApplicationController;
import ru.zhukov.domain.SetupAccountForTransfer;
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
    private TableView<SetupDepartmentForTransfer> setupDepartmentTransfer;
    @FXML
    private TableColumn<SetupDepartmentForTransfer,String> fromDepartment;
    @FXML
    private TableColumn<SetupDepartmentForTransfer,String> intoDepartment;
    @FXML
    private TableColumn<SetupDepartmentForTransfer,String> fromDepartmentCFO;

    public  SetupDepartmentTransferController(){
        this.service = ApplicationController.getInstance().getCtx().getBean(TransferDepartmentService.class);
    }

    private SetupDepartmentForTransfer departmentForTransfer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
         //departmentAit.getCellFactory(ComboBoxTableCell.forTableColumn())
        setupDepartmentTransfer.setEditable(true);
        ObjectProperty<SetupDepartmentForTransfer> transferObjectProperty = new SimpleObjectProperty<>();
        fromDepartment.setCellValueFactory(new PropertyValueFactory<>("fromDepartment"));
        fromDepartment.setCellFactory(TextFieldTableCell.<SetupDepartmentForTransfer>forTableColumn());
        intoDepartment.setCellValueFactory(new PropertyValueFactory<>("toDepartment"));
        intoDepartment.setCellFactory(TextFieldTableCell.<SetupDepartmentForTransfer>forTableColumn());
        fromDepartmentCFO.setCellValueFactory(new PropertyValueFactory<>("departmentCFO"));

        intoDepartment.setStyle("-fx-alignment: CENTER;");
        fromDepartment.setStyle("-fx-alignment: CENTER;");
        fromDepartmentCFO.setStyle("-fx-alignment: CENTER;");

        fromDepartment.setOnEditCommit(e -> {
            departmentForTransfer = setupDepartmentTransfer.getItems().stream()
                                                                      .filter(v -> v.getFromDepartment().equals(e.getOldValue())).findFirst()
                                                                      .orElseThrow(() ->{
                                                                                return new RuntimeException("I don't give number department");}
                                                                              );
            departmentForTransfer.setFromDepartment(e.getNewValue());
            service.save(departmentForTransfer);

        });

        setupDepartmentTransfer.getItems().addAll(service.findAll());
    }
}
