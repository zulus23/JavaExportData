package ru.zhukov.account;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.application.Platform;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.table.TableFilter;
import ru.zhukov.domain.AccountRecord;
import ru.zhukov.service.AccountRecordDataService;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Gukov on 29.03.2016.
 */
public class AccountRecordController  implements Initializable{

    private AccountRecordDataService dataService;


    @FXML
    TableView<AccountRecord> accountRecordTable;
    @FXML
    TableColumn department;
    @FXML
    TableColumn employee;
    @FXML
    TableColumn debit;
    @FXML
    TableColumn credit;
    @FXML
    TableColumn description;
    @FXML
    TableColumn<AccountRecord,Number> summa;
    @FXML
    TableColumn<AccountRecord,String> costItem;
    @FXML
    TableColumn<AccountRecord,String> objectId;
    @FXML
    TableColumn<AccountRecord,String> taxArticle;
    @FXML
    TableColumn<AccountRecord,String> cfo;
    @FXML
    TableColumn<AccountRecord,String> code;


    @FXML
    TextField allSumma;

    @FXML
    StackPane stackPane;

    private MaskerPane masker;


    private int month;
    private int year;

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

   // private TableFilter tableFilter;

    public AccountRecordController(AccountRecordDataService dataService,int month, int year){
        this.dataService = dataService;
        this.month = month;
        this.year = year;

        masker = new MaskerPane();
        masker.setVisible(false);
        masker.setText("Выполняю загрузку. Ожидайте...");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


          department.setCellValueFactory(new PropertyValueFactory<AccountRecord,String>("department"));
          employee.setCellValueFactory(new PropertyValueFactory<AccountRecord,String>("employee"));
          debit.setCellValueFactory(new PropertyValueFactory<AccountRecord,String>("debit"));
          debit.setStyle("-fx-alignment: CENTER;");
          credit.setCellValueFactory(new PropertyValueFactory<AccountRecord,String>("credit"));
          credit.setStyle("-fx-alignment: CENTER;");
          summa.setCellValueFactory(new PropertyValueFactory<AccountRecord,Number>("summa"));
          summa.setStyle("-fx-alignment: CENTER-RIGHT;");

          /*summa.setCellFactory(cellData -> {
              TableCell<AccountRecord,Number> cell = new TableCell<AccountRecord, Number>(){
                  @Override
                  protected void updateItem(Number item, boolean empty) {
                      super.updateItem(item, empty);
                      if(!empty){
                          this.setText(item == null ? "" : NumberFormat.getNumberInstance().format(item));

                      }
                  }
              };
            return cell;
          });*/
          description.setCellValueFactory(new PropertyValueFactory<AccountRecord,String>("description"));
          costItem.setCellValueFactory(new PropertyValueFactory<AccountRecord,String>("costItem"));
          costItem.setStyle("-fx-alignment: CENTER;");
          objectId.setCellValueFactory(new PropertyValueFactory<AccountRecord,String>("objectId"));
          objectId.setStyle("-fx-alignment: CENTER;");
          taxArticle.setCellValueFactory(new PropertyValueFactory<AccountRecord,String>("taxArticle"));
          taxArticle.setStyle("-fx-alignment: CENTER;");
          cfo.setCellValueFactory(new PropertyValueFactory<AccountRecord,String>("cfo"));
          cfo.setStyle("-fx-alignment: CENTER;");
          code.setCellValueFactory(new PropertyValueFactory<AccountRecord,String>("code"));
          code.setStyle("-fx-alignment: CENTER;");

          stackPane.getChildren().add(masker);


          //allSumma.layoutXProperty().bind(summa.getProperties().);
        //masker.setVisible(true);

        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(() ->{
            Platform.runLater(()-> masker.setVisible(true));
            List<AccountRecord> accountRecords = dataService.accountRecordListByMonthAndYear(month,year);
            Platform.runLater(()-> {
                this.accountRecordTable.getItems().addAll(accountRecords);
                TableFilter tableFilter = TableFilter.forTableView(accountRecordTable).lazy(true).apply();
                this.setListener(tableFilter.getFilteredList());
            });


            Platform.runLater(()-> masker.setVisible(false));

        });


        if (service != null) service.shutdown();


//        CompletableFuture.supplyAsync(()-> {
//                                             Platform.runLater(()-> masker.setVisible(true));
//                                             return dataService.accountRecordListByMonthAndYear(month,year);})
//
//                          .thenAcceptAsync(e ->Platform.runLater(()->{
//                              masker.setVisible(false);
//                              this.accountRecordTable.getItems().addAll(e);
//                              tableFilter =  TableFilter.forTableView(accountRecordTable).apply();
//                              setListener(tableFilter.getFilteredList());
//                          }
//                          ));
//
//

        //  this.accountRecordTable.getItems().addAll(dataService.accountRecordListByMonthAndYear(month,year));





    }

    private void setListener(FilteredList list){
        //ObservableList<AccountRecord> accountRecords =  tableFilter.getFilteredList();
        list.addListener(new ListChangeListener<AccountRecord>() {
            @Override
            public void onChanged(Change<? extends AccountRecord> c) {

              //FilteredList<AccountRecord> filteredList =  tableFilter.getFilteredList();
                NumberFormat f = DecimalFormat.getInstance();
                f.setGroupingUsed(true);
                allSumma.setText( f.format( c.getList().stream().mapToDouble(a -> a.getSumma()).sum()));
            }
        });
    }

//    public List<AccountRecord> getAccountRecordTable() {
//        return tableFilter.getFilteredList();
//    }
}
