package helper;

import java.util.HashMap;

public class MathHelper {

    private static HashMap<Double, Double> sigmoidMap;

    public static void start(){
        // wird ausgeführt um die HashMaps zu erstellen, welche die Berechnung verschnellern
        sigmoidMap = new HashMap<Double, Double>();
        for(int i = -600; i <= 600; i += 1){
            double iDouble = i;
            sigmoidMap.put((double)i,  (double) (1 / (1 + Math.pow(Math.E, -(iDouble/100)))));

        }


}
