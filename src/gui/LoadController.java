package gui;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;



public class LoadController {


    @FXML
    private TableView<WeightData> table;

    @FXML
    private TableColumn<WeightData, Integer> nr;

    @FXML
    private TableColumn<WeightData, Double> succesrate;

    @FXML
    private TextField Nr;

    @FXML
    private Button clickload;

    @FXML
    private Button clickdelete;

    @FXML
    void clickdelete(ActionEvent event) {
    //Delete Methode zum löschen in der Db einfuegen

    }

    @FXML
    void clickload(ActionEvent event) {



    }

    private ObservableList<WeightData> inhalt = FXCollections.observableArrayList();

    public void filltable(Integer nr, Double succesrate) {
        inhalt.add(new WeightData(nr,succesrate));


        this.nr.setCellValueFactory(new PropertyValueFactory<WeightData, Integer>("Nr"));
        this.succesrate.setCellValueFactory(new PropertyValueFactory<WeightData, Double>("Erfolgsrate"));

        table.setItems(inhalt);
    }



}
