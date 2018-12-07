package gui;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class UserDatamanager {

public static void saveuserdata(String url, String user, String passwort)
    {
        PrintWriter pWriter = null;
        try {
            pWriter = new PrintWriter(new BufferedWriter(new FileWriter("passwort.txt")));
            pWriter.println(url);
            pWriter.println(user);
            pWriter.println(passwort);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

public static void login(String url, String user, String passwort)
    {

    }

}
