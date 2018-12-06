package helper;

import main.OutputNeuron;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class LearnObserver {
    private static int[] timesSuccessfull = new int[10];
    private static int[] timesTried = new int[10];
    private static int pictureCounter = 0;



    public static void watchResults(Integer label, ArrayList<OutputNeuron> outputNeurons) {
        try{
        OutputNeuron biggestNeuron = null;
        for(OutputNeuron outputNeuron: outputNeurons){
            if(biggestNeuron == null || outputNeuron.calculateOutput() > biggestNeuron.calculateOutput()){
                biggestNeuron = outputNeuron;
            }
        }

        timesTried[label] += 1;
        pictureCounter++;
        if(pictureCounter % 5000 == 0){
            timesSuccessfull = new int[10];
            timesTried = new int[10];
        }
        if(biggestNeuron.getIdentNumber() == label) {
            timesSuccessfull[biggestNeuron.getIdentNumber()] += 1;
        }
        System.out.println(pictureCounter / 600 + " Prozent abgeschlossen.  "+ (100 * IntStream.of(timesSuccessfull).sum() / IntStream.of(timesTried).sum()) + " Prozent richtig");
        for(int i = 0; i < 10;i++){

                System.out.println("Die Zahl " + (i) + " war zu " + 100 * timesSuccessfull[i] / timesTried[i] + " Prozent richtig.");

            }
        }catch (Exception e){
            System.out.println("Noch nicht genügend Zahlen");
        }
    }
}
