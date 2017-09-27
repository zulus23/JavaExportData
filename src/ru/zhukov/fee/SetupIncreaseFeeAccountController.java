package ru.zhukov.fee;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import ru.zhukov.action.Action;
import ru.zhukov.domain.IncreaseKindPay;
import ru.zhukov.domain.KindPay;
import ru.zhukov.service.IncreaseFeeAccountService;
import ru.zhukov.utils.Account;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class SetupIncreaseFeeAccountController implements Initializable{


    private IncreaseFeeAccountService feeAccountService;


    @FXML
    private TableView setupAccountFee;
    @FXML
    private TableColumn<IncreaseKindPay,KindPay> codeAccount;
  /*  @FXML
    private TableColumn<IncreaseKindPay,String> nameAccount;*/




    public SetupIncreaseFeeAccountController(IncreaseFeeAccountService increaseFeeAccountService) {
         this.feeAccountService = increaseFeeAccountService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setupAccountFee.setEditable(true);
        CompletableFuture.runAsync(()->{
            Platform.runLater(()->{
                setupAccountFee.getItems().addAll(feeAccountService.increaseKindPayList());});
        });

        ObservableList<KindPay> kindPay = FXCollections.observableArrayList(feeAccountService.kindPayList());
        codeAccount.setCellFactory(ComboBoxTableCell.forTableColumn(kindPay));
/*
        codeAccount.setCellValueFactory(cellData -> {
            IncreaseKindPay increaseKindPay =  cellData.getValue();
            return new SimpleObjectProperty(increaseKindPay.getKindPay().getCode());
        });*/
        codeAccount.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<IncreaseKindPay, KindPay>, ObservableValue<KindPay>>() {
            @Override
            public ObservableValue<KindPay> call(TableColumn.CellDataFeatures<IncreaseKindPay, KindPay> param) {
                KindPay temp = param.getValue().getKindPay();
                return new SimpleObjectProperty<>(temp);
            }
        });
        codeAccount.setOnEditCommit(event -> {
            TablePosition<IncreaseKindPay, KindPay> pos = event.getTablePosition();
             KindPay newKindPay = event.getNewValue();
             int row = pos.getRow();
             IncreaseKindPay increaseKindPay = event.getTableView().getItems().get(row);
             increaseKindPay.setKindPay(newKindPay);
             feeAccountService.saveIncreaseKindPay(increaseKindPay).whenComplete((object,error)->{
               Optional.ofNullable(error).ifPresent((e)->{
                   Platform.runLater(()-> {
                       Action.showErrorInformation(String.format("Сохранить не могу. Причина %s%n",e.getMessage()));
                       event.getTableView().getItems().remove(row);
                   });

               });
             });
        });

       /* nameAccount.setCellValueFactory(cellData -> {
            IncreaseKindPay increaseKindPay =  cellData.getValue();
            return new SimpleObjectProperty(Optional.ofNullable(increaseKindPay).map(e->e.getKindPay()).map(e ->e.getName()).orElse(""));
        });*/
        setupAccountFee.setOnKeyPressed(event -> {
           if(event.getCode().equals(KeyCode.INSERT)){
               setupAccountFee.getItems().add(new IncreaseKindPay());

               setupAccountFee.requestFocus();

               setupAccountFee.getSelectionModel().select(setupAccountFee.getItems().size()-1);

               setupAccountFee.getFocusModel().focus(setupAccountFee.getItems().size()-1);

           }
            if(event.getCode().equals(KeyCode.DELETE)){
               TableView.TableViewSelectionModel<IncreaseKindPay> selectionModel =  setupAccountFee.getSelectionModel();
               if (!selectionModel.isEmpty()){
                   IncreaseKindPay deleteIncreasePay = selectionModel.getSelectedItem();

                   Action.deleteMessage(deleteIncreasePay.toString()).ifPresent((buttonType) ->{
                       if(buttonType == ButtonType.OK){
                           feeAccountService.deleteIncreaseKindPay(deleteIncreasePay).whenComplete((object,error)->{
                               if(error != null){
                                    Platform.runLater(()-> {
                                       Action.showErrorInformation(String.format("Удалить не могу. Причина %s%n",error.getMessage()));
                                    });
                               } else {
                                   setupAccountFee.getItems().remove(deleteIncreasePay);
                               }
                           });

                       }});
                   };

               }
             System.out.println("Deleting");

        });
    }
}
