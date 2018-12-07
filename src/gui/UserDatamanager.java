package gui;

import java.io.*;

public class UserDatamanager {


    //Speichert URL und Username
public static void SaveUserData(String url, String user)
    {
        PrintWriter pWriter = null;
        try {
            pWriter = new PrintWriter(new BufferedWriter(new FileWriter("userdata.txt")));
            pWriter.println(url);
            pWriter.println(user);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    //liest Datei aus     noch in Bearbeitung
public static void readSavedUserData()
    {
      try {
          new FileReader("userdata.txt");
            }
            catch (IOException ioe)
            {
            }


    }

    //Übergibt Logindaten zum anmelden
public static void login(String url, String user, String passwort)
    {

    }

}
