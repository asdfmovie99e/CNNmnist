package helper;

import java.util.HashMap;

/**
 * Enthält eine Sammlung an Mathe Funktionen.
 * @author Jens Krüger
 * @author Niklas Bruns
 * @author Marc Seibel
 * @version 1.0
 *
 */
public class MathHelper {

    private static HashMap<Integer, Double> sigmoidMap;
    private static boolean sigmoidMapInitialized = false;

    /**
     * Bereitet die HashMap vor, in der die Sigmoid Map gespeichert werden.
     */
    private static void start() {
        // wird ausgeführt um die HashMaps zu erstellen, welche die Berechnung verschnellern
        sigmoidMapInitialized = true;
        sigmoidMap = new HashMap<Integer, Double>();
        for (int i = -8000; i <= 8000; i += 1) {
            double iDouble = i;
            sigmoidMap.put((int)(iDouble ), (double) (1 / (1 + Math.pow(Math.E, -(iDouble / 1000)))));

        }
    }

    /**
     * Gibt einen approximierten Wert der Sigmoid Funktion zurück. Spart Rechenzeit.
     * @param sigmoidX Der x- Wert, zu dem der Sigmoidwert ausgegeben werden soll.
     * @return Der Sigmoidwert zum eingegebenen x-Wert.
     */
    public static Double getSigmoidApprox(Double sigmoidX){
        if (!sigmoidMapInitialized) MathHelper.start();
        if(sigmoidX > 7.9d) return 1d;
        if(sigmoidX < -7.9d) return 0d;
        int MapIndex = (int) (sigmoidX * 1000);
        return sigmoidMap.get(MapIndex);
    }

}
