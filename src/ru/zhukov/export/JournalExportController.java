package ru.zhukov.export;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.zhukov.domain.JournalExportDetail;
import ru.zhukov.domain.JournalExportHeader;
import ru.zhukov.service.JournalExportDataService;

import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Gukov on 14.04.2016.
 */
public class JournalExportController implements Initializable {

    @FXML
    private TableView<JournalExportHeader>  tHeader;
    @FXML
    private TableView<JournalExportDetail>  tDetail;


    @FXML
    private TableColumn<JournalExportHeader,Long> thNumberOrder;
    @FXML
    private TableColumn<JournalExportHeader,String> thNumberJournal;
    @FXML
    private TableColumn<JournalExportHeader,String> thImportBatch;
    @FXML
    private TableColumn<JournalExportHeader,String> thDescription;
    @FXML
    private TableColumn<JournalExportDetail,Long>   tdNumberOrder;
    @FXML
    private TableColumn<JournalExportDetail,Date>   tdData;
    @FXML
    private TableColumn<JournalExportDetail,String> tdDebit;
    @FXML
    private TableColumn<JournalExportDetail,String> tdCredit;
    @FXML
    private TableColumn<JournalExportDetail,String> tdCurrentCode;
    @FXML
    private TableColumn<JournalExportDetail,Double> tdSumma;
    @FXML
    private TableColumn<JournalExportDetail,String> tdDescription;
    @FXML
    private TableColumn<JournalExportDetail,String> tdDimension;
    @FXML
    private TableColumn<JournalExportDetail,String> tdDimension2;
    @FXML
    private TableColumn<JournalExportDetail,String> tdDimension4;
    @FXML
    private TableColumn<JournalExportDetail,String> tdDimension7;


    private ObjectProperty<JournalExportHeader> journalExportHeaderObjectProperty;



    private final JournalExportDataService dataService;
    private String dimension;


    public JournalExportController(JournalExportDataService dataService,String dimension){
        this.dataService = dataService;
        this.dimension = dimension;
    }

    public void refreshData(){
        tDetail.getItems().clear();
        tHeader.getItems().clear();
        tHeader.getItems().addAll(dataService.allJournal(dimension));

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        journalExportHeaderObjectProperty = new SimpleObjectProperty<>();

        thNumberOrder.setCellValueFactory(new PropertyValueFactory<JournalExportHeader,Long>("recNo"));
        thNumberJournal.setCellValueFactory(new PropertyValueFactory<JournalExportHeader,String>("parentRecId"));
        thImportBatch.setCellValueFactory(new PropertyValueFactory<JournalExportHeader,String>("importBatch"));
        thDescription.setCellValueFactory(new PropertyValueFactory<JournalExportHeader,String>("name"));

        tdNumberOrder.setCellValueFactory(new PropertyValueFactory<JournalExportDetail,Long>("recNo"));
        tdDebit.setCellValueFactory(new PropertyValueFactory<JournalExportDetail,String>("debit"));
        tdCredit.setCellValueFactory(new PropertyValueFactory<JournalExportDetail,String>("credit"));
        tdData.setCellValueFactory(new PropertyValueFactory<JournalExportDetail,Date>("transDate"));
        tdSumma.setCellValueFactory(new PropertyValueFactory<JournalExportDetail,Double>("summa"));
        tdCurrentCode.setCellValueFactory(new PropertyValueFactory<JournalExportDetail,String>("currentCode"));
        tdDescription.setCellValueFactory(new PropertyValueFactory<JournalExportDetail,String>("text"));
        tdDimension.setCellValueFactory(new PropertyValueFactory<JournalExportDetail,String>("transDimension"));
        tdDimension2.setCellValueFactory(new PropertyValueFactory<JournalExportDetail,String>("transDimension2"));
        tdDimension4.setCellValueFactory(new PropertyValueFactory<JournalExportDetail,String>("transDimension4"));
        tdDimension7.setCellValueFactory(new PropertyValueFactory<JournalExportDetail,String>("transDimension7"));
        //tdDimension8.setCellValueFactory(new PropertyValueFactory<JournalExportDetail,String>("transDimension8"));

        tHeader.getItems().addAll(dataService.allJournal(dimension));


        tHeader.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (Optional.ofNullable(newValue).isPresent()) {
                tDetail.getItems().addAll(dataService.listDetailJournal(newValue.getParentRecId()));
            }
                journalExportHeaderObjectProperty.set(newValue);

        });

        /*
        ObservableList<JournalExportHeader> observableList = tHeader.getSelectionModel().getSelectedItems();
        observableList.addListener( new ListChangeListener<JournalExportHeader>() {
            @Override
            public void onChanged(Change<? extends JournalExportHeader> c) {
                 System.out.println("----------------------------------------------------");

                tDetail.getItems().addAll(dataService.listDetailJournal(
                        c.getList().stream().map(e -> e.getParentRecId()).findAny().orElse("")));
            }
        });
         */
    }

    public JournalExportHeader getJournalExportHeaderObjectProperty() {
        return journalExportHeaderObjectProperty.get();
    }

    public ObjectProperty<JournalExportHeader> journalExportHeaderObjectPropertyProperty() {
        return journalExportHeaderObjectProperty;
    }

}
