package ru.zhukov.transfer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.table.TableFilter;
import ru.zhukov.ApplicationController;
import ru.zhukov.domain.SetupCostItemTransfer;
import ru.zhukov.service.TransferCostItemService;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Gukov on 27.04.2017.
 */
public class SetupCostItemTransferController implements Initializable {
    private TransferCostItemService costItemService;
    @FXML
    private TableView<SetupCostItemTransfer> setupAccountTransferTableView;
    @FXML
    private TableColumn<SetupCostItemTransfer,String> zatrFromAit;
    @FXML
    private TableColumn<SetupCostItemTransfer,String> zatrTo1C;

    @FXML
    private TableColumn<SetupCostItemTransfer,String> debit;
    @FXML
    private TableColumn<SetupCostItemTransfer,String> credit;
    @FXML
    private TableColumn<SetupCostItemTransfer,String> code;


    private TableFilter tableFilter;

    public SetupCostItemTransferController() {
        this.costItemService = ApplicationController.getInstance().getCtx().getBean(TransferCostItemService.class);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        zatrFromAit.setCellValueFactory(new PropertyValueFactory<>("zatrFromAit"));
        zatrFromAit.setStyle("-fx-alignment: CENTER;");
        zatrTo1C.setCellValueFactory(new PropertyValueFactory<>("zatrTo1C"));
        zatrTo1C.setStyle("-fx-alignment: CENTER;");
        debit.setCellValueFactory(new PropertyValueFactory<>("debit"));
        debit.setStyle("-fx-alignment: CENTER;");
        credit.setCellValueFactory(new PropertyValueFactory<>("credit"));
        credit.setStyle("-fx-alignment: CENTER;");
        code.setCellValueFactory(new PropertyValueFactory<>("code"));
        code.setStyle("-fx-alignment: CENTER;");
        ObservableList<SetupCostItemTransfer> setupCostItemTransfers = FXCollections.observableArrayList(costItemService.findAll());

        setupAccountTransferTableView.setItems(setupCostItemTransfers);
         TableFilter.forTableView(this.setupAccountTransferTableView).apply();

    }
}
