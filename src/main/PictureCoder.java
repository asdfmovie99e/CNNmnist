package main;

import java.io.File;

public class PictureCoder
{
    public static Integer [] picture; //Zahlen von 0-9
    public static byte [][][] pixel; // Bild(0-60k), X, Y

    public void readTrainData ()
    {
       File trainImages = new File("MNIST/train-images.idx3-ubyte");
       File trainLabnels = new File("MNIST/train-images.idx3-ubyte");
    }

    public byte[][] pixelsOfPicture (Integer a) //0-60k um PixelArray zurückzubekommen
    {
        return pixel[a];
    }

    public Integer[] pictureID () // Nummer, die auf dem Bild zu sehen ist
    {
        return picture;
    }


    for (int i = 16; i < 60000*28*28;i++) //60k Bilder á 28*28 Pixel
    {
        int modOfPicture = i % (28*28);   // z.B. 1600 mod 784 = 32
        int roundedPicture = i - modOfPicture;    // 1600 - 32 = 1568

        if (modOfPicture == 0)
        {
            int pictureNumber = (roundedPicture / (28*28)); // 1568 / 784 = 2
        }
        else
        {
            int pictureNumber = (roundedPicture / (28*28))+1; // 1568 / 784 = 2 +1, weil 1600 ist 3tes Bild und nicht 2tes
        }


        int modOfPixel = i % 28;                // z.B. 30 mod 28 = 2
        int roundedPixel = i - modOfPixel;      // 30 - 2 = 28

        if (modOfPixel == 0)
        {
            int rownumber = (roundedPixel / 28);    // 28 / 1 = 1
        }
        else
        {
            int rownumber = (roundedPixel / 28)+1;    // 28 / 1 = 1 +1, weil 30 ist 2te Reihe
        }
    }
    

}
