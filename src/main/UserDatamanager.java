package main;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * Speichert die Anmeldedaten und liest diese auch wieder aus.
 * @author Jens Krüger
 * @author Niklas Bruns
 * @author Marc Seibel
 * @version 1.0
 *
 */
public class UserDatamanager {

   private static String dbUrl;
   private static String dbUser;
   private static String dbEncryptedPassword;
   private static String dbPort;

    /**
     * Speichert Url und Username in der Datei 'userdata.txt'.
     * @param url Url zum anmelden an die DB.
     * @param user Username zum anmelden an die DB.
     */
public static void saveUserData(String url, String user, String decryptedPassword)
    {
        PrintWriter pWriter = null;
        try {
            pWriter = new PrintWriter(new BufferedWriter(new FileWriter(System.getenv("APPDATA")+ "\\mnist\\userdata.txt")));
            pWriter.println(url);
            pWriter.println(user);
            pWriter.println(encode(decryptedPassword));
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
    String encryptedPassword = null;

    FileReader fr = null;
    BufferedReader br = null;

    try {
        String fileName = System.getenv("APPDATA")+ "\\mnist\\userdata.txt";
        File file = new File(fileName);
        if (!file.exists())

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
        encryptedPassword = br.readLine();
        }
         catch (IOException ex) {
        ex.printStackTrace();
    }

    return new String[] {url, user, decode(encryptedPassword)};
    }

    public static String getDbUrl() {
        return dbUrl;
    }

    public static void setDbUrl(String url) {
        dbUrl = url;
    }

    public static String getDbUser() {
        return dbUser;
    }

    public static void setDbUser(String user) {
        dbUser = user;
    }

    public static String getDbDecryptedPassword() {
        return decode(dbEncryptedPassword);
    }

    public static void setDbDecryptedPassword(String password)
    {
        dbEncryptedPassword = encode(password);
    }

    public static void setDbPort(String portNew) {
    dbPort = portNew;
    }

    public static String getDbPort() {
        return dbPort;
    }

    private static String encode(String decryptedPassword){
        String encryptedPassword = null;
try {
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.ENCRYPT_MODE, generateAESKey());
    byte[] encrypted = cipher.doFinal(decryptedPassword.getBytes());

    BASE64Encoder myEncoder = new BASE64Encoder();
    encryptedPassword = myEncoder.encode(encrypted);

} catch (Exception e){
    e.printStackTrace();
}
        return encryptedPassword;
}

private static String decode(String encryptedString) {
    String decryptedString = null;
    try {
        BASE64Decoder myDecoder2 = new BASE64Decoder();
        byte[] crypted2 = myDecoder2.decodeBuffer(encryptedString);

        Cipher cipher2 = Cipher.getInstance("AES");
        cipher2.init(Cipher.DECRYPT_MODE, generateAESKey());
        byte[] cipherData2 = cipher2.doFinal(crypted2);
        decryptedString = new String(cipherData2);

    }catch (Exception e){
        e.printStackTrace();
    }
    return decryptedString;
}

private static SecretKeySpec generateAESKey() {
    SecretKeySpec secretKeySpec = null;
    try {
        String keyStr = "rH4*~6nsFtN.-&5Sep#[f";
        byte[] key = (keyStr).getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16);
        secretKeySpec = new SecretKeySpec(key, "AES");
    }catch (Exception e){
        e.printStackTrace();
    }
    return secretKeySpec;
}

}
