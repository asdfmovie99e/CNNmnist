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
    private static PasswordController passwordController;
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
        this.passwordController = passwordController;
    }

    public static Stage getPasswordStage() {
        return passwordStage;
    }

    public static PasswordController getPasswordController() {
        return passwordController;
    }

    public static void main(String[] args) {
        launch(args);

      // Integer[] test =  DBConnect.getAllSaveNumbers();
        System.exit(1);
    }



}
