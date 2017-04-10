package ru.zhukov.base;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.util.StringConverter;
import ru.zhukov.account.AccountRecordController;
import ru.zhukov.account.ExportAccountRecordController;
import ru.zhukov.action.Action;
import ru.zhukov.config.ApplicationContextConfig;
import ru.zhukov.dto.CurrentUser;
import ru.zhukov.dto.ExportJournal;
import ru.zhukov.employee.AccrualEmployeeController;
import ru.zhukov.export.JournalExportController;
import ru.zhukov.repository.JDBCExportAccountRepository;
import ru.zhukov.service.AccountRecordDataService;
import ru.zhukov.service.JournalExportDataService;
import ru.zhukov.transfer.SetupDepartmentTransferController;
import ru.zhukov.utils.ImportIntoXLS;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.WeakHashMap;

import static javax.swing.UIManager.get;

/**
 * Created by Gukov on 24.03.2016.
 */

public class BasicApplicationController implements Initializable {

    private Map<Tab,AccountRecordController> accountRecordControllerWeakHashMap  = new WeakHashMap<>();
    private Map<Tab,JournalExportController> journalExportControllerWeakHashMap  = new WeakHashMap<>();

    private ObjectProperty<JournalExportController> currentJournalExportController;

    private ResourceBundle resourceBundle;

    @FXML
    private ToolBar tToolBar;

    @FXML
    private MenuItem miExit;
    @FXML
    private MenuItem miNewDocument;

    @FXML
    private MenuItem miPreferences;
    @FXML
    private MenuItem miPrintDocument;

    @FXML
    private MenuItem miViewAccount;
    @FXML
    private MenuItem miViewJournalExport;


    @FXML
    private MenuItem miViewTransferMoneyBankJournal;


    @FXML
    private MenuItem miCreateAccountRecord;
    @FXML
    private MenuItem miExportAccountRecord;

    @FXML
    private MenuItem miDeleteRecord;

    @FXML
    private MenuItem miClose;

    /* ------- Справочники -----------*/
    @FXML
    private MenuItem miCostHelper;
    @FXML
    private MenuItem miDepartmentSetup;
    @FXML
    private MenuItem miAccountHelper;

    /* ----------------------------- */




    @FXML
    private VBox mainWindow;

    @FXML
    private TabPane tpWindowContainer;

    private CreateAccountRecordTask createAccountRecordTask;
    private Button createAccountRecordButton;
    private DatePicker datePicker;
    private Tab currentTab;
    private ProgressBar progressBarCreateAccountRecord;


    private AccountRecordDataService dataService;

    private JournalExportDataService exportDataService;


    private int month;
    private int year;

    private CurrentUser currentUser;

