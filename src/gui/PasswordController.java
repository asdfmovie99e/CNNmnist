package gui;


import helper.DBConnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class PasswordController {

    @FXML
    private CheckBox savelogin;

    @FXML
    private Button clicklogin;

    @FXML
    private TextField URL;

    @FXML
    private TextField User;

    @FXML
    private PasswordField Password;



    @FXML
    void clicklogin(ActionEvent event) {
        openMainGui();
        String url = URL.getText();
        String user = User.getText();
        String password = Password.toString();
    if (savelogin.isSelected())
        {
            UserDatamanager.SaveUserData(url,user);
        }

        //aufrufen der connect methode
        DBConnect.connect(url,user,password);

        //Übergabe an die Variabeln im UserDatamanager zum späteren Aufruf
        UserDatamanager.setDburl(url);
        UserDatamanager.setDbuser(user);
        UserDatamanager.setDbpassword(password);

        //schließen Gui



    }




    //Fuellt URL und Username in GUI mit gespeicherten Daten
public void fillSavedData (String url, String user)
{    String[] ret = UserDatamanager.readSavedUserData();
    URL.setText(ret[0]);
    User.setText(ret[1]);


}


private void openMainGui(){
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