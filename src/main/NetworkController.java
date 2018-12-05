package main;

import java.util.ArrayList;

public class NetworkController {

    private static final int ANZAHL_INPUT_NEURONS = 28 * 28;
    private static final int ANZAHL_HIDDEN_ONE = 100;
    private static final int ANZAHL_OUTPUT_NEURON = 10;
    private static ArrayList<InputNeuron> inputNeurons = new ArrayList<InputNeuron>();
    private static ArrayList<HiddenFullyMeshedNeuron> hiddenNeuronsOne = new ArrayList<HiddenFullyMeshedNeuron>();
    private static ArrayList<OutputNeuron> outputNeurons = new ArrayList<OutputNeuron>();


    public static void startLearning() {
        instantiateNeurons();
        instantiateEdges();
    }

    private static void instantiateEdges() {


    }

    private static void instantiateNeurons() {
        for(int i = 0; i < ANZAHL_INPUT_NEURONS; i++){
            InputNeuron inputNeuron = new InputNeuron(i);
            inputNeurons.add(inputNeuron);
        }
        for(int i = 0; i < ANZAHL_HIDDEN_ONE; i++){
            HiddenFullyMeshedNeuron hiddenFullyMeshedNeuron = new HiddenFullyMeshedNeuron(i);
            hiddenNeuronsOne.add(hiddenFullyMeshedNeuron);
        }
        for(int i = 0; i < ANZAHL_OUTPUT_NEURON; i++){
            OutputNeuron outputNeuron = new OutputNeuron(i);
            outputNeurons.add(outputNeuron);
        }
    }


    private void connectNeurons(Neuron incomingNeuron, Neuron nextNeuron){
        new Edge(incomingNeuron,nextNeuron);
    }

}
