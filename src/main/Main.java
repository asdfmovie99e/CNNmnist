package main;

import helper.MathHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage passwordStage;
    @Override
    public void start(Stage passwordStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../gui/GUIPassword.fxml"));
        passwordStage.setTitle("Zahlenerkennung");
        passwordStage.setScene(new Scene(root, 380, 240));
        passwordStage.show();
        this.passwordStage=passwordStage;
    }

    public static Stage getPasswordStage() {
        return passwordStage;
    }

    public static void main(String[] args) {
         launch(args);
       // MathHelper.start();
        //PictureCoder.readMnistFiles();
        //NetworkController.startLearning();

        System.exit(1);
    }
}
