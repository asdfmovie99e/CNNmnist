package gui;

import java.io.*;

/**
 * Speichert die Anmeldedaten und liest diese auch wieder aus.
 * @author Jens Krüger
 * @author Niklas Bruns
 * @author Marc Seibel
 * @version 1.0
 *
 */
public class UserDatamanager {

   private static String dburl;
   private static String dbuser;
   private static String dbpassword;
   private static String port;
    /**
     * Speichert Url und Username in der Datei 'userdata.txt'.
     * @param url Url zum anmelden an die DB.
     * @param user Username zum anmelden an die DB.
     */
public static void SaveUserData(String url, String user)
    {
        PrintWriter pWriter = null;
        try {
            pWriter = new PrintWriter(new BufferedWriter(new FileWriter(System.getenv("APPDATA")+ "\\mnist\\userdata.txt")));
            pWriter.println(url);
            pWriter.println(user);
            pWriter.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Liest die gespeicherten Anmeldedaten aus der Datei 'userdata.txt'.
     */
public static String [] readSavedUserData() {
    String url = null;
    String user = null ;

    FileReader fr = null;
    BufferedReader br = null;

    try {
        String fileName = System.getenv("APPDATA")+ "\\mnist\\userdata.txt";
        File file = new File(fileName);
        if (file.exists())
        {

        }
        else
        {
            PrintWriter pWriter = null;
            try {
                pWriter = new PrintWriter(new BufferedWriter(new FileWriter(System.getenv("APPDATA")+ "\\mnist\\userdata.txt")));
                pWriter.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        fr = new FileReader(file);
        br = new BufferedReader(fr);

        url = br.readLine();
        user = br.readLine();
        }
         catch (IOException ex) {
        ex.printStackTrace();
    }
        if (url == null || url.equals(""))
        {
            url = "jdbc:mysql://";
        }

    return new String[] {url, user};
    }

    public static String getDburl() {
        return dburl;
    }

    public static void setDburl(String url) {
        dburl = url;
    }

    public static String getDbuser() {
        return dbuser;
    }

    public static void setDbuser(String user) {
        dbuser = user;
    }

    public static String getDbpassword() {
        return dbpassword;
    }

    public static void setDbpassword(String password)
    {
        dbpassword = password;
    }

    public static void setPort(String portNew) {
    port = portNew;
    }

    public static String getPort() {
        return port;
    }


}
