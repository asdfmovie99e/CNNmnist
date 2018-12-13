package main;

import gui.PasswordController;
import helper.DBConnect;
import helper.MathHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage passwordStage;
    Parent root = null;
    @Override
    public void start(Stage passwordStage) throws Exception{
        FXMLLoader loader =new FXMLLoader(getClass().getResource("../gui/GUIPassword.fxml"));
        root = loader.load();
        passwordStage.setTitle("Zahlenerkennung");
        passwordStage.setScene(new Scene(root, 380, 240));
        PasswordController passwordController = loader.getController();
        passwordController.fillSavedData();
        passwordStage.show();

        this.passwordStage=passwordStage;
    }

    public static Stage getPasswordStage() {
        return passwordStage;
    }

    public static void main(String[] args) {
       // launch(args);
        //MathHelper.start();
        //PictureCoder.readMnistFiles();
        //NetworkController.startLearning();
        DBConnect.getAllSaveNumbers();
        System.exit(1);
    }
}
