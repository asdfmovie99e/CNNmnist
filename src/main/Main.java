package main;

import helper.MathHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../gui/GUIPassword.fxml"));
        primaryStage.setTitle("Zahlenerkennung");
        primaryStage.setScene(new Scene(root, 840, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
         launch(args);
       // MathHelper.start();
        //PictureCoder.readMnistFiles();
        //NetworkController.startLearning();

        System.exit(1);
    }
}
