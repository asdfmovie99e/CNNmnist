package main;


import helper.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Die Hautpklasse des Projektes.
 * @author Jens Krüger
 * @author Niklas Bruns
 * @author Marc Seibel
 * @version 1.0
 *
 */
public class Main extends Application {

    private static Stage passwordStage;
    private static PasswordController passwordController;
    private static Stage mainStage;
    private static Controller mainController;
    private static Stage LoadStage;
    private static LoadController loadController;
    private Parent root = null;


    @Override
    /**
     * Startet die Passwort GUI.
     * @param passwordStage Wird von JavaFX automatisch übergeben.
     */
    public void start(Stage passwordStage) throws Exception{
        FXMLLoader loader =new FXMLLoader(getClass().getResource("GUIPassword.fxml"));
        root = loader.load();
        passwordStage.setTitle("Zahlenerkennung");
        passwordStage.setScene(new Scene(root, 380, 240));
        PasswordController passwordController = loader.getController();
        passwordController.fillSavedData();
        passwordStage.show();
        setPasswordStage(passwordStage);
        setPasswordController(passwordController);
    }


    /**
     * Hier findet der Aufruf der ersten GUI statt.
     * @param args Parameter mit denen das Programm gestartet wird. Werden nicht beachtet.
     */
    public static void main(String[] args) {
        launch(args);

        DatabaseManager.close();
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
