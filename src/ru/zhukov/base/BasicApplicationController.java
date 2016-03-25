package ru.zhukov.base;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.zhukov.action.Action;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Gukov on 24.03.2016.
 */

public class BasicApplicationController implements Initializable {

    private ResourceBundle resourceBundle;

    @FXML
    private ToolBar tToolBar;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;

        Button exitButton = new Button();
        exitButton.setGraphic(new ImageView(new Image(getClass().getResource("../res/toolbar/folder_next.png").toExternalForm())));
        exitButton.setTooltip(new Tooltip("Выход из приложения"));
        exitButton.setOnAction(Action.exit);

        DatePicker datePicker = new DatePicker();
        datePicker.setTooltip(new Tooltip("Период"));
         datePicker.setEditable(false);


        tToolBar.getItems().add(exitButton);
        tToolBar.getItems().add(new Separator(Orientation.VERTICAL));
        tToolBar.getItems().add(datePicker);




    }



}
