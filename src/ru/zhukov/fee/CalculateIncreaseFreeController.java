package ru.zhukov.fee;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.table.TableFilter;
import ru.zhukov.action.Action;
import ru.zhukov.domain.Employee;
import ru.zhukov.service.TariffIncreaseServiceable;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

public class CalculateIncreaseFreeController implements Initializable{

    private TariffIncreaseServiceable increaseService;




    @FXML
    private TableView<Employee> employeeIncreaseFee;
    @FXML
    private TableColumn<Employee,String>  employeeNumber;
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
    @FXML
    private TableColumn increaseSummaFeeOne;
    @FXML
    private TableColumn increaseSummaFeeTwo;
    @FXML
    private Button bCalculate;
    @FXML
    private Button bMakeAddToPersonCart;

    @FXML
    private StackPane stackPane;

    private MaskerPane masker;



    private LocalDateTime dateMothCalculate;

    public CalculateIncreaseFreeController(TariffIncreaseServiceable service, LocalDateTime dateTimeMonth) {
        this.increaseService = service;
        this.dateMothCalculate = dateTimeMonth;
        masker = new MaskerPane();
        masker.setVisible(false);
        masker.setText("Выполняю загрузку. Ожидайте...");

    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {



        bCalculate.setGraphic(new ImageView(new Image(getClass().getResource("/ru/zhukov/assests/image32/table_loan.png").toExternalForm())));
        bCalculate.setTooltip(new Tooltip("Выполнить расчет суммы надбавки"));
        bCalculate.setOnAction(this::calculateIncreaseFee);


        bMakeAddToPersonCart.setGraphic(new ImageView(new Image(getClass().getResource("/ru/zhukov/assests/image32/table_loan-add2.png").toExternalForm())));
        bMakeAddToPersonCart.setTooltip(new Tooltip("Начислить надбавку"));
        bMakeAddToPersonCart.setOnAction(this::makeAddToPersonCart);
        stackPane.getChildren().add(masker);



        CompletableFuture.supplyAsync(() -> {
            showMessageAboutWork("Выполняю загрузку. Ожидайте...");

           return increaseService.employeeListNeedIncreaseTarif(dateMothCalculate);
        }).thenAcceptAsync((e)->{
            Platform.runLater(()->{
                employeeIncreaseFee.getItems().addAll(e);
                TableFilter tableFilter = TableFilter.forTableView(employeeIncreaseFee).lazy(true).apply();
            });
        }).thenAcceptAsync(e -> {

        })
          .whenComplete((object,error)->{
            hideMessageAboutWork();
            if(error != null){
                Platform.runLater(()-> {
                    Action.showErrorInformation(error.getCause().getMessage());
                });
            }
                  employeeNumber.setCellValueFactory(new PropertyValueFactory<>("tabelNumber"));
                  employee.setCellValueFactory(new PropertyValueFactory<>("fullName"));
                  department.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
                  position.setCellValueFactory(new PropertyValueFactory<>("positionName"));
                  rankCurrent.setCellValueFactory(new PropertyValueFactory<>("currentRank"));
                  rankByTariff.setCellValueFactory(new PropertyValueFactory<>("rankByTariff"));
                  tariffByPosition.setCellValueFactory(new PropertyValueFactory<>("salaryByPosition"));
                  tariffByPerson.setCellValueFactory(new PropertyValueFactory<>("salary"));
                  coefficient.setCellValueFactory(new PropertyValueFactory<>("coefficient"));
                  increaseSummaFeeOne.setCellValueFactory(new PropertyValueFactory<>("increaseSummaFeeOne"));
                  increaseSummaFeeTwo.setCellValueFactory(new PropertyValueFactory<>("increaseSummaFeeTwo"));


        });
        employeeNumber.setStyle("-fx-alignment: CENTER-LEFT;");
        employee.setStyle("-fx-alignment: CENTER-LEFT;");
        department.setStyle("-fx-alignment: CENTER-LEFT;");
        position.setStyle("-fx-alignment: CENTER-LEFT;");
        rankCurrent.setStyle("-fx-alignment: CENTER;");
        rankByTariff.setStyle("-fx-alignment: CENTER;");
        tariffByPosition.setStyle("-fx-alignment: CENTER;");
        tariffByPerson.setStyle("-fx-alignment: CENTER;");
        coefficient.setStyle("-fx-alignment: CENTER;");
        increaseSummaFeeOne.setStyle("-fx-alignment: CENTER;");
        increaseSummaFeeTwo.setStyle("-fx-alignment: CENTER;");





    }

    public List<Employee> printDataSource(){
        return new ArrayList<>(employeeIncreaseFee.getItems());
    }

    private void makeAddToPersonCart(ActionEvent actionEvent) {
        /*Action.selectKindPay(increaseService.selectKindPayLess500()).ifPresent((kindPayConsumer) ->{
            CompletableFuture.runAsync(()->{
                        showMessageAboutWork("Выполняю начисление. Ожидайте...");
                        increaseService.makeIncreaseFeeByEmployee(employeeIncreaseFee.getItems(),kindPayConsumer,dateMothCalculate);
                    })
                    .whenComplete((obj,err)->{
                        hideMessageAboutWork();
                       if (err != null){
                          Action.showErrorInformation(err.getMessage());
                       }else{

                       }
                    });
        });*/
        Action.confirmationAction("Наичисление "). ifPresent((response) ->{
            if(response == ButtonType.OK){
            CompletableFuture.runAsync(()->{
                showMessageAboutWork("Выполняю начисление. Ожидайте...");

                increaseService.makeIncreaseFeeByEmployee(employeeIncreaseFee.getItems(),dateMothCalculate);
            })
                    .whenComplete((obj,err)->{
                        hideMessageAboutWork();
                        if (err != null){
                            Action.showErrorInformation(err.getMessage());
                        }else{

                        }
                    });
        }});
    }



    private void calculateIncreaseFee(ActionEvent actionEvent) {

        CompletableFuture.runAsync(()-> {
            showMessageAboutWork("Выполняю расчет. Ожидайте...");
            increaseService.calculateIncreaseFee(employeeIncreaseFee.getItems(),dateMothCalculate);
        }).whenComplete((obj,err)->{
            employeeIncreaseFee.refresh();
            hideMessageAboutWork();
        });

    }

    private void showMessageAboutWork(String message) {
        Platform.runLater(()-> {
            masker.setText(message);
            bMakeAddToPersonCart.setDisable(true);
            bCalculate.setDisable(true);
            masker.setVisible(true);
        });
    }
    private void hideMessageAboutWork() {
        Platform.runLater(()-> {
            bMakeAddToPersonCart.setDisable(false);
            bCalculate.setDisable(false);
            masker.setVisible(false);
        });
    }
}
