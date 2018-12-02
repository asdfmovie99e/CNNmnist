package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class PictureCoder {
    File trainImages = new File("MNIST/train-images.idx3-ubyte");
    File trainLabels = new File("MNIST/train-images.idx3-ubyte");
    BufferedImage images;
    BufferedImage labels;
    ArrayList<ArrayList<ArrayList<Byte>>> picturelist = new ArrayList<ArrayList<ArrayList<Byte>>>();


    public void readTrainData(Image image) //Bilder einlesen
    {
        try
        {
            images = ImageIO.read(trainImages);
            labels = ImageIO.read(trainLabels);
        }
        catch (IOException io)
        {
        }

        for(int i=16; i<60000; i++)
        {
            ArrayList<ArrayList<Byte>> picture = new ArrayList<ArrayList<Byte>>(); //bild
            for(int ii=0; ii<28; ii++)
            {
                ArrayList<Byte> row = new ArrayList<Byte>(); // reihe
                for(int iii=0; iii<28; iii++)
                {
                    row.add((byte)(images.getRGB(ii, iii)));
                }
                picture.add(row);
            }
            picturelist.add(picture);
        }

    }

    public Byte readPixel(int i, int ii, int iii) //Pixel auslesen
    {
        ArrayList<ArrayList<Byte>> twoDPicture = picturelist.get(i); //i = bild

        ArrayList<Byte> pixelRow = twoDPicture.get(ii); // ii = Reihe

        Byte bytewert = pixelRow.get(iii);    // iii = spalte
        return bytewert;
    }

    public ArrayList<ArrayList<Byte>> readPicture(int i)  //Bild auslesen
    {
        ArrayList<ArrayList<Byte>> twoDPicture = picturelist.get(i); // i = bild
        return twoDPicture;
    }

}
