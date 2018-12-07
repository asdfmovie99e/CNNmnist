package gui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class PasswortController {

    @FXML
    private CheckBox savelogin;

    @FXML
    private Button clicklogin;

    @FXML
    private TextField URL;

    @FXML
    private TextField User;

    @FXML
    private PasswordField Passwort;



    @FXML
    void clicklogin(ActionEvent event) {
        String url = URL.toString();
        String user = User.toString();
        String passwort = Passwort.toString();
    if (savelogin.isSelected())
        {
            UserDatamanager.SaveUserData(url,user);
        }

        UserDatamanager.login(url,user,passwort);
    }


    //Fuellt URL und Username mit gespeicherten Daten
public void fillSavedData (String url, String user)
{

    URL.setText(url);
    User.setText(user);
}
}