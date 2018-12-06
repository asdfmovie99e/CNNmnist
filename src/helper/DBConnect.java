package helper;

import java.sql.*;
import java.util.Scanner;


public class DBConnect {

    String user;
    String pass;


    public void tabelleErstellen(){
        Connection conn = null;
        Statement stmt = null;

        DBConnect User1 = new DBConnect();

        User1.einlesenUser();
        User1.einlesenPass();

        try {
            conn = DriverManager.getConnection(
                    'jdbc:mysql://localhost:3306/neuronaldata' , user, pass);
            }
            catch (SQLException se) {
            se.printStackTrace();
            System.out.println("Das sollte nicht passieren");
            }

            stmt = conn.createStatement();

                String sql = "Create new Table Registration"
                        +
            System.out.println("Neue Tabelle in der Datenbank wird erstellt!");
    }

    public String einlesenUser(){
        System.out.println("Geben Sie ihren Benutzernamen ein.");
        Scanner scanUser = new Scanner(System.in);
        user = scanUser.nextLine();
        return user;
    }

    public String einlesenPass(){
        System.out.println("Geben Sie ihr Passwort ein.");
        Scanner scanPass = new Scanner(System.in);
        pass = scanPass.nextLine();
        return pass;
    }
}
