package sample;

/*
 @author Niklas Bruns
 @version 1.0
 */

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Canvas canvas;


    @FXML
    private ProgressBar pb0;

    @FXML
    private ProgressBar pb1;

    @FXML
    private ProgressBar pb2;

    @FXML
    private ProgressBar pb3;

    @FXML
    private ProgressBar pb4;

    @FXML
    private ProgressBar pb5;

    @FXML
    private ProgressBar pb6;

    @FXML
    private ProgressBar pb8;

    @FXML
    private ProgressBar pb7;

    @FXML
    private ProgressBar pb9;

    @FXML
    private TextField textausgabe;




    @FXML
    void onAuswertenClicked(ActionEvent event) {

    }




    @FXML
    void onDeleteAction(ActionEvent event) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(),canvas.getHeight());


        pb0.setProgress(0);
        pb1.setProgress(0);
        pb2.setProgress(0);
        pb3.setProgress(0);
        pb4.setProgress(0);
        pb5.setProgress(0);
        pb6.setProgress(0);
        pb7.setProgress(0);
        pb8.setProgress(0);
        pb9.setProgress(0);

        textausgabe.setText("");
    }

    @FXML
    void onGewichteladenclicked(ActionEvent event) {
    }

    @FXML
    void onLernenClicked(ActionEvent event) {

    }



    @FXML
    void initialize() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        canvas.setOnMouseDragged(event ->{
            double size = 12 ;
            double x = event.getX() - size / 2;
            double y = event.getY() - size / 2;
            g.fill();
            g.fillRect(x, y, size, size);

        } );


    }


    private void shownumber()
    {
        textausgabe.setText("2");
    }





    private void showpb(double[] pbarray)
    {

        pb0.setProgress(pbarray[0]);
        pb1.setProgress(pbarray[1]);
        pb2.setProgress(pbarray[2]);
        pb3.setProgress(pbarray[3]);
        pb4.setProgress(pbarray[4]);
        pb5.setProgress(pbarray[5]);
        pb6.setProgress(pbarray[6]);
        pb7.setProgress(pbarray[7]);
        pb8.setProgress(pbarray[8]);
        pb9.setProgress(pbarray[9]);
    }
    public void setTextausgabe(String s){
        // textausgabe.setText(s);
    }
}
