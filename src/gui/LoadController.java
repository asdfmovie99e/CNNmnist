package gui;

import java.net.URL;
import java.util.ResourceBundle;

import helper.DBConnect;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.NetworkController;

public class LoadController implements Initializable {


    @FXML
    private TableView<WeightData> table;
    @FXML private TableColumn saveNrCol ;
    @FXML private TableColumn genauigkeitCol ;

    @FXML
    private Button loadbutton;

    @FXML
    private ProgressBar loadbar;

    @FXML
    private Button deletebutton;

    @FXML
    void clickdelete(ActionEvent event) {
        table.getItems().removeAll(table.getSelectionModel().getSelectedItem());
    }


    public void setBar (Double barValue)
    {

        loadbar.setProgress(barValue);

    }


    @FXML
    //liest nr des ausgewählten Gewichtes aus
    void clickload(ActionEvent event) {
        loadbar.setVisible(true);
       WeightData selectedItem = table.getSelectionModel().getSelectedItem();
       NetworkController.loadDataFromDb(selectedItem.getSaveNr());
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
        putDBEntrysToTable();
    }

    private void putDBEntrysToTable() {
      Integer nrlength = DBConnect.getAllSaveNumbers().length;
      Integer [] savednr = DBConnect.getAllSaveNumbers();

      for (Integer i = 0; i < nrlength; i++)
      {
        Object [] tableEntry = DBConnect.getMainTableEntry(savednr[i]);
        Integer nr = (Integer) tableEntry[0];
        double succesrate = (Double) tableEntry [1];
        addEntry(nr, succesrate);
      }
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