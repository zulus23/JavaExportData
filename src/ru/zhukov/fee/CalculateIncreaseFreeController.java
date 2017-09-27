package ru.zhukov.fee;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.table.TableFilter;
import ru.zhukov.action.Action;
import ru.zhukov.domain.Employee;
import ru.zhukov.domain.KindPay;
import ru.zhukov.service.TariffIncreaseService;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
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
    @FXML
    private TableColumn increaseSummaFee;
    @FXML
    private Button bCalculate;
    @FXML
    private Button bMakeAddToPersonCart;

    @FXML
    private StackPane stackPane;

    private MaskerPane masker;


    private LocalDateTime dateMothCalculate;

    public CalculateIncreaseFreeController(TariffIncreaseService service, LocalDateTime dateTimeMonth) {
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
            Platform.runLater(()-> {
                bMakeAddToPersonCart.setDisable(true);
                bCalculate.setDisable(true);
                masker.setVisible(true);
            });
           return increaseService.employeeListNeedIncreaseTarif(dateMothCalculate);
        }).thenAcceptAsync((e)->{
            Platform.runLater(()->{
                employeeIncreaseFee.getItems().addAll(e);
                TableFilter tableFilter = TableFilter.forTableView(employeeIncreaseFee).lazy(true).apply();
            });
        }).whenComplete((object,erroe)->{
            Platform.runLater(()-> {
                bMakeAddToPersonCart.setDisable(false);
                bCalculate.setDisable(false);
                masker.setVisible(false);
            });
            employee.setCellValueFactory(new PropertyValueFactory<>("fullName"));
            department.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
            position.setCellValueFactory(new PropertyValueFactory<>("positionName"));
            rankCurrent.setCellValueFactory(new PropertyValueFactory<>("currentRank"));
            rankByTariff.setCellValueFactory(new PropertyValueFactory<>("rankByTariff"));
            tariffByPosition.setCellValueFactory(new PropertyValueFactory<>("salaryByPosition"));
            tariffByPerson.setCellValueFactory(new PropertyValueFactory<>("salary"));
            coefficient.setCellValueFactory(new PropertyValueFactory<>("coefficient"));
            increaseSummaFee.setCellValueFactory(new PropertyValueFactory<>("increaseSummaFee"));
        });

        employee.setStyle("-fx-alignment: CENTER-LEFT;");
        department.setStyle("-fx-alignment: CENTER-LEFT;");
        position.setStyle("-fx-alignment: CENTER-LEFT;");
        rankCurrent.setStyle("-fx-alignment: CENTER;");
        rankByTariff.setStyle("-fx-alignment: CENTER;");
        tariffByPosition.setStyle("-fx-alignment: CENTER;");
        tariffByPerson.setStyle("-fx-alignment: CENTER;");
        coefficient.setStyle("-fx-alignment: CENTER;");
        increaseSummaFee.setStyle("-fx-alignment: CENTER;");





    }

    private void makeAddToPersonCart(ActionEvent actionEvent) {
        Action.selectKindPay(increaseService.selectKindPayLess500()).ifPresent((e) ->{
            System.out.println("e.getCode() = " + e.getCode());
        });
    }

    private void calculateIncreaseFee(ActionEvent actionEvent) {
        List<KindPay> kindPays = increaseService.listKindPayIncreaseFee();
         employeeIncreaseFee.getItems().stream().forEach(e -> {
             double s =  e.getCaclucateFees().stream().filter(code -> kindPays.contains(code.getKindPay()))
                                          .mapToDouble(emp->emp.getSumma().doubleValue())
                                          .sum();
             e.setIncreaseSummaFee(new BigDecimal(s).multiply(e.getCoefficient().remainder(new BigDecimal(1))).round(new MathContext(4,RoundingMode.HALF_UP)));
         });
         employeeIncreaseFee.refresh();
    }
}
