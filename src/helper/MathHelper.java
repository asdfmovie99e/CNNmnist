package helper;

import java.util.HashMap;

public class MathHelper {

    private static HashMap<Integer, Double> sigmoidMap;
    private static boolean sigmoidMapInitialized = false;

    public static void start() {
        // wird ausgeführt um die HashMaps zu erstellen, welche die Berechnung verschnellern
        sigmoidMapInitialized = true;
        sigmoidMap = new HashMap<Integer, Double>();
        for (int i = -600; i <= 600; i += 1) {
            double iDouble = i;
            sigmoidMap.put((int)(iDouble ), (double) (1 / (1 + Math.pow(Math.E, -(iDouble / 100)))));

        }
    }

    public static Double getSigmoidApprox(Double sigmoidX){
        if (!sigmoidMapInitialized) MathHelper.start();
        if(sigmoidX > 5.9d) return 1d;
        if(sigmoidX < -5.9d) return 0d;
        int MapIndex = (int) (sigmoidX * 100);
        return sigmoidMap.get(MapIndex);
    }

}
