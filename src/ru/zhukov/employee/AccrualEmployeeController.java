package ru.zhukov.employee;

import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.zhukov.domain.AccountRecord;
import ru.zhukov.domain.AccrualEmployee;
import ru.zhukov.repository.AccountRepository;
import ru.zhukov.service.AccountRecordDataService;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Gukov on 29.04.2016.
 */
public class AccrualEmployeeController implements Initializable {

    private final AccountRecordDataService service;
    private final int month;
    private final int year;


    @FXML
    private TableView<AccrualEmployee> employeeTable;

    @FXML
    private TableColumn<AccrualEmployee,String> department;
    @FXML
    private TableColumn<AccrualEmployee,String> tabelN;

    @FXML
    private TableColumn<AccrualEmployee,String> employee;

    @FXML
    private TableColumn<AccrualEmployee,String> account;
    @FXML
    private TableColumn<AccrualEmployee,String> accountReceiver;
    @FXML
    private TableColumn<AccrualEmployee,String> code;
    @FXML
    private TableColumn<AccrualEmployee,String> codeName;
    @FXML
    private TableColumn<AccrualEmployee,String> receiver;
    @FXML
    private TableColumn<AccrualEmployee,Double> summa;
    @FXML
    private TableColumn<AccrualEmployee,String> office;
    @FXML
    private TableColumn<AccrualEmployee,String> branchOffice;



    public AccrualEmployeeController(AccountRecordDataService service,int month, int year) {
        this.service = service;
        this.month = month;
        this.year = year;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tabelN.setCellValueFactory(new PropertyValueFactory<AccrualEmployee,String>("tabel"));

        department.setCellValueFactory(new PropertyValueFactory<AccrualEmployee,String>("department"));
        employee.setCellValueFactory(new PropertyValueFactory<AccrualEmployee,String>("nameEmployee"));
        code.setCellValueFactory(new PropertyValueFactory<AccrualEmployee,String>("code"));
        codeName.setCellValueFactory(new PropertyValueFactory<AccrualEmployee,String>("codeName"));
        account.setCellValueFactory(new PropertyValueFactory<AccrualEmployee,String>("accountNumber"));
        accountReceiver.setCellValueFactory(new PropertyValueFactory<AccrualEmployee,String>("accountByBank"));
        office.setCellValueFactory(new PropertyValueFactory<AccrualEmployee,String>("office"));
        branchOffice.setCellValueFactory(new PropertyValueFactory<AccrualEmployee,String>("branchOffice"));
        summa.setCellValueFactory(new PropertyValueFactory<AccrualEmployee,Double>("summa"));

        receiver.setCellValueFactory(new PropertyValueFactory<AccrualEmployee,String>("fullNameReceiver"));

        CompletableFuture.runAsync(()->{
            Platform.runLater(()->{
                employeeTable.getItems().addAll(service.accrualEmployeeListByMonthAndYear(month,year));});});

    }






}

