package helper;

import java.util.HashMap;

public class MathHelper {

    private static HashMap<Double, Double> sigmoidMap;

    public static void start() {
        // wird ausgeführt um die HashMaps zu erstellen, welche die Berechnung verschnellern
        sigmoidMap = new HashMap<Double, Double>();
        for (int i = -600; i <= 600; i += 1) {
            double iDouble = i;
            sigmoidMap.put(((double) i) / 100, (double) (1 / (1 + Math.pow(Math.E, -(iDouble / 100)))));

        }
    }

    public static Double getSigmoidApprox(Double sigmoidX){
        if(sigmoidX > 5.9d) return 1d;
        if(sigmoidX < -5.9d) return 0d;
        int MapIndex = (int) (sigmoidX * 100);
        return sigmoidMap.get(MapIndex);
    }

}
