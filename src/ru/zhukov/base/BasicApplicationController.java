package ru.zhukov.base;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import ru.zhukov.action.Action;

import java.net.URL;
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

        //preferencesButton.setTooltip(new Tooltip("Выход из приложения"));
        //preferencesButton.setOnAction(Action::exit);
        preferencesButton.setOnAction(Action::createAccountRecord);




       /* Button exitButton = new Button();

        exitButton.setGraphic(new ImageView(new Image(getClass().getResource("/ru/zhukov/assests/image/toolbar/folder_next.png").toExternalForm())));

        exitButton.setTooltip(new Tooltip("Выход из приложения"));
        exitButton.setOnAction(Action::exit);
 */
        DatePicker datePicker = new DatePicker();
        datePicker.setTooltip(new Tooltip("Период"));
         datePicker.setEditable(false);


        //tToolBar.getItems().add(exitButton);
         tToolBar.getItems().add(new Separator(Orientation.VERTICAL));
        tToolBar.getItems().add(datePicker);
        tToolBar.getItems().add(preferencesButton);
       // tToolBar.getItems().add(new Separator(Orientation.VERTICAL));




    }



}
