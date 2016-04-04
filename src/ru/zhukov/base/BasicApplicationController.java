package ru.zhukov.base;

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
import org.springframework.format.annotation.DateTimeFormat;
import ru.zhukov.ApplicationController;
import ru.zhukov.account.AccountRecordController;
import ru.zhukov.action.Action;
import ru.zhukov.service.AccountRecordDataService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Created by Gukov on 24.03.2016.
 */

public class BasicApplicationController implements Initializable {

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
    private VBox mainWindow;

   /* public TabPane getTpWindowContainer() {
        return tpWindowContainer;
    }
*/


    @FXML
    private TabPane tpWindowContainer;



    private DatePicker datePicker;
    private AccountRecordDataService dataService;
    private int month;
    private int year;

    public BasicApplicationController(AccountRecordDataService dataService){
        this.dataService = dataService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
        miExit.setOnAction(Action::exit);
        miExit.setAccelerator(KeyCombination.keyCombination("Ctrl+F4"));
        miNewDocument.setGraphic(new ImageView(new Image(getClass().getResource("/ru/zhukov/assests/image16/document.png").toExternalForm())));
        miPreferences.setGraphic(new ImageView(new Image(getClass().getResource("/ru/zhukov/assests/image16/application-gear.png").toExternalForm())));
        miPrintDocument.setGraphic(new ImageView(new Image(getClass().getResource("/ru/zhukov/assests/image16/document-print.png").toExternalForm())));

        Button preferencesButton = new Button();

        preferencesButton.setGraphic(new ImageView(new Image(getClass().getResource("/ru/zhukov/assests/image32/application-gear.png").toExternalForm())));
        Button createAccountRecordButton = new Button();
        createAccountRecordButton.setGraphic(new ImageView(new Image(getClass().getResource("/ru/zhukov/assests/image32/contract-execute.png").toExternalForm())));
        createAccountRecordButton.setOnAction(this::createAccountRecord);

        Button showAccountRecordView = new Button();
        showAccountRecordView.setText("Проводки");
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
        datePicker.setConverter(new MyStringConverter());
        datePicker.setEditable(false);


        //tToolBar.getItems().add(exitButton);
        tToolBar.getItems().add(createAccountRecordButton);
        tToolBar.getItems().add(showAccountRecordView);
        tToolBar.getItems().add(new Separator(Orientation.VERTICAL));
        tToolBar.getItems().add(datePicker);
        tToolBar.getItems().add(preferencesButton);
       // tToolBar.getItems().add(new Separator(Orientation.VERTICAL));






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
            tpWindowContainer.setTabMinWidth(160);
            tpWindowContainer.setTabMaxWidth(160);
            tpWindowContainer.getTabs().addAll(tabAccount);

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


            }
        });


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
