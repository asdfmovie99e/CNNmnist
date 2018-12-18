package gui;



import java.io.File;
import java.io.IOException;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.Main;
import main.NetworkController;
import main.PictureCoder;

import javax.imageio.ImageIO;

/**
 * Die Controllerklasse für die MainGui (GUI.fxml)
 * @author Jens Krüger
 * @author Niklas Bruns
 * @author Marc Seibel
 * @version 1.0
 *
 */

public class Controller {


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
    /**
     * Erstellt vom Canvas Feld eine png Datei, speichert und wertet diese aus.
     * Danach erfolgt eine Textausgabe des Ergebnisses.
     */
    void onAuswertenClicked(ActionEvent event) {

        Image snapshot = canvas.snapshot(null,null);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot,null), "png", new File(System.getenv("APPDATA") + "\\mnist\\" + "paint.png"));
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int[][] scaledDownArray = PictureCoder.readSelfDrawnImage();
        double[]resultArray = NetworkController.sendDrawnImageToNeurons(scaledDownArray);
        int biggestNeuronIdent = 0;
        for(int i = 0; i < 10; i ++){
            if(resultArray[biggestNeuronIdent] < resultArray[i]) {
                biggestNeuronIdent = i;
            }
        }
        Main.getMainController().showpb(resultArray, resultArray[biggestNeuronIdent]);
        Main.getMainController().setTextausgabe(biggestNeuronIdent);
    }



    @FXML
    /**
     * Setzt Canvasfeld und ProgressBars zurueck.
     */
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
    /**
     * Startet die Load Gui (guidbLoad.fxml) zum laden bzw. löschen der gelernten Gewichte.
     */
    void onGewichteladenclicked(ActionEvent event) {
        openGuiLoad();

    }



    @FXML
    /**
     * Die Main Gui wird geschlossen und das lernen startet.
     */
    void onLernenClicked(ActionEvent event) {
        Main.getMainStage().close();
        PictureCoder.readMnistFiles();
        NetworkController.startLearning();
    }



    @FXML
    /**
     *  Initialisiert das Canvas Feld zum Zeichnen.
     */
    void initialize() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        canvas.setOnMouseDragged(event ->{
            double size = 2 ;
            double x = event.getX() - size / 2;
            double y = event.getY() - size / 2;
            g.fill();
            g.fillRect(x, y, size, size);

        } );




    }



    /**
     * Uebergibt Werte an die ProgressBars
     * @param pbarray Der Array enthaelt die Wahrscheinlichkeiten fuer die jeweiligen Zahlen.
     * @param maxOutput Enthält die hoechste Wahrscheinlichkeit des Durchlaufes.
     */

    public void showpb(double[] pbarray, double maxOutput)
    {

        pb0.setProgress(pbarray[0] / maxOutput);
        pb1.setProgress(pbarray[1] / maxOutput);
        pb2.setProgress(pbarray[2] / maxOutput);
        pb3.setProgress(pbarray[3] / maxOutput);
        pb4.setProgress(pbarray[4] / maxOutput);
        pb5.setProgress(pbarray[5] / maxOutput);
        pb6.setProgress(pbarray[6] / maxOutput);
        pb7.setProgress(pbarray[7] / maxOutput);
        pb8.setProgress(pbarray[8] / maxOutput);
        pb9.setProgress(pbarray[9] / maxOutput);
    }
    public void setTextausgabe(int biggestNeuronIdent){
        String s = Integer.toString(biggestNeuronIdent);
         textausgabe.setText(s);

    }

    /**
     * Startet die Load Gui.
     */

    private void openGuiLoad(){

        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/guidbLoad.fxml"));
            root = loader.load();
            Stage loadStage = new Stage();
            loadStage.setTitle("Zahlenerkennung");
            Scene scene = new Scene(root);
            loadStage.setScene(scene);
            Main.setLoadController(loader.getController());
            loadStage.show();
            Main.setLoadStage(loadStage);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}


