package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PictureCoder {

    private static File mnistFolder = new File(Paths.get("").toAbsolutePath().toString() + "\\src\\mnist");
    private static int[] labelArray;
    private static byte[] byteArray;
    private static int[] pixelArray;

    public static void readMnistFiles(){
        scanImages();
        scanLabels();
    }


    public static int[][] get2DPictureArray(int pictureId) {
        //gibt ein 2 dimensionales Array aus in dem die Pixel des Bildes pictureId gespeichert sind. jeder wert ist zwischen 0(weiss) und 1(schwarz)
        // das erste [] gibt die x koordinate an und das zweite [] gibt die y koordinate an
        int[][] result2Darray = new int[28][28];
        for(int x = 0; x < 28; x++){
            for(int y = 0; y < 28; y++){
                if(pixelArray[x + y * 28 + pictureId * 28 * 28] >= 0) {
                    result2Darray[x][y] = pixelArray[x + y * 28 + pictureId * 28 * 28];
                } else{
                    result2Darray[x][y] = pixelArray[x + y * 28 + pictureId * 28 * 28] * -1 + 128;
                }
            }
        }

        return result2Darray;
    }

    public static Integer getLabel(int pictureId){
        return  labelArray[pictureId];
    }

    private static void scanLabels() {
        //die Daten aus der UByte Label Datei werden in das LabelArray geladen
        byteArray = new byte[0];
        try {
            byteArray = Files.readAllBytes(new File(mnistFolder.getAbsolutePath() + "\\train-labels.idx1-ubyte").toPath());
            labelArray = new int[byteArray.length - 8];
            for (int i = 8; i <= byteArray.length - 1; i++) {
                labelArray[i - 8] = byteArray[i];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void scanImages() {
        //die Daten aus der UByte images Datei werden in das pixelArray geladen
        byteArray = new byte[0];
        try {
            byteArray = Files.readAllBytes(new File(mnistFolder.getAbsolutePath() + "\\train-images.idx3-ubyte").toPath());
            pixelArray = new int[byteArray.length - 16];
            for (int i = 0; i <= pixelArray.length - 1; i++) {
                if(byteArray[i + 16] < 0) {
                    pixelArray[i] = -1* byteArray[i + 16] + 128;
                }else{
                    pixelArray[i] = byteArray[i + 16];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
