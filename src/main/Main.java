package main;

import gui.Controller;
import gui.LoadController;
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
    private static Stage mainStage;
    private static Controller mainController;
    private static Stage LoadStage;
    private static LoadController loadController;

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
        setPasswordStage(passwordStage);
        setPasswordController(passwordController);
    }



    public static void main(String[] args) {
        launch(args);

      // Integer[] test =  DBConnect.getAllSaveNumbers();
        System.exit(1);
    }

    public static Stage getPasswordStage() {
        return passwordStage;
    }

    public static void setPasswordStage(Stage passwordStage) {
        Main.passwordStage = passwordStage;
    }

    public static PasswordController getPasswordController() {
        return passwordController;
    }

    public static void setPasswordController(PasswordController passwordController) {
        Main.passwordController = passwordController;
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void setMainStage(Stage mainStage) {
        Main.mainStage = mainStage;
    }

    public static Controller getMainController() {
        return mainController;
    }

    public static void setMainController(Controller mainController) {
        Main.mainController = mainController;
    }

    public static Stage getLoadStage() {
        return LoadStage;
    }

    public static void setLoadStage(Stage loadStage) {
        LoadStage = loadStage;
    }

    public static LoadController getLoadController() {
        return loadController;
    }

    public static void setLoadController(LoadController loadController) {
        Main.loadController = loadController;
    }
}
