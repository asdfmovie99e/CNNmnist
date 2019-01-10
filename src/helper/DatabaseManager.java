package helper;


import main.UserDatamanager;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * Die Klasse zur Steuerung der Datenbank Ein- und Ausgaben
 * @author Jens Krüger
 * @author Niklas Bruns
 * @author Marc Seibel
 * @version 1.0
 *
 */

public class DatabaseManager {

    static final String jdbcDriver = "org.mariadb.jdbc.Driver";
    private static Connection connection;
    private static Object[][][] bufferedLoadEdgeArray = null;
    private static String bufferedSaveCommand = null;
    private static Integer bufferedLoadSaveNr = null;

    /**
     * Erstellt eine Verbindung zur Datenbank.
     * @param url ist die Adresse zur Datenbank.
     * @param user Benutzerkennung der Datenbank,
     * @param pass Passwort zur Benutzerkennung,
     * @param port, diese Parameter sind notwendig um sich bei der Datenbank anmelden zu können.
     */
    private static void connect(String url, String user, String pass, String port) throws Exception{
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection("jdbc:mysql://" + url + ":" + port + "/neuronaldata", user, pass);
            System.out.println("Sie sind jetzt verbunden.");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Diese Methode meldet dich von der Datenbank ab.
     */
    public static void close(){
        if (connection != null) {
            try {
                connection.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Prüfung ob eine bestehende Verbindung vorhanden ist.
     */
    private static boolean isConnected(){

        try {
            ResultSet rs = sendQuery("SELECT 1;");
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

    /**
     * Anfangs wird geprüft ob eine Verbindung besteht.
     * Wenn Verbindung besteht wird die Methode beendet und Programm fortgesetzt.
     * Wenn keine Verbindung besteht wird die Verbindung hergestellt und anschließend beendet.
     */
    private static void refreshConnection() {
        if (!isConnected()) {
            try {
                connect(UserDatamanager.getDbUrl(),UserDatamanager.getDbUser(), UserDatamanager.getDbDecryptedPassword(), UserDatamanager.getDbPort());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Die Methode stellt die Abfrage aus der Datenbank zur Verfügung.
      * @param query ist der SQL - Befehl der übergeben wird.
     * @return Es wird dann ein ResultSet zurückgegeben, welches einem Tabellenausschnitt entspricht,
     * oder im Falle der Exception wird nichts zurückgegeben.
     */
    private static ResultSet sendQuery(String query) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            return rs;
        } catch (SQLException e) {
            return null;
        }
    }


    /**
     * Es wird geprüft ob eine Verbindung besteht und falls notwendig eine Verbindung hergestellt.
     * Darauf folgt die Abfrage aller Save_NR aus der Maintable aus der Datenbank.
     * Das Ergebnis der Abfrage wird in einem Array gespeichert.
     * @return gibt alle Save_Nr aus dem Array aus.
     */
    public static Integer[] getAllSaveNumbers() {

        refreshConnection();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            String s = "(SELECT SAVE_NR FROM maintable)";
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

    /**
     * Prüft Verbindung und erstellt falls notwendig eine Verbindung.
     * Erstellt dann ein Objektarray.
     * Fragt eine konkrete Spalte ab bei der die übergebene Save_NR zutrifft.
     * @param SaveNr ist die Nummer des Speicherstandes der geladen werden soll.
     * @return gibt ein Objektarray aus mit der abgefragten Spalte aus.
     */
    public static Object[] getMainTableEntry(Integer SaveNr){

        refreshConnection();
        Statement stmt = null;
        ResultSet rs = null;
        Object result;
        ArrayList<Object> resultList = new ArrayList<Object>();

        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM maintable WHERE SAVE_NR =" + SaveNr);
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
                obArray[8] = rs.getInt("OUTPUT_NEURON");


            }
            return obArray;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Fragt ab ob eine Verbindung besteht und stellt wenn notwendig eine Verbindung her.
     * Füllt die angegebenen Parameter mit den übergebenenen Werten.
     * @param saveNr ist die Nummer des Speicherstandes der geladen werden soll.
     * @param inputNeurons gibt an welches Neuron den Hiddenschichten den Wert übergibt.
     * @param hiddenNeuronsOne ist die erste Schicht durch welche die Neuronen durchlaufen.
     * @param hiddenNeuronsTwo ist die zweite Schicht durch welche die Neuronen durchlaufen.
     * @param outputNeurons ist das Neuron, welches die Information beinhält welche Zahl erkannt wurde.
     * @param successRate gibt die Erfolgsrate der Zahlenerkennung an.
     */
    public static void addMainTableEntry(Integer saveNr, Integer inputNeurons, Integer hiddenNeuronsOne,Integer hiddenNeuronsTwo, Integer outputNeurons, Double successRate) {
        Statement stmt = null;
        ResultSet rst = null;
        refreshConnection();
        try {
            String s = "INSERT INTO maintable (SAVE_NR, INPUT_NEURON, HIDDEN_NEURON_ONE, HIDDEN_NEURON_TWO, OUTPUT_NEURON, ACCURACY) VALUES " +
                    "(" + saveNr + "," + inputNeurons + "," + hiddenNeuronsOne + "," + hiddenNeuronsTwo +  "," + outputNeurons + "," + successRate +")" ;
            stmt = connection.createStatement();
            rst = stmt.executeQuery(s);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }


    /**
     *
     * @param saveNr ist die Nummer des Speicherstandes der geladen werden soll.
     * @param layerNumber beschreibt die Nummer, in welcher Schicht das Neuron sich befindet.
     * @param edgeNumber beschreibt die Anzahl an Kanten.
     * @return gibt ein Objektarray aus, in dem die Layernumber und Edgenumber beinhält.
     */
    public static Object[] getEdgeEntry(Integer saveNr, Integer layerNumber, Integer edgeNumber){
        if(bufferedLoadSaveNr == null || bufferedLoadSaveNr != saveNr){
            prepareSave(saveNr);
        }

        Object[] edge = bufferedLoadEdgeArray[layerNumber][edgeNumber];


        return edge;
    }

    /**
     * Die Methode holt sich die Anmeldeinformationen aus dem Anmeldefenster der GUI und loggt sich über die
     * connect() ein.
     * @throws Exception wirft eine Fehlermeldung, wenn die Informationen nicht empfangen werden können.
     */
    public static void checkCredentials() throws Exception{
        connect(UserDatamanager.getDbUrl(),UserDatamanager.getDbUser(), UserDatamanager.getDbDecryptedPassword(), UserDatamanager.getDbPort());
    }

    /**
     * Es werden die Kanten gezählt auf welche die Parameter zutreffen.
     * @param SaveNr ist die Nummer des Speicherstandes der geladen werden soll.
     * @param layerNumber beschreibt die Nummer, in welcher Schicht das Neuron sich befindet.
     * @return gibt die Anzahl von Kanten aus, welche die angegebene SaveNr und Layernumber haben.
     */
    public static Integer getEgeCountInLayer(Integer SaveNr, Integer layerNumber){

        Statement stmt = null;
        ResultSet rst = null;
        Integer result = null;
        refreshConnection();
        try {
            String s = ("SELECT COUNT(EDGE_NR) AS EDGECOUNT FROM edgetable WHERE SAVE_NR = " + SaveNr + " and LAYER_NR = " + layerNumber);
            stmt = connection.createStatement();
            rst = stmt.executeQuery(s);

            while (rst.next()) {
                result = rst.getInt("EDGECOUNT");
            }

        }catch (SQLException e){
            return null;
        }
        return result;
    }

    /**
     * Es werden aus beiden Tabellen der Datenbank die Zeilen gelöscht, bei denen die Übergebene SaveNr zutrifft
     * @param SaveNr ist die Nummer des Speicherstandes der geladen werden soll.
     */
    public static void deleteSave(Integer SaveNr){
        Statement stmt1 = null;
        Statement stmt2 = null;
        refreshConnection();
        try {
            String s1 = "DELETE FROM maintable WHERE SAVE_NR =" + SaveNr;
            stmt1 = connection.createStatement();
            stmt1.executeQuery(s1);
            String s2 = " DELETE FROM edgetable WHERE SAVE_NR =" + SaveNr;
            stmt2 = connection.createStatement();
            stmt2.executeQuery(s2);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gibt alle Information aus der Edgetabelle zur übergebenen SaveNr aus und weißt aus dem ResultSet
     * den zugehörigen Wert zu.
     * @param saveNr ist die Nummer des Speicherstandes der geladen werden soll.
     */
    public static void prepareSave(Integer saveNr){

        Statement stmt = null;
        ResultSet rs = null;
        refreshConnection();
        Object [][][] obArray = new Object[6][235200][3];
        try {
            String s = "SELECT PRE_NEURON_IDENT, NEXT_NEURON_IDENT, WEIGHT, LAYER_NR, EDGE_NR FROM edgetable WHERE " +
                    "SAVE_NR = "+ saveNr;
            stmt = connection.createStatement();
            rs = stmt.executeQuery(s);
            while (rs.next()) {
                int layerNr =rs.getInt("LAYER_NR");
                int edgeNr = rs.getInt("EDGE_NR");
                int preNeuronIdent = rs.getInt("PRE_NEURON_IDENT");
                int nextNeuronIdent = rs.getInt("NEXT_NEURON_IDENT");
                double weight = rs.getDouble("WEIGHT");
                obArray[layerNr][edgeNr][0] = preNeuronIdent;
                obArray[layerNr][edgeNr][1] = nextNeuronIdent;
                obArray[layerNr][edgeNr][2]= weight;
            }



        }catch (SQLException e){
            e.printStackTrace();
        }
        bufferedLoadEdgeArray = obArray;
        bufferedLoadSaveNr = saveNr;
    }

    /**
     * Es werden sofern keine Werte in der Datenbank vorhanden sind,wird der SQL-Befehl gestartet und Werte befüllt.
     * Anschließend wird alle 10.000 Daten ein String bereitgestellt um die Abfragezeit zu verkürzen.
     * @param saveNr ist die Nummer des Speicherstandes der geladen werden soll.
     * @param layerNumber beschreibt die Nummer, in welcher Schicht das Neuron sich befindet.
     * @param edgeNumber beschreibt die Anzahl an Kanten.
     * @param previousNeuronID ist die eindeutige Identifikationsnummer des eingegangen Neurons.
     * @param nextNeuronID ist die eindeutige Indentifikationsnummer des Neurons welches die Information erhält.
     * @param weight ist die Gewichtung der Kanten
     */
    public static void addEdge(Integer saveNr,Integer layerNumber,Integer edgeNumber, Integer previousNeuronID, Integer nextNeuronID, Double weight){
        String startString = "insert into edgetable (SAVE_NR, LAYER_NR, EDGE_NR, PRE_NEURON_IDENT, NEXT_NEURON_IDENT, WEIGHT) VALUES ";
        if(bufferedSaveCommand == null){
            bufferedSaveCommand = startString;
        }
        if(!bufferedSaveCommand.equals(startString)){
            bufferedSaveCommand+= ",";
        }
        bufferedSaveCommand += "(" + saveNr + "," + layerNumber + "," + edgeNumber + "," + previousNeuronID + "," + nextNeuronID + "," + weight + ")";
        if (bufferedSaveCommand.length() > 100000){
            flushSaveCommand();
        }
    }

    /**
     * Verkürzt die Ladezeit in dem ein String mit einer bestimmten Anzahl an Daten befüllt und bereits
     * vor Abfrage bereitgestellt wird. Verschickt den String auch wenn unter 10.000 Daten enthalten sind.
     */
    public static void flushSaveCommand() {
        refreshConnection();

        try {
            Statement stmt = connection.createStatement();
            stmt.executeQuery(bufferedSaveCommand);
            bufferedSaveCommand = null;
        } catch (SQLException e) {
            return ;
        }
    }
}