    public BasicApplicationController(AccountRecordDataService dataService, CurrentUser currentUser){
        this.dataService = dataService;
        createAccountRecordTask = new CreateAccountRecordTask(this.dataService);
        this.currentUser = currentUser;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.resourceBundle = resources;
        miExit.setOnAction(Action::exit);
        miExit.setAccelerator(KeyCombination.keyCombination("Ctrl+F4"));
        miNewDocument.setGraphic(new ImageView(new Image(getClass().getResource("/ru/zhukov/assests/image16/document.png").toExternalForm())));
        miPreferences.setGraphic(new ImageView(new Image(getClass().getResource("/ru/zhukov/assests/image16/application-gear.png").toExternalForm())));
        miPrintDocument.setGraphic(new ImageView(new Image(getClass().getResource("/ru/zhukov/assests/image16/document-print.png").toExternalForm())));
        miPrintDocument.setOnAction(this::printFile);
        BooleanBinding emptyTab =  Bindings.isEmpty(tpWindowContainer.getTabs());
        miPrintDocument.disableProperty().bind(emptyTab);
        miClose.disableProperty().bind(emptyTab);
       // miExportAccountRecord.disableProperty().bind(Bindings.isEmpty(tpWindowContainer.getTabs()));
        miViewAccount.setGraphic(new ImageView(new Image(getClass().getResource("/ru/zhukov/assests/image16/account_book-customer.png").toExternalForm())));
        miViewAccount.setOnAction(this::showAccountRecordView);
        miViewJournalExport.setOnAction(this::showJournalExport);
        miDeleteRecord.setOnAction(this::deleteJournalExport);

        miCreateAccountRecord.setGraphic(new ImageView(new Image(getClass().getResource("/ru/zhukov/assests/image16/contract-execute.png").toExternalForm())));
        miCreateAccountRecord.setOnAction(this::createAccountRecord);

        miExportAccountRecord.setOnAction(this::exportAccountRecordAction);

        Button preferencesButton = new Button();

        preferencesButton.setGraphic(new ImageView(new Image(getClass().getResource("/ru/zhukov/assests/image32/application-gear.png").toExternalForm())));
        createAccountRecordButton = new Button();
        createAccountRecordButton.setGraphic(new ImageView(new Image(getClass().getResource("/ru/zhukov/assests/image32/contract-execute.png").toExternalForm())));
        createAccountRecordButton.setOnAction(this::createAccountRecord);
       // createAccountRecordButton.disableProperty().bind(createAccountRecordTask.runningProperty());

        Button showAccountRecordView = new Button();
        showAccountRecordView.setTooltip(new Tooltip(resourceBundle.getString("tooltip.showProvod")));
        showAccountRecordView.setGraphic(new ImageView(new Image(getClass().getResource("/ru/zhukov/assests/image32/account_book-customer.png").toExternalForm())));
        showAccountRecordView.setOnAction(this::showAccountRecordView);


        //preferencesButton.setTooltip(new Tooltip("Выход из приложения"));
        //preferencesButton.setOnAction(Action::exit);
        preferencesButton.setOnAction(Action::createAccountRecord);



       /* Button exitButton = new Button();

        exitButton.setGraphic(new ImageView(new Image(getClass().getResource("/ru/zhukov/assests/image/toolbar/folder_next.png").toExternalForm())));

        exitButton.setTooltip(new Tooltip("Выход из приложения"));
        exitButton.setOnAction(Action::exit);
 */
         datePicker = new DatePicker();
        datePicker.setTooltip(new Tooltip("Период"));
        datePicker.setValue(LocalDate.now());
        datePicker.setMinWidth(150);
        datePicker.setConverter(new MyStringConverter());
        datePicker.setEditable(false);


        //tToolBar.getItems().add(exitButton);
        tToolBar.getItems().add(createAccountRecordButton);
        tToolBar.getItems().add(showAccountRecordView);
        tToolBar.getItems().add(new Separator(Orientation.VERTICAL));
        tToolBar.getItems().add(datePicker);
        tToolBar.getItems().add(preferencesButton);
       // tToolBar.getItems().add(new Separator(Orientation.VERTICAL));
        currentJournalExportController = new SimpleObjectProperty<>();


        miCreateAccountRecord.disableProperty().bindBidirectional(createAccountRecordButton.disableProperty());
        tpWindowContainer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

              currentJournalExportController.set(journalExportControllerWeakHashMap.get(newValue));
               BooleanBinding bindings = Bindings.isNull(currentJournalExportController);
               if (currentJournalExportController.get() != null){
                 bindings =  Bindings.isNull(currentJournalExportController).and(currentJournalExportController.get().journalExportHeaderObjectPropertyProperty().isNull());
               }
               miDeleteRecord.disableProperty().bind(bindings);


        });

        miViewTransferMoneyBankJournal.setOnAction(this::showTransferMoneyBankJournal);

        miDepartmentSetup.setOnAction(this::showDepartmentSetupTransfer);


    }

    private void showDepartmentSetupTransfer(ActionEvent actionEvent) {
        FXMLLoader fxmlDepartmentSetupTransfer = new FXMLLoader(getClass().getResource("/ru/zhukov/transfer/SetupDepartmentTransferView.fxml"));
        SetupDepartmentTransferController transferController = new SetupDepartmentTransferController();
        fxmlDepartmentSetupTransfer.setController(transferController);

        try{

            AnchorPane departmentSetup =  fxmlDepartmentSetupTransfer.load();
            AnchorPane anchorPane = new AnchorPane();
            AnchorPane.setTopAnchor(departmentSetup, 0.0);
            AnchorPane.setLeftAnchor(departmentSetup, 0.0);
            AnchorPane.setRightAnchor(departmentSetup, 0.0);
            AnchorPane.setBottomAnchor(departmentSetup, 0.0);

            anchorPane.getChildren().add(departmentSetup);

            Tab tabDepartmentSetupTransfer = new Tab();

            tabDepartmentSetupTransfer.setText("Настройка соотвествия подразделений");
            tabDepartmentSetupTransfer.setContent(anchorPane);
            tpWindowContainer.setTabMinWidth(160);
            tpWindowContainer.setTabMaxWidth(160);


            tpWindowContainer.getTabs().addAll(tabDepartmentSetupTransfer);


        }catch(IOException ex){
           ex.printStackTrace();
        }
    }

    private void showTransferMoneyBankJournal(ActionEvent event) {
        month = datePicker.getValue().getMonthValue();
        year = datePicker.getValue().getYear();
        FXMLLoader fxmlTransferMoneyBankJournal = new FXMLLoader(getClass().getResource("/ru/zhukov/employee/AccrualEmployeeView.fxml"));

        AccrualEmployeeController accrualEmployeeController = new AccrualEmployeeController(dataService,month,year);
        fxmlTransferMoneyBankJournal.setController(accrualEmployeeController);
        try {
            AnchorPane transferAnchorPane =  fxmlTransferMoneyBankJournal.load();
            AnchorPane anchorPane = new AnchorPane();
            AnchorPane.setTopAnchor(transferAnchorPane, 0.0);
            AnchorPane.setLeftAnchor(transferAnchorPane, 0.0);
            AnchorPane.setRightAnchor(transferAnchorPane, 0.0);
            AnchorPane.setBottomAnchor(transferAnchorPane, 0.0);

            anchorPane.getChildren().add(transferAnchorPane);

            Tab tabJournalTransferTab = new Tab();

            tabJournalTransferTab.setText("Журнал перечисления в банк");
            tabJournalTransferTab.setContent(anchorPane);
            tpWindowContainer.setTabMinWidth(160);
            tpWindowContainer.setTabMaxWidth(160);


            tpWindowContainer.getTabs().addAll(tabJournalTransferTab);


        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteJournalExport(ActionEvent event) {
        String numberDocument = currentJournalExportController.get().getJournalExportHeaderObjectProperty().getParentRecId();

        //TODO Удаление журнала
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, String.format("Удалить журнал %s?",numberDocument));
        alert.setTitle("Внимание");
        alert.setHeaderText("");
        alert.showAndWait().filter(e -> e.getButtonData() == ButtonBar.ButtonData.OK_DONE).ifPresent(response -> {

            exportDataService.deleteJournal(currentJournalExportController.get().getJournalExportHeaderObjectProperty().getParentRecId(),
                    currentUser.getDatabase().getNameinAxapta());
            currentJournalExportController.get().refreshData();

        });

    }

    private void bindTaskCreateAccountRecord(){
        mainWindow.sceneProperty().getValue().getWindow().widthProperty().addListener( e ->{
            if(createAccountRecordTask.isRunning()){
                Double withProgressBar  = ((ReadOnlyDoubleProperty) e).getValue() -  tToolBar.getItems().get(tToolBar.getItems().size()-2).layoutXProperty().getValue()-75;
                progressBarCreateAccountRecord.setMinWidth(withProgressBar);
            }
        });
        createAccountRecordTask = new CreateAccountRecordTask(this.dataService);
        Double withProgressBar  =  tToolBar.getWidth() -  tToolBar.getItems().get(tToolBar.getItems().size()-1).layoutXProperty().getValue()-75;
        progressBarCreateAccountRecord = new ProgressBar();
        progressBarCreateAccountRecord.minHeight(20);
        progressBarCreateAccountRecord.setMinWidth(withProgressBar);

        tToolBar.getItems().add(progressBarCreateAccountRecord);
         //  progressBarCreateAccount.visibleProperty().bind(createAccountRecordTask.runningProperty());
        createAccountRecordButton.disableProperty().bind(createAccountRecordTask.runningProperty());


    }

    //TODO необходимо проверка на то что tab существует
    private void printFile(ActionEvent event) {

        //AccountRecordController accountRecordController = getCurrentAccountRecordController();
        new ImportIntoXLS().CreateXLS(getCurrentAccountRecordController().getAccountRecordTable());
    }

    private AccountRecordController getCurrentAccountRecordController() {
        return accountRecordControllerWeakHashMap.get(tpWindowContainer.getSelectionModel().getSelectedItem());
    }

    public void showJournalExport(ActionEvent event){

        this.exportDataService = new JournalExportDataService(new JDBCExportAccountRepository(ApplicationContextConfig.dataSourceAxapta()));


        FXMLLoader fxmlJournalExportLoader = new FXMLLoader(getClass().getResource("/ru/zhukov/export/JournalExportView.fxml"));

        JournalExportController journalExportController = new JournalExportController(exportDataService,currentUser.getDatabase().getNameinAxapta());
        fxmlJournalExportLoader.setController(journalExportController);
        try {
            AnchorPane splitPane = fxmlJournalExportLoader.load();

            AnchorPane anchorPane = new AnchorPane();
            AnchorPane.setTopAnchor(splitPane, 0.0);
            AnchorPane.setLeftAnchor(splitPane, 0.0);
            AnchorPane.setRightAnchor(splitPane, 0.0);
            AnchorPane.setBottomAnchor(splitPane, 0.0);

            anchorPane.getChildren().add(splitPane);

            Tab tabJournalExport = new Tab();

            tabJournalExport.setText("Журнал экспорта");
            tabJournalExport.setContent(splitPane);
            tpWindowContainer.setTabMinWidth(160);
            tpWindowContainer.setTabMaxWidth(160);

            //TODO Необходимо сохранять ссылку на контроллер

            //miDeleteRecord.disableProperty().bind(journalExportController.journalExportHeaderObjectPropertyProperty().isNull());
            //miDeleteRecord.disableProperty().bind(bindings);
          /*  journalExportController.journalExportHeaderObjectPropertyProperty().addListener((observable, oldValue, newValue) -> {
                 System.out.println(newValue);
            });
            */
            journalExportControllerWeakHashMap.putIfAbsent(tabJournalExport,journalExportController);
            tpWindowContainer.getTabs().addAll(tabJournalExport);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void  showAccountRecordView(ActionEvent event){
        try {
            month = datePicker.getValue().getMonthValue();
            year = datePicker.getValue().getYear();

            FXMLLoader fxmlAccountLoader = new FXMLLoader(getClass().getResource("/ru/zhukov/account/AccountRecordView.fxml"));

            AccountRecordController accountRecordController = new AccountRecordController(dataService,month,year);
            fxmlAccountLoader.setController(accountRecordController);



            AnchorPane app = fxmlAccountLoader.load();
            //  app.setMinWidth(tabPane.getTabMaxWidth());
            // app.setMinHeight(tabPane.getTabMaxHeight());
            AnchorPane anchorPane = new AnchorPane();
            AnchorPane.setTopAnchor(app, 0.0);
            AnchorPane.setLeftAnchor(app, 0.0);
            AnchorPane.setRightAnchor(app, 0.0);
            AnchorPane.setBottomAnchor(app, 0.0);

            anchorPane.getChildren().add(app);

            Tab tabAccount = new Tab();

            tabAccount.setText(String.format("Проводки за %s ",datePicker.getValue().format(DateTimeFormatter.ofPattern("MMM-YYYY"))));
            tabAccount.setContent(anchorPane);
            tpWindowContainer.setTabMinWidth(180);
            tpWindowContainer.setTabMaxWidth(180);
            tpWindowContainer.getTabs().addAll(tabAccount);
            accountRecordControllerWeakHashMap.putIfAbsent(tabAccount,accountRecordController);

        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public void createAccountRecord(ActionEvent event){
        ButtonType yesButtonType = new ButtonType("Да",ButtonBar.ButtonData.YES);
        ButtonType noButtonType = new ButtonType("Нет",ButtonBar.ButtonData.NO);
        Alert askCreateAccount = new Alert(Alert.AlertType.CONFIRMATION,"",yesButtonType,noButtonType);
        askCreateAccount.getDialogPane().getStylesheets().addAll(getClass().getResource("/ru/zhukov/assests/css/MyDialog.css").toExternalForm());
        askCreateAccount.setTitle("Формирование проводок");
        askCreateAccount.setHeaderText("Сформировать проводки");
        askCreateAccount.setContentText("Для формирования проводок будет использован текущий месяц в АиТ. Предыдущие данные будут удалены. Продолжить?");
        askCreateAccount.initOwner(mainWindow.getScene().getWindow());

        askCreateAccount.initModality(Modality.WINDOW_MODAL);
        askCreateAccount.setGraphic(new ImageView(new Image(getClass().getResource("/ru/zhukov/assests/image32/contract-execute.png").toExternalForm())));
        askCreateAccount.showAndWait().ifPresent(result ->{
            if(result == yesButtonType){
                bindTaskCreateAccountRecord();
                Thread threadCreateAccount =  new Thread(createAccountRecordTask);
                threadCreateAccount.setDaemon(true);
                threadCreateAccount.start();


            }
        });


    }


    private void exportAccountRecordAction(ActionEvent event){
       FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ru/zhukov/account/ExportAccountRecordDialog.fxml"));
       AccountRecordController accountRecordController = getCurrentAccountRecordController();
       ExportAccountRecordController exportAccountRecordController =
               new ExportAccountRecordController(accountRecordController.getYear(),accountRecordController.getMonth(),
                                                 currentUser.getDatabase().getNameinAxapta());
       fxmlLoader.setController(exportAccountRecordController);
        try {

            DialogPane dialogPane =  fxmlLoader.load();

            Dialog exportDialog = new Dialog();
            exportDialog.setTitle("Экспорт проводок в Axapta");
            exportDialog.setDialogPane(dialogPane);
            ButtonType yesButtonType = new ButtonType("Выполнить",ButtonBar.ButtonData.YES);
            ButtonType noButtonType = new ButtonType("Нет",ButtonBar.ButtonData.NO);
            exportDialog.getDialogPane().getButtonTypes().removeAll();
            exportDialog.getDialogPane().getButtonTypes().addAll(yesButtonType,noButtonType);


            exportDialog.initOwner(this.mainWindow.getScene().getWindow());
            exportDialog.initModality(Modality.WINDOW_MODAL);


            exportDialog.showAndWait().filter(e -> e == yesButtonType)
                                      .ifPresent(result -> runExportAccountRecord(exportAccountRecordController));



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //@TODO перенос проводок
    private void runExportAccountRecord(ExportAccountRecordController controller) {
        AccountRecordController accountRecordController =  getCurrentAccountRecordController();
        ExportJournal exportJournal = controller.getExportJournal();
        dataService.exportAccountRecord(accountRecordController.getMonth(),accountRecordController.getYear(),exportJournal);



    }


    private class CreateAccountRecordTask extends Task<Integer>{

         private AccountRecordDataService accountRecordDataService;

        public CreateAccountRecordTask(AccountRecordDataService dataService){
            this.accountRecordDataService = dataService;
        }

        @Override
        protected void succeeded() {
            super.succeeded();
            tToolBar.getItems().remove(progressBarCreateAccountRecord);
        }

        @Override
        protected void updateMessage(String message) {
            super.updateMessage(message);

        }

        @Override
        protected Integer call() throws Exception {

            accountRecordDataService.createAccountRecord();

            return 1;
        }


    }



    private class MyStringConverter extends StringConverter<LocalDate> {
        String pattern = "MMMM-yyyy";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

        {

            datePicker.setPromptText(pattern.toLowerCase());
        }



        @Override
        public String toString(LocalDate date) {
            if (date != null) {
                return dateFormatter.format(date);
            } else {
                return "";
            }
        }

        @Override
        public LocalDate fromString(String string) {
            if (string != null && !string.isEmpty()) {
                return LocalDate.parse(string, dateFormatter);
            } else {
                return null;
            }
        }
    }




}
