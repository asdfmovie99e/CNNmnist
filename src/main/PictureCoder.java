package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
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


    public static void readSelfDrawnImage() {
        BufferedImage img = null;
        try {
            img= ImageIO.read(new File(System.getenv("APPDATA") + "\\mnist\\" + "paint.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Integer[][] colorArray = new Integer[196][196];
        for(int y = 0 ; y < 196; y++){
            for(int x = 0; x < 196; x++){
                Color c = new Color(img.getRGB(x, y), true);
                colorArray[x][y] = 255 - c.getRed(); //umdrehen damit weiss 0 und schwarz 255 ist
            }
        }
        scaleColorArrayDown(colorArray);
    }

    private static void scaleColorArrayDown(Integer[][] colorArray) {
        int[][] scaledDownArray = new int[28][28];
        BufferedImage shrunkImage = new BufferedImage(28, 28, BufferedImage.TYPE_INT_RGB);
        for(int outerY = 0 ; outerY < 28; outerY++){
            for(int outerX = 0; outerX < 28; outerX++){
                int blockSum = 0;
                for(int x = 0; x < 7;x++){
                    for(int y = 0; y < 7; y++){
                        blockSum += colorArray[x + outerX * 7][y + outerY * 7];
                    }
                }
                scaledDownArray[outerX][outerY] =/*255 - */blockSum / 49;
                Integer colorInt = scaledDownArray[outerX][outerY];
                int rgb = (colorInt << 16 | colorInt << 8 | colorInt);
                shrunkImage.setRGB(outerX,outerY,rgb);
            }
        }
        try {
            ImageIO.write(shrunkImage,"png",new File(System.getenv("APPDATA") + "\\mnist\\" + "asdf.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        NetworkController.sendDrawnImageToNeurons(scaledDownArray);
    }

}
