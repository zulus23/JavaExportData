package ru.zhukov.transfer;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import org.controlsfx.control.table.TableFilter;
import ru.zhukov.ApplicationController;
import ru.zhukov.domain.SetupAccountForTransfer;
import ru.zhukov.service.TransferAccountService;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Gukov on 10.04.2017.
 */

public class SetupAccountTransferController implements Initializable {
    private TransferAccountService service;
    @FXML
    private TableView<SetupAccountForTransfer> setupAccountTransferTableView;
    @FXML
    private TableColumn<SetupAccountForTransfer,String> accountOut;
    @FXML
    private TableColumn<SetupAccountForTransfer,String> accountIn;

    @FXML
    private TableColumn<SetupAccountForTransfer,String> nameAnalytic;

    public SetupAccountTransferController(){
        this.service = ApplicationController.getInstance().getCtx().getBean(TransferAccountService.class);
    }
    private SetupAccountForTransfer setupAccountForTransfer;

    private ObservableList<SetupAccountForTransfer> observableList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
         //departmentAit.getCellFactory(ComboBoxTableCell.forTableColumn())
        setupAccountTransferTableView.setEditable(true);
        accountOut.setOnEditCommit(e -> {
          setupAccountForTransfer = setupAccountTransferTableView.getItems().stream().filter(a -> a.getAccountOut().equals(e.getOldValue())).findFirst().get();
          setupAccountForTransfer.setAccountOut(e.getNewValue());
           service.save(setupAccountForTransfer);
         });

        accountIn.setOnEditCommit(e -> {
            setupAccountForTransfer = setupAccountTransferTableView.getItems().stream().filter(a -> a.getAccountIn().equals(e.getOldValue())).findFirst().get();
            setupAccountForTransfer.setAccountIn(e.getNewValue());
            service.save(setupAccountForTransfer);
        });

        /*accountOut.setCellValueFactory((cellData) -> {
              SetupAccountForTransfer accountTranssfer = cellData.getValue();
              return new SimpleObjectProperty(accountTranssfer.getTransferKey());
        });*/
        accountOut.setCellValueFactory(new PropertyValueFactory<SetupAccountForTransfer,String>("accountOut"));
        accountOut.setCellFactory(TextFieldTableCell.<SetupAccountForTransfer>forTableColumn());
        accountOut.setStyle("-fx-alignment: CENTER;");
        /*accountIn.setCellValueFactory((cellData) -> {
            SetupAccountForTransfer accountTranssfer = cellData.getValue();
            return  new SimpleObjectProperty(accountTranssfer.getTransferKey().getAccountIn());
        });*/
        accountIn.setCellValueFactory(new PropertyValueFactory<SetupAccountForTransfer,String>("accountIn"));
        accountIn.setCellFactory(TextFieldTableCell.<SetupAccountForTransfer>forTableColumn());
        accountIn.setStyle("-fx-alignment: CENTER;");
        nameAnalytic.setCellValueFactory(new PropertyValueFactory<SetupAccountForTransfer,String>("nameAnalytic"));
        setupAccountTransferTableView.getItems().addAll(service.findAll());
        TableFilter.forTableView(setupAccountTransferTableView).lazy(true).apply();

    }
}
