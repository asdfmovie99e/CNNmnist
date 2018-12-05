package helper;

import main.OutputNeuron;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class LearnObserver {
    private static int[] timesSuccessfull = new int[10];
    private static int[] timesTried = new int[10];



    public static void watchResults(Integer label, ArrayList<OutputNeuron> outputNeurons) {
        OutputNeuron biggestNeuron = null;
        for(OutputNeuron outputNeuron: outputNeurons){
            if(biggestNeuron == null || outputNeuron.calculateOutput() > biggestNeuron.calculateOutput()){
                biggestNeuron = outputNeuron;
            }
        }

        timesTried[label] += 1;
        if(IntStream.of(timesTried).sum() % 5000 == 0){
            timesSuccessfull = new int[10];
            timesTried = new int[10];
        }
        if(biggestNeuron.getIdentNumber() == label) {
            timesSuccessfull[biggestNeuron.getIdentNumber()] += 1;
        }
        System.out.println(IntStream.of(timesTried).sum() / 600 + " Prozent abgeschlossen.");
        for(int i = 0; i < 10;i++){
            try {
                System.out.println("Die Zahl " + (i + 1) + " war zu " + 100 * timesSuccessfull[i] / timesTried[i] + " Prozent richtig.");
            }catch (Exception e){
                System.out.println("Noch nicht genügend Zahlen");
            }
            }
    }
}
