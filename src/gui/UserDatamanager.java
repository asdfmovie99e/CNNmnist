package gui;

import java.io.*;

public class UserDatamanager {

   private static String dburl;
   private static String dbuser;
   private static String dbpassword;

    //Speichert URL und Username
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

    //liest Datei aus
public static String [] readSavedUserData() {
    String url;
    String user;

    FileReader fr = null;
    BufferedReader br = null;

    try {
        String fileName = System.getenv("APPDATA")+ "\\mnist\\userdata.txt";
        File file = new File(fileName);
        fr = new FileReader(file);
        br = new BufferedReader(fr);

        url = br.readLine();
        user = br.readLine();
        }
         catch (IOException ex) {
        System.out.println(ex);

        url = "";
        user = "";

        }
    return new String[] {url, user};
    }

    public static String getDburl() {
        return dburl;
    }

    public static void setDburl(String dburl) {
        dburl = dburl;
    }

    public static String getDbuser() {
        return dbuser;
    }

    public static void setDbuser(String dbuser) {
        dbuser = dbuser;
    }

    public static String getDbpassword() {
        return dbpassword;
    }

    public static void setDbpassword(String dbpassword) {
        dbpassword = dbpassword;
    }
}
