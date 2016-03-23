package ru.zhukov.bootstrap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.zhukov.ApplicationController;
import ru.zhukov.utils.ReadFileWithDatabase;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by Zhukov on 15.03.2016.
 */
public class BootstrapApplication extends Application {
    private Stage primaryStage;


    @Override
    public void start(Stage primaryStage) throws Exception {
         ApplicationController.getInstance().createLoginWindow();
       // new ReadFileWithDatabase().getDatabaseList();
    }




    public static void main(String... args){
        launch(args);
    }




}
