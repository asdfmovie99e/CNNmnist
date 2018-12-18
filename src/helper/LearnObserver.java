package helper;

import main.OutputNeuron;

import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * Überwacht den Lernprozess.
 * @author Jens Krüger
 * @author Niklas Bruns
 * @author Marc Seibel
 * @version 1.0
 *
 */
public class LearnObserver {
    private static int[] timesSuccessfull = new int[10];
    private static int[] timesTried = new int[10];
    private static int pictureCounter = 0;
    private static Double successRate = null;

    /**
     * Wird bei jeder Lerniteration einmal aufgerufen um die Lernrate aktuell zu halten und dokumentiert den Prozess in der Konsole
     * @param label Die Zahl die zu erkennen war.
     * @param outputNeurons Alle Outputneuronen, um auszuwerten, welches Neuron das höchste war.
     */

    public static void watchResults(Integer label, ArrayList<OutputNeuron> outputNeurons) {
        try{
        OutputNeuron biggestNeuron = null;
        for(OutputNeuron outputNeuron: outputNeurons){
            if(biggestNeuron == null || outputNeuron.calculateOutput() >= biggestNeuron.calculateOutput()){
                biggestNeuron = outputNeuron;
            }
        }

        pictureCounter++;
        if(pictureCounter % 5000 == 1){
            timesSuccessfull = new int[10];
            timesTried = new int[10];
        }
            timesTried[label] += 1;
        if(biggestNeuron.getIdentNumber() == label) {
            timesSuccessfull[biggestNeuron.getIdentNumber()] += 1;
        }
        successRate = (100 * ((double)IntStream.of(timesSuccessfull).sum()) / ((double)IntStream.of(timesTried).sum()));
        System.out.println(pictureCounter / 600 + " Prozent abgeschlossen.  "+ successRate + " Prozent richtig");
        for(int i = 0; i < 10;i++){

                System.out.println("Die Zahl " + (i) + " war zu " + 100 * timesSuccessfull[i] / timesTried[i] + " Prozent richtig.");

            }
        }catch (Exception e){
            System.out.println("Noch nicht genügend Zahlen");
        }
    }

    public static Double getSuccesRate(){

        return successRate;
    }
}
