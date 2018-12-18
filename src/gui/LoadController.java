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
import main.Main;
import main.NetworkController;

/**
 * Die Controllerklasse für die LoadGui (guidbLoad.fxml)
 * @author Jens Krüger
 * @author Niklas Bruns
 * @author Marc Seibel
 * @version 1.0
 *
 */

public class LoadController implements Initializable {


    @FXML
    private TableView<WeightData> table;

    @FXML
    private TableColumn saveNrCol ;

    @FXML
    private TableColumn genauigkeitCol ;

    @FXML
    private Button loadbutton;


    @FXML
    private Button deletebutton;

    @FXML
    /**
     * Loescht den ausgewaehlten Datensatz aus der Tabelle und aus der DB.
     */
    void clickdelete(ActionEvent event) {

        WeightData selectedItem = table.getSelectionModel().getSelectedItem();
        DBConnect.deleteRows(selectedItem.getSaveNr());
        table.getItems().removeAll(table.getSelectionModel().getSelectedItem());
    }




    @FXML
    /**
     * Laedt ausgewählten Datensatz mit Gewichten in das Netz und schließt LoadGui
     */
    //liest nr des ausgewählten Gewichtes aus
    void clickload(ActionEvent event) {
       WeightData selectedItem = table.getSelectionModel().getSelectedItem();
       NetworkController.loadDataFromDb(selectedItem.getSaveNr());
        Main.getLoadStage().close();
    }



    private final ObservableList<WeightData> data = FXCollections.observableArrayList();

    /**
     * Schreibt Daten in die Tabelle
     * @param saveNr Die Savenummer in der DB.
     * @param genauigkeit Gibt die Erfolgsrate des Datensatzes an.
     */
    private void addEntry(Integer saveNr, Double genauigkeit) {

        System.out.println("Entry added");
        table.getItems().add(new WeightData(
                saveNr,
                genauigkeit));
    }

    @Override
    /**
     *  Initialisiert die Tabelle
     */
    public void initialize(URL url, ResourceBundle rb) {
        saveNrCol.setMinWidth(100);
        genauigkeitCol.setMinWidth(100);
        table.getItems().setAll(this.data);
        putDBEntrysToTable();
    }

    /**
     * Laedt Daten fuer die Tabelle und fuehrt dann addEntry aus.
     */
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

    /**
     * Verwaltet die Daten der Tabelle.
     */
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