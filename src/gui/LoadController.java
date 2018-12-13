package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import main.NetworkController;

public class LoadController implements Initializable {


    @FXML
    private TableView<WeightData> table;
    @FXML private TableColumn saveNrCol ;
    @FXML private TableColumn genauigkeitCol ;

    @FXML
    private Button loadbutton;

    @FXML
    private Button deletebutton;

    @FXML
    void clickdelete(ActionEvent event) {
        table.getItems().removeAll(table.getSelectionModel().getSelectedItem());
    }


    @FXML
    private TextField nrload;

    @FXML
    //liest nr des ausgewählten Gewichtes aus
    void clickload(ActionEvent event) {
       WeightData selectedItem = table.getSelectionModel().getSelectedItem();
       NetworkController.loadDataFrom(selectedItem.getSaveNr());
       Controller.getLoadStage().close();
    }



    private final ObservableList<WeightData> data = FXCollections.observableArrayList();


    private void addEntry(Integer saveNr, Double genauigkeit) {
        // DAS HIER IST DIE FUNKTION MIT DER MAN NEUE EINTRÄGE HINZUFÜGEN KANN
        System.out.println("Entry added");
        table.getItems().add(new WeightData(
                saveNr,
                genauigkeit));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //DAS HIER IST DIE FUNKTION DIE AM ANFANG AUFGERUFEN WIRD. DU KANNST ALSO VON HIER AUS DIE DATEN AUS DER DATENBANK ABRUFEN UND MIT addEntry(s,g) DIREKT SICHTBAR MACHEN
        saveNrCol.setMinWidth(100);
        genauigkeitCol.setMinWidth(100);
        table.getItems().setAll(this.data);
        addEntry(534,2344d);
        addEntry(535,2.3);
    }

    public static class WeightData {

        private final SimpleIntegerProperty saveNr;
        private final SimpleDoubleProperty genauigkeit;

        private WeightData(Integer sNr, Double genauigkeit) {
            this.saveNr = new SimpleIntegerProperty(sNr);
            this.genauigkeit = new SimpleDoubleProperty(genauigkeit);
        }

        public Integer getSaveNr() {
            return saveNr.get();
        }

        public void setSaveNr(Integer sNr) {
            saveNr.set(sNr);
        }

        public Double getGenauigkeit(){
            return genauigkeit.getValue();
        }

        public void setGenaugikeit(Double gn) {
            genauigkeit.set(gn);
        }


}
}