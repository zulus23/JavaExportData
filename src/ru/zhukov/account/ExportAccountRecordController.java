package ru.zhukov.account;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import ru.zhukov.dto.ExportJournal;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Created by Gukov on 08.04.2016.
 */
public class ExportAccountRecordController implements Initializable {


    @FXML
    private TextField tCode;

    @FXML
    private DatePicker tDate;
    @FXML
    private TextField tName;
    @FXML
    private TextArea tDescription;

    private int year;
    private int month;
    private String dimensionDb;




    public ExportAccountRecordController(int year, int month,String dimensionDb){
        this.month = month;
        this.year = year;
        this.dimensionDb = dimensionDb;

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String code = String.format("ZARP-%s-%s",this.month,this.year);
        String name = String.format("Запрлата за %s",LocalDate.of(this.year,this.month,1).format(DateTimeFormatter.ofPattern("MM-YYYY")));
        String description = String.format("Предача данных по запрлате за %s",LocalDate.of(this.year,this.month,1).format(DateTimeFormatter.ofPattern("MM-YYYY")));
        tDate.setValue(LocalDate.now());
        tCode.setText(code);
        tName.setText(name);
        tDescription.setText(description);
   //     BooleanBinding changeBinding =  Bindings.or(tDate.valueProperty().isNotEqualTo(LocalDate.of(this.year,this.month,1)),tCode.textProperty().isNotEqualToIgnoreCase(code));
                                                 //.or(tName.textProperty().isNotEqualToIgnoreCase(name))
                                                 //.or(tDescription.textProperty().isNotEqualToIgnoreCase(description));

    }

    public ExportJournal getExportJournal(){
        return new ExportJournal(tCode.getText(),tDate.getValue(),
                          tName.getText(),tDescription.getText(),this.dimensionDb);
    }


}
