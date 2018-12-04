package helper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
 // nur für debug zwecke. Die ganze klasse
public class TestPictureCreator {

    private static Double[] pixelArray = new Double[784];

    public static void passValue(int ident, double value){
        pixelArray[ident] = value;
    }


   public static void createPic(int size) {
           int colorInt;
           BufferedImage bufferedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB); // hoehe auf 28x28 festgelegt, da nur mit dem mnist datensatz gearbeitet wird
           for (int iy = 0; iy < size; iy++) {
               for (int ix = 0; ix < size; ix++) {
                    if(ix + (iy * size) > -1){
                        doNothing();
                    }
                   double tempColor =  pixelArray[ix + (iy * size)];
                   colorInt =255 -  (int) ((tempColor * 255));
                   int pixel = (colorInt << 24 | colorInt << 16 | colorInt << 8 | colorInt); // bit shifting um den standards zu entsprchen. red green und blue sind immer 0
                   bufferedImage.setRGB(ix, iy, pixel);
                   new File(System.getenv("APPDATA") + "\\mnist\\").mkdirs();
                   File file = new File(System.getenv("APPDATA") + "\\mnist\\new.png");
                   try {
                       ImageIO.write(bufferedImage, "png", file);
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
           }

    }


     static void doNothing() {

     }
 }
