package main;

import helper.LearnObserver;

import java.util.ArrayList;

public class NetworkController {

    private static final int ANZAHL_INPUT_NEURONS = 28 * 28;
    private static final int ANZAHL_HIDDEN_ONE = 100;
    private static final int ANZAHL_OUTPUT_NEURON = 10;
    public static final double EPSILON = 0.05d;
    private static final int ANZAHL_BILDER = 60000;
    private static ArrayList<InputNeuron> inputNeurons = new ArrayList<InputNeuron>();
    private static ArrayList<HiddenNeuron> hiddenNeuronsOne = new ArrayList<HiddenNeuron>();
    private static ArrayList<OutputNeuron> outputNeurons = new ArrayList<OutputNeuron>();


    public static void startLearning() {
        instantiateNeurons();
        instantiateEdges();

        for(int pictureNumber = 0; pictureNumber < ANZAHL_BILDER; pictureNumber++){
        resetAllNeurons();
        sendForward(pictureNumber);
        LearnObserver.watchResults(PictureCoder.getLabel(pictureNumber), outputNeurons);
        doBackPropagation(PictureCoder.getLabel(pictureNumber));
        }
    }

    private static void sendForward(int pictureNumber) {
        int[][] pixelArray = PictureCoder.get2DPictureArray(pictureNumber);
        for(InputNeuron inputNeuron: inputNeurons){
            int x = inputNeuron.getIdentNumber() % 28;
            int y = (inputNeuron.getIdentNumber() - inputNeuron.getIdentNumber() % 28) / 28;
            int pixelValue = pixelArray[x][y];
            inputNeuron.receiveInput(pixelValue);
            inputNeuron.sendOutputToNextEdge();
        }
        for(HiddenNeuron hiddenNeuron : hiddenNeuronsOne){
            hiddenNeuron.sendOutputToNextEdge();
        }
        for(OutputNeuron outputNeuron: outputNeurons){
            outputNeuron.calculateOutput();
        }
    }

    private static void resetAllNeurons() {
        for(HiddenNeuron hiddenNeuron : hiddenNeuronsOne){
            hiddenNeuron.resetInput();
        }
        for(OutputNeuron outputNeuron: outputNeurons){
            outputNeuron.resetInput();
        }

    }

    private static void instantiateEdges() {
        //von input zu hiddenOne
        for(InputNeuron inputNeuron: inputNeurons){
            for(HiddenNeuron hiddenNeuron : hiddenNeuronsOne){
                connectNeurons(inputNeuron, hiddenNeuron);
            }
        }

        //von hiddenOne zum output
        for(HiddenNeuron hiddenNeuron : hiddenNeuronsOne){
            for(OutputNeuron outputNeuron: outputNeurons){
                connectNeurons(hiddenNeuron, outputNeuron);
            }
        }

    }

    private static void instantiateNeurons() {
        for(int i = 0; i < ANZAHL_INPUT_NEURONS; i++){
            InputNeuron inputNeuron = new InputNeuron(i);
            inputNeurons.add(inputNeuron);
        }
        for(int i = 0; i < ANZAHL_HIDDEN_ONE; i++){
            HiddenNeuron hiddenNeuron = new HiddenNeuron(i);
            hiddenNeuronsOne.add(hiddenNeuron);
        }
        for(int i = 0; i < ANZAHL_OUTPUT_NEURON; i++){
            OutputNeuron outputNeuron = new OutputNeuron(i);
            outputNeurons.add(outputNeuron);
        }
    }


    private static void connectNeurons(Neuron previousNeuron, Neuron nextNeuron){
       Edge edge = new Edge(previousNeuron,nextNeuron);
       previousNeuron.addOutgoingEdge(edge);
       nextNeuron.addIncomingEdge(edge);
    }

    private static void doBackPropagation(int label){
        //es werden immer funktionen in den neuronen hinter den edges aufgerufen die angepasst werden
        for(OutputNeuron outputNeuron: outputNeurons){
            if(label == outputNeuron.getIdentNumber()) {
                outputNeuron.modWeight(1d);
            } else{
                outputNeuron.modWeight(0d);
            }
        }
        for(HiddenNeuron hiddenNeuron: hiddenNeuronsOne){
            hiddenNeuron.modWeight();
        }
    }

}
