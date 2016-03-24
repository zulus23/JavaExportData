package ru.zhukov.base;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Gukov on 24.03.2016.
 */

public class BaseApplicationController implements Initializable {

    private ResourceBundle resourceBundle;

    @FXML
    private ToolBar tToolBar;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;

        Button exitButton = new Button();
        exitButton.setGraphic(new ImageView(new Image(getClass().getResource("../res/toolbar/folder_next.png").toExternalForm())));
        exitButton.setTooltip(new Tooltip("Выход из приложения"));

        tToolBar.getItems().add(exitButton);

    }



}
