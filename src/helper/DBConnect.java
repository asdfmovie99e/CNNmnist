package helper;

import java.util.Scanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.*;

public class DBConnect {

    static final String jdbcDriver = "org.mariadb.jdbc.Driver";
    public static String user;
    public static String pass;
    public static String url = "jdbc:mysql://localhost:3306/neuronaldata";
    private static Connection connection;
    private static String driver ="com.mysql.jdbc.Driver";

    //Methode zum Einlesen des Usernamens
    public static String einlesenUser(){
        System.out.println("Geben Sie ihren Benutzernamen ein.");
        Scanner scanUser = new Scanner(System.in);
        user = scanUser.nextLine();
        return user;
    }

    //Methode zum Einlesen des Passwortes
    public static String einlesenPass(){
        System.out.println("Geben Sie ihr Passwort ein.");
        Scanner scanPass = new Scanner(System.in);
        pass = scanPass.nextLine();
        return pass;
    }

    //Methode zum verbinden
    public static void connect(String URL, String user, String pass){
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(url,user,pass);
            System.out.println("Sie sind jetzt verbunden.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Methode zum trennen
    public static void close(){
        if (connection != null) {
            try {
                connection.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Methode zum prüfen einer bestehenden Verbindung
    public static boolean isConnected(){

        try {
            ResultSet rs = abfrage("SELECT 1;");
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
    public static ResultSet abfrage(String query) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            return rs;
        } catch (SQLException e) {
            return null;
        }
    }

    //Methode zum Eintragen in die DB

    public static boolean setEintrag(String query) {
        try {
            Statement stmt = connection.createStatement();
            return stmt.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void hiddenLayerTabelleErstellen(){
        Connection conn = null;
        Statement stmt = null;

        DBConnect User1 = new DBConnect();

        User1.einlesenUser();
        User1.einlesenPass();

        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/neuronaldata" , user, pass);
        }
        catch (SQLException se) {
            se.printStackTrace();
            System.out.println("Das sollte nicht passieren");
        }

        try {
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
        } catch (SQLException e) {
            return ;
        }

        System.out.println("Die Tabelle 'HiddenLayer' wurde in der Datenbank wird erstellt!");
    }


    //AB HIER HAB ICH MAL EIN PAAR METHODEN ERSTELLT DIE DU FÜLLEN MUSST DAMIT ICH SCHONMAL WEITERMACHEN KANN ~ Jens

    public static Integer[] getAllSaveNumbers(){
        /*
        @return Gibt ein Array mit allen SaveNummern aus. Niklas braucht die um die aufzulisten.
         */
        return null;
    }

    public static Object[] getMainTable(Integer SaveNr){
        /*
        @params Die SaveNr des Standes der ausgegeben werden soll
        @return Es wird ein ObjectArray ausgegeben mit [Save_Nr, Input_Neuron, Hidden_Neuron_One,...HiddenNeuronFive, OutputNeuron, SuccessRate]
                Alles ist Integer, ausser SuccessRate, das ist Double
        */
            return null;
    }

    public static void addMainTableEntry(Integer InputNeurons, Integer HiddenNeuronsOne,Integer HiddenNeuronsTwo, Integer HiddenNeuronsThree, Integer HiddenNeuronsFour,Integer HiddenNeuronsFive, Integer OutputNeurons, Double SuccessRate){
        /*
        @params Sind selbsterklärend
         */

    }
}
