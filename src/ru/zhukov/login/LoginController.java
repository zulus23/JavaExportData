package ru.zhukov.login;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.controlsfx.control.MaskerPane;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import ru.zhukov.ApplicationController;
import ru.zhukov.domain.Database;
import ru.zhukov.utils.ReadFileWithDatabase;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Zhukov on 15.03.2016.
 */
public class LoginController  implements Initializable{

    @FXML
    private Button bLogin;
    @FXML
    private Button bCancelLogin;
    @FXML
    private TextField tUserName;
    @FXML
    private PasswordField tPassword;

    @FXML
    private ComboBox<Database> cNameEnterprise;

    @FXML
    private Label tError;

    @FXML
    private StackPane stackPane;


    private ObservableList<Database> databaseList;

    private Database currenSelect;

    private ApplicationController applicationController;

    private MaskerPane masker;
    public LoginController(ApplicationController mainController) {
        this.applicationController = mainController;

        masker = new MaskerPane();
        masker.setVisible(false);
        masker.setText("Выполняю проверку. Ожидайте...");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        databaseList = FXCollections.observableArrayList();
        databaseList.addAll(new ReadFileWithDatabase().getDatabaseList());
        cNameEnterprise.setConverter(new DatabaseStringConverter());
        cNameEnterprise.setItems(databaseList);
        cNameEnterprise.setOnAction(e -> valueChanged(cNameEnterprise));
        cNameEnterprise.getSelectionModel().selectedItemProperty().addListener(this::enterpriseChanged);
        cNameEnterprise.getSelectionModel().selectFirst();
        BooleanBinding booleanBinding = Bindings.or(tUserName.textProperty().isEmpty(),tPassword.textProperty().isEmpty())
                                                .or(cNameEnterprise.getSelectionModel().selectedItemProperty().isNull());
        bLogin.disableProperty().bind(booleanBinding);
        bCancelLogin.setOnAction(event -> Platform.exit());
        bLogin.setOnAction(e -> eventLoginAction(e));
        stackPane.getChildren().addAll(masker);


    }

    public void setMainController(ApplicationController mainController){


    }

    private void eventLoginAction(ActionEvent event){

         masker.setVisible(true);
        applicationController.authentication(tUserName.getText(),tPassword.getText(),currenSelect);
    }

    private void valueChanged(ComboBox<Database> cNameEnterprise) {
        currenSelect = cNameEnterprise.getValue();

    }

    public void setTextError(String s) {
           masker.setVisible(false);
           tError.setVisible(true);
           tError.setText(s);

    }
    public void close(){
        Optional.of(this.bLogin).map(e -> (Stage)e.getScene().getWindow()).ifPresent(e ->e.close());
    }


    class  DatabaseStringConverter extends StringConverter<Database>{

        @Override
        public String toString(Database object) {
            return object.getName();
        }

        @Override
        public Database fromString(String string) {
            Database database = null;
            if (string == null){
                return  database;
            }
            return databaseList.filtered(e->e.getName().equalsIgnoreCase(string)).get(0);

        }
    }

    public void enterpriseChanged(ObservableValue<? extends Database> observableValue, Database oldValue, Database newValue){
       currenSelect = cNameEnterprise.getValue();
    }


}


