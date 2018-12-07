package helper;

import java.sql.Connection;
import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;



public class DBConnect {

    String user;
    String pass;
    private Connection connection;
    private String driver ="com.mysql.jdbc.Driver";

    //Methode zum Einlesen des Usernamens
    public String einlesenUser(){
        System.out.println("Geben Sie ihren Benutzernamen ein.");
        Scanner scanUser = new Scanner(System.in);
        user = scanUser.nextLine();
        return user;
    }

    //Methode zum Einlesen des Passwortes
    public String einlesenPass(){
        System.out.println("Geben Sie ihr Passwort ein.");
        Scanner scanPass = new Scanner(System.in);
        pass = scanPass.nextLine();
        return pass;
    }
}

    //Methode zum verbinden
    public void connect(String URL, String user, String pass){
        try {
            Class.forName(this.driver);
            this.connection = DriverManager.getConnection("jdbc:mysql://loclhost:3306/neuronaldata",user,pass);
            } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Methode zum trennen
    public void close(){
        if (this.connection != null) {
            try {
                this.connection.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Methode zum prüfen einer bestehenden Verbindung
    public boolean isConnected(){

        try {
            ResultSet rs = this.abfrage("SELECT 1;");
            if (rs == null){
                return false;
            }
            if (rs.next()) {
                return true;
            }
            return false;
            } catch (Exception e) {
            return false;
        }
    }

    //Methode zur Abfrage aus der DB
    public ResultSet abfrage(String query) {
        try {
            Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            return rs;
        } catch (SQLException e) {
            return null;
        }
    }

    //Methode zum Eintragen in die DB

    public boolean setEintrag(String query) {
        try {
            Statement stmt = this.connection.createStatement();
            return stmt.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public void hiddenLayerTabelleErstellen(){
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

                String sql = "Create Table Test"
                        + " (Save_Nr Integer, "
                        + " Input_Neuron_One Integer, "
                        + " Input_Neuron_Two Integer, "
                        + " Input_Neuron_Three Integer, "
                        + " Input_Neuron_Four Integer, "
                        + " Input_Neuron_Five Integer, "
                        + " Primary Key (Save_Nr))";
            stmt.executeUpdate(sql);

            System.out.println("Die Tabelle 'HiddenLayer' wurde in der Datenbank wird erstellt!");
    }
