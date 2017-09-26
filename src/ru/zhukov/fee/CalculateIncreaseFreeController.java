package ru.zhukov.fee;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.zhukov.domain.Employee;
import ru.zhukov.service.TariffIncreaseService;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

public class CalculateIncreaseFreeController implements Initializable{

    private TariffIncreaseService increaseService;

    @FXML
    private TableView<Employee> employeeIncreaseFee;
    @FXML
    private TableColumn<Employee,String> employee;
    @FXML
    private TableColumn department;
    @FXML
    private TableColumn position;


    @FXML
    private TableColumn<Employee,Integer> rankCurrent;
    @FXML
    private TableColumn rankByTariff;
    @FXML
    private TableColumn tariffByPosition;
    @FXML
    private TableColumn tariffByPerson;
    @FXML
    private TableColumn coefficient;

    private LocalDateTime dateMothCalculate;

    public CalculateIncreaseFreeController(TariffIncreaseService service, LocalDateTime dateTimeMonth) {
        this.increaseService = service;
        this.dateMothCalculate = dateTimeMonth;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CompletableFuture.runAsync(()->{
            Platform.runLater(()->{
                employeeIncreaseFee.getItems().addAll(increaseService.employeeListNeedIncreaseTarif(dateMothCalculate));});
        });

        employee.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        department.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        position.setCellValueFactory(new PropertyValueFactory<>("positionName"));
        rankCurrent.setCellValueFactory(new PropertyValueFactory<>("currentRank"));
        rankByTariff.setCellValueFactory(new PropertyValueFactory<>("rankByTariff"));
        tariffByPosition.setCellValueFactory(new PropertyValueFactory<>("salaryByPosition"));
        tariffByPerson.setCellValueFactory(new PropertyValueFactory<>("salary"));

    }
}
