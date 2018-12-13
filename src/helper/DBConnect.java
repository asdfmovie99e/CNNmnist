package helper;

import gui.UserDatamanager;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.ArrayList;
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

    //Methode zum verbinden
    public static void connect(String URL, String user, String pass) throws Exception{
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(UserDatamanager.getDburl(),UserDatamanager.getDbuser(),UserDatamanager.getDbpassword());
            System.out.println("Sie sind jetzt verbunden.");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
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

    public static void checkConnection() {
        if (!isConnected()) {
            try {
                connect(UserDatamanager.getDburl(),UserDatamanager.getDbuser(), UserDatamanager.getDbpassword());
            } catch (Exception e) {
                e.printStackTrace();
            }
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

            String sql = "Create Table MAINTABLE"
                    + " (Save_Nr Integer Auto_Increment, "
                    + " Input_Neuron Integer, "
                    + " HIDDEN_NEURON_ONE Integer, "
                    + " HIDDEN_NEURON_TWO Integer, "
                    + " HIDDEN_NEURON_THREE Integer, "
                    + " HIDDEN_NEURON_FOUR Integer, "
                    + " HIDDEN_NEURON_FIVE INTEGER, "
                    + " OUTPUT_NEURON INTEGER, "
                    + " ACCURACY DOUBLE, "
                    + " Primary Key (Save_Nr))";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            return ;
        }

        System.out.println("Die Tabelle 'HiddenLayer' wurde in der Datenbank wird erstellt!");
    }


    //AB HIER HAB ICH MAL EIN PAAR METHODEN ERSTELLT DIE DU FÜLLEN MUSST DAMIT ICH SCHONMAL WEITERMACHEN KANN ~ Jens~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public static Integer[] getAllSaveNumbers() {
        /*
        @return Gibt ein Array mit allen SaveNummern aus.
         */
        checkConnection();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            String s = "(SELECT SAVE_NR FROM MAINTABLE)";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(s);
            ArrayList<Integer> resultList = new ArrayList<Integer>();
            Integer result;
            while (rs.next()) {
                result = rs.getInt(1);
                resultList.add(result);
            }
            Integer[] resultArray = new Integer[resultList.size()];
            Object[] objectArray = resultList.toArray();
            for(int i = 0 ; i < resultList.size(); i++){
                resultArray[i] = (Integer) (objectArray[i]);
            }
            return resultArray;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object[] getMainTableEntry(Integer SaveNr){
        /*
        @params Die SaveNr des Standes der ausgegeben werden soll
        @return Es wird ein ObjectArray ausgegeben mit [Save_Nr, Input_Neuron, Hidden_Neuron_One,...HiddenNeuronFive, OutputNeuron, SuccessRate]
                Alles ist Integer, ausser SuccessRate, das ist Double
        */
        checkConnection();
        Statement stmt = null;
        ResultSet rs = null;
        Object result;
        ArrayList<Object> resultList = new ArrayList<Object>();

        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM MAINTABLE WHERE SAVE_NR =" + SaveNr);
            Object [] obArray = new Object[9];

            while (rs.next()) {
                obArray[0] = rs.getInt("SAVE_NR");
                obArray[1] = rs.getDouble("ACCURACY");
                obArray[2] = rs.getInt("INPUT_NEURON");
                obArray[3] = rs.getInt("HIDDEN_NEURON_ONE");
                obArray[4] = rs.getInt("HIDDEN_NEURON_TWO");
                obArray[5] = rs.getInt("HIDDEN_NEURON_THREE");
                obArray[6] = rs.getInt("HIDDEN_NEURON_FOUR");
                obArray[7] = rs.getInt("HIDDEN_NEURON_FIVE");
                obArray[8] = rs.getInt("OUTOUT_NEURON");


            }
            return obArray;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addMainTableEntry(Integer saveNr, Integer InputNeurons, Integer HiddenNeuronsOne,Integer HiddenNeuronsTwo, Integer HiddenNeuronsThree, Integer HiddenNeuronsFour,Integer HiddenNeuronsFive, Integer OutputNeurons, Double SuccessRate) {
        /*
        @params Sind selbsterklärend
         */
        Statement stmt = null;
        ResultSet rst = null;
        checkConnection();
        try {
            String s = "INSERT INTO MAINTABLE (SAVE_NR, INPUT_NEURON, HIDDEN_NEURON_ONE, HIDDEN_NEURON_TWO, HIDDEN_NEURON_THREE, HIDDEN_NEURON_FOUR, HIDDEN_NEURON_FIVE, OUTPUT_NEURON,ACCURACY) VALUES " +
                    "(" + saveNr + "," + InputNeurons + "," + HiddenNeuronsOne + "," + HiddenNeuronsTwo + "," + HiddenNeuronsThree + "," + HiddenNeuronsFour + "," + HiddenNeuronsFive + "," + OutputNeurons + "," + SuccessRate +")" ;
            stmt = connection.createStatement();
            rst = stmt.executeQuery(s);
          //  rst2 = stmt.executeQuery("INSERT INTO HIDDENLAYER")
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public static void addEdge(Integer SaveNr,Integer LayerNumber,Integer EdgeNumber, Integer previousNeuronID, Integer nextNeuronID, Double Weight){

        Statement stmt = null;
        ResultSet rst = null;
        checkConnection();

        try {
            String s = "INSERT INTO EDGETABLE (SAVE_NR, LAYER_NR, EDGE_NR, PRE_NEURON_IDENT, NEXT_NEURON_IDENT, WEIGHT) VALUES" +
                    "( " + SaveNr +"," + LayerNumber + "," + EdgeNumber + "," + previousNeuronID + "," + nextNeuronID + "," + Weight + ")";
            stmt = connection.createStatement();
            rst = stmt.executeQuery(s);

        } catch (SQLException e) {
            return ;
        }
    }

    public static Object[] getEdgeEntry(Integer SaveNr, Integer Layernumber, Integer EdgeNumber){

        Statement stmt = null;
        ResultSet rs = null;
        checkConnection();
        Object result;
        ArrayList <Object> resultList = new ArrayList<Object>();
        try {
            String s = "SELECT PRE_NEURON_IDENT, NEXT_NEURON_IDENT, ACCURACY FROM EDGETABELLE WHERE " +
                    "SAVE_NR = "+ SaveNr+ " and LAYER_NR ="+ Layernumber +" and EDGE_NR = "+ EdgeNumber;
            stmt = connection.createStatement();
            rs = stmt.executeQuery(s);
            Object [] ObArray = new Object[6];
            while (rs.next()) {
                result = rs.getObject(1);
                resultList.add(result);
            }
            Integer[] resultArray = new Integer[resultList.size()];
            Object[] objectArray = resultList.toArray();
            for(int i = 0 ; i < resultList.size(); i++){
                resultArray[i] = (Integer) (objectArray[i]);
            }
            return resultArray;


        }catch (SQLException e){
            return null;
        }
    }

    public static void checkCredentials() throws Exception{
        connect(UserDatamanager.getDburl(),UserDatamanager.getDbuser(), UserDatamanager.getDbpassword());
    }


    public static Integer getEgeCountInLayer(Integer SaveNr, Integer Layernumber){
        /*
        @params Die SaveNr und der Layernumber der zu zählenden Edges
        @return die anzahl der edges auf die das zutrifft
         */
        Statement stmt = null;
        ResultSet rst = null;
        checkConnection();
        try {
            String s = ("SELECT COUNT(EDGE_NR) FROM EDGETABLE WHERE SAVE_NR = " + SaveNr + " and LAYER_NR = " + Layernumber);
            stmt = connection.createStatement();
            rst = stmt.executeQuery(s);

        }catch (SQLException e){
            return null;
        }
        return null;
    }
}
