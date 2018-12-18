package gui;


import helper.DBConnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Main;

import java.io.IOException;

/**
 * Die Controllerklasse fuer die PasswordGui (GUIPassword.fxml)
 * @author Jens Krüger
 * @author Niklas Bruns
 * @author Marc Seibel
 * @version 1.0
 *
 */

public class PasswordController {


    @FXML
    private CheckBox savelogin;

    @FXML
    private Button clicklogin;


    @FXML
    private Label LoginFail;

    @FXML
    private TextField URL;

    @FXML
    private TextField User;

    @FXML
    private PasswordField Password;

    @FXML
    private TextField portField;



    @FXML
    /**
     * Uebergibt URL und Username an den UserDatamanager und stellt Verbindung zur Db her.
     * Sind die Anmeldedaten falsch oder ist Anmeldung aus anderen Gruenden nicht moeglich erfolgt eine Fehlermeldung.
     * Bei erfolgreicher Anmeldung wird die MainGui (GUI.fxml) geoefffnet.
     */
    void clicklogin(ActionEvent event) {

        String url = URL.getText();
        String user = User.getText();
        String password = Password.getText();
        String port = portField.getText();
        //Übergabe an die Variabeln im UserDatamanager zum späteren Aufruf
        UserDatamanager.setDburl(url);
        UserDatamanager.setDbuser(user);
        UserDatamanager.setDbpassword(password);
        UserDatamanager.setPort(port);
        if (savelogin.isSelected()) {
            UserDatamanager.SaveUserData(url, user);
        }

        try {
            DBConnect.checkCredentials();

        } catch (Exception e) {
            e.printStackTrace();
            //Bei Fehlerhafter anmeldung wird eine Nachricht angezeigt
            LoginFail.setVisible(true);
            return;
        }
        openMainGui();

    }


    /**
     * Fuellt Gui mit gespeicherten Anmeldedaten aus dem UserDatamanager.
     */
    public void fillSavedData() {
        String[] ret = UserDatamanager.readSavedUserData();
        URL.setText(ret[0]);
        User.setText(ret[1]);


    }

    /**
     * Oeffnet MainGui (GUI.fxml).
     */
    private void openMainGui() {
        Main.getPasswordStage().close();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/GUI.fxml"));
            root = loader.load();
            Stage mainStage = new Stage();
            mainStage.setTitle("Zahlenerkennung");
            mainStage.setScene(new Scene(root, 840, 500));
            Main.setMainController(loader.getController());
            Main.setMainStage(mainStage);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}