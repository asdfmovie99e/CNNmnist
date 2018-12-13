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
    void clicklogin(ActionEvent event) {

        String url = URL.getText();
        String user = User.getText();
        String password = Password.toString();
        //Übergabe an die Variabeln im UserDatamanager zum späteren Aufruf
        UserDatamanager.setDburl(url);
        UserDatamanager.setDbuser(user);
        UserDatamanager.setDbpassword(password);
    if (savelogin.isSelected())
        {
            UserDatamanager.SaveUserData(url,user);
        }

    try {
        DBConnect.checkCredentials();

    }
    catch (Exception e)
    {
        e.printStackTrace();
        //Bei Fehlerhafter anmeldung wird eine Nachricht angezeigt
        LoginFail.setVisible(true);
        return;
    }
        openMainGui();

    }




    //Fuellt URL und Username in GUI mit gespeicherten Daten
public void fillSavedData ()
{    String[] ret = UserDatamanager.readSavedUserData();
    URL.setText(ret[0]);
    User.setText(ret[1]);


}


private void openMainGui(){

    Main.getPasswordStage().close();



    Parent root = null;
    try {
        root = FXMLLoader.load(getClass().getResource("../gui/GUI.fxml"));
        Stage mainStage = new Stage();
        mainStage.setTitle("Zahlenerkennung");
        mainStage.setScene(new Scene(root, 840, 500));
        mainStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }

}

}