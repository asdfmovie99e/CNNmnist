package main;

import helper.DBConnect;
import helper.LearnObserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class NetworkController {

    private static int ANZAHL_INPUT_NEURONS = 28 * 28;
    private static int ANZAHL_HIDDEN_ONE = 784;
    private static int ANZAHL_HIDDEN_TWO = 115;
    private static int ANZAHL_OUTPUT_NEURON = 10;
    public static final double EPSILON = 0.05d;
    private static final int ANZAHL_BILDER = 60000;
    private static ArrayList<InputNeuron> inputNeurons = new ArrayList<InputNeuron>();
    private static ArrayList<HiddenNeuron> hiddenNeuronsOne = new ArrayList<HiddenNeuron>();
    private static ArrayList<HiddenNeuron> hiddenNeuronsTwo = new ArrayList<HiddenNeuron>();
    private static ArrayList<OutputNeuron> outputNeurons = new ArrayList<OutputNeuron>();


    public static void startLearning() {
        instantiateNeurons();
        instantiateEdges();

        for(int gesamtPictureNumber = 0; gesamtPictureNumber < ANZAHL_BILDER ; gesamtPictureNumber++){
            int pictureNumber = gesamtPictureNumber % ANZAHL_BILDER;
        resetAllNeurons();
        sendForward(pictureNumber);
        LearnObserver.watchResults(PictureCoder.getLabel(pictureNumber), outputNeurons);
        doBackPropagation(PictureCoder.getLabel(pictureNumber));
        }
        saveCurrentNetwork();
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
        for(HiddenNeuron hiddenNeuron : hiddenNeuronsTwo){
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
        for(HiddenNeuron hiddenNeuron : hiddenNeuronsTwo){
            hiddenNeuron.resetInput();
        }
        for(OutputNeuron outputNeuron: outputNeurons){
            outputNeuron.resetInput();
        }

    }

    private static void instantiateEdges() {
        if(inputNeurons.get(0).outgoingEdges.size()> 0) return; // bricht hier ab falls es schon edges gibt
        InputNeuron[] inputNeuronsArray =  inputNeurons.toArray(new InputNeuron[inputNeurons.size()]);
        HiddenNeuron[] hiddenNeuronsOneArray = hiddenNeuronsOne.toArray(new HiddenNeuron[hiddenNeuronsOne.size()]);
/*
            for(HiddenNeuron hiddenNeuron : hiddenNeuronsOne){
                for(int x = -1; x <= 1; x++){
                    for(int y = -1; y <= 1;y++){
                        int inputNeuronIdent = hiddenNeuron.getIdentNumber() + x + y * 28;
                        if(hiddenNeuron.getIdentNumber() >=28 && hiddenNeuron.getIdentNumber() <= 27*28 && hiddenNeuron.getIdentNumber() % 28 != 0 && hiddenNeuron.getIdentNumber() % 28 != 27) {
                            connectNeurons(inputNeuronsArray[inputNeuronIdent], hiddenNeuron);
                        }
                    }
                }

            }

*/

        for(InputNeuron inputNeuron: inputNeurons){
            int debugNr = inputNeuron.getIdentNumber(); // DELETE!! ONLY FOR DEBUG PURPOSES
            for(int x = -1; x <= 1; x++){
                for(int y = -1; y <= 1; y++){
                    int inputNr = inputNeuron.getIdentNumber();
                    int targetNr = inputNeuron.getIdentNumber() + x + y * 28;
                    if(targetNr < 0 || targetNr > 28 * 28 || (targetNr % 28 == 0 && x == 1) || (targetNr % 28 == 27 && x== -1)){
                        continue;
                    }
                    connectNeurons(inputNeuron, hiddenNeuronsOneArray[targetNr]);
                }
            }
        }

        //von hiddenOne zum output
        for(HiddenNeuron hiddenNeuron1 : hiddenNeuronsOne){
            for(HiddenNeuron hiddenNeuron2: hiddenNeuronsTwo){
                connectNeurons(hiddenNeuron1, hiddenNeuron2);
            }
        }
        for(HiddenNeuron hiddenNeuron : hiddenNeuronsTwo){
            for(OutputNeuron outputNeuron: outputNeurons){
                connectNeurons(hiddenNeuron, outputNeuron);
            }
        }

    }
    @Deprecated
    private static <NeuronType extends Neuron> Object[] arrayListToArrayByIdent(ArrayList<NeuronType> neuronList) {
        NeuronType[] resultArray =(NeuronType[]) new Object[neuronList.size()];
        for(int i = 0; i < neuronList.size(); i++){
            resultArray[((NeuronType)neuronList.get(i)).getIdentNumber()] = (NeuronType) neuronList.get(i);
        }

        return resultArray;
    }

    private static void instantiateNeurons() {
        if(inputNeurons.size() > 0) return; // falls die neuronen schon existerien  passiert hier nichts
        for(int i = 0; i < ANZAHL_INPUT_NEURONS; i++){
            InputNeuron inputNeuron = new InputNeuron(i);
            inputNeurons.add(inputNeuron);
        }
        for(int i = 0; i < ANZAHL_HIDDEN_ONE; i++){
            HiddenNeuron hiddenNeuron = new HiddenNeuron(i);
            hiddenNeuronsOne.add(hiddenNeuron);
        }
        for(int i = 0; i < ANZAHL_HIDDEN_TWO; i++){
            HiddenNeuron hiddenNeuron = new HiddenNeuron(i);
            hiddenNeuronsTwo.add(hiddenNeuron);
        }
        for(int i = 0; i < ANZAHL_OUTPUT_NEURON; i++){
            OutputNeuron outputNeuron = new OutputNeuron(i);
            outputNeurons.add(outputNeuron);
        }
    }


    private static Edge connectNeurons(Neuron previousNeuron, Neuron nextNeuron){
       Edge edge = new Edge(previousNeuron,nextNeuron);
       previousNeuron.addOutgoingEdge(edge);
       nextNeuron.addIncomingEdge(edge);
       return edge;
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
        for(HiddenNeuron hiddenNeuron: hiddenNeuronsTwo) {
            hiddenNeuron.modWeight();
        }
        for(HiddenNeuron hiddenNeuron: hiddenNeuronsOne){
            hiddenNeuron.modWeight();

        }
    }

    private static void saveCurrentNetwork(){
        //Freie Save Nummer finden
        Integer[] occupiedSaveNumbers = DBConnect.getAllSaveNumbers();
        Integer saveNr = 0;
        while(Arrays.asList(occupiedSaveNumbers).contains(saveNr)){
            saveNr++;
        }
        //in den Haupttable Schreiben
        DBConnect.addMainTableEntry(saveNr, ANZAHL_INPUT_NEURONS,ANZAHL_HIDDEN_ONE,ANZAHL_HIDDEN_TWO,
                                    0, 0, 0,
                                    ANZAHL_OUTPUT_NEURON,LearnObserver.getSuccesRate());
        Integer edgeNumber = 0;
        for(InputNeuron inputNeuron:inputNeurons){
            HashSet<Edge> outgoingEdges = inputNeuron.getOutgoingEdges();
            for(Edge edge: outgoingEdges){
                DBConnect.addEdge(saveNr, 0,edgeNumber,edge.getPreviousNeuron().getIdentNumber(), edge.getNextNeuron().getIdentNumber(), edge.getCurrentWeight());
                edgeNumber++;
            }
        }
        edgeNumber = 0;
        for(HiddenNeuron hiddenNeuronOne: hiddenNeuronsOne){
            HashSet<Edge> outgoingEdges = hiddenNeuronOne.getOutgoingEdges();
            for(Edge edge: outgoingEdges){
                DBConnect.addEdge(saveNr,1,edgeNumber,edge.getPreviousNeuron().getIdentNumber(), edge.getNextNeuron().getIdentNumber(), edge.getCurrentWeight());
                edgeNumber++;
            }
        }
        edgeNumber = 0;
        for(HiddenNeuron hiddenNeuronTwo: hiddenNeuronsTwo){
            HashSet<Edge> outgoingEdges = hiddenNeuronTwo.getOutgoingEdges();
            for(Edge edge: outgoingEdges){
                DBConnect.addEdge(saveNr,2,edgeNumber,edge.getPreviousNeuron().getIdentNumber(), edge.getNextNeuron().getIdentNumber(), edge.getCurrentWeight());
                edgeNumber++;
            }
        }



    }

    public static void loadDataFromDb (Integer saveNr)
    {
       Object[] obArray = DBConnect.getMainTableEntry(saveNr);
       ANZAHL_INPUT_NEURONS = (Integer) obArray[2];
       ANZAHL_HIDDEN_ONE = (Integer) obArray[3];
       ANZAHL_HIDDEN_TWO = (Integer) obArray[4];
       ANZAHL_OUTPUT_NEURON = (Integer) obArray[8];
        for(int i = 0; i < ANZAHL_INPUT_NEURONS; i++){
            InputNeuron inputNeuron = new InputNeuron(i);
            inputNeurons.add(inputNeuron);
        }
        for(int i = 0; i < ANZAHL_HIDDEN_ONE; i++){
            HiddenNeuron hiddenNeuron = new HiddenNeuron(i);
            hiddenNeuronsOne.add(hiddenNeuron);
        }
        for(int i = 0; i < ANZAHL_HIDDEN_TWO; i++){
            HiddenNeuron hiddenNeuron = new HiddenNeuron(i);
            hiddenNeuronsTwo.add(hiddenNeuron);
        }
        for(int i = 0; i < ANZAHL_OUTPUT_NEURON; i++){
            OutputNeuron outputNeuron = new OutputNeuron(i);
            outputNeurons.add(outputNeuron);
        }
        Integer layerZeroEdgeCount = DBConnect.getEgeCountInLayer(saveNr, 0);
        Integer layerOneEdgeCount = DBConnect.getEgeCountInLayer(saveNr, 1);
        Integer layerTwoEdgeCount = DBConnect.getEgeCountInLayer(saveNr, 2);

        for(int edgeNumber = 0; edgeNumber < layerZeroEdgeCount; edgeNumber++){
            Object[] edgeValues = DBConnect.getEdgeEntry(saveNr,0, edgeNumber);
            Neuron preNeuron = inputNeurons.get((Integer) edgeValues[0]);
            Neuron nextNeuron = hiddenNeuronsOne.get((Integer) edgeValues[1]);
            Double weight = (Double) edgeValues[2];
            Edge edge = connectNeurons(preNeuron,nextNeuron);
            edge.setNewWeight(weight);
        }

        for(int edgeNumber = 0; edgeNumber < layerOneEdgeCount; edgeNumber++){
            Object[] edgeValues = DBConnect.getEdgeEntry(saveNr,1, edgeNumber);
            Neuron preNeuron = hiddenNeuronsOne.get((Integer) edgeValues[0]);
            Neuron nextNeuron = hiddenNeuronsTwo.get((Integer) edgeValues[1]);
            Double weight = (Double) edgeValues[2];
            Edge edge = connectNeurons(preNeuron,nextNeuron);
            edge.setNewWeight(weight);
        }

        for(int edgeNumber = 0; edgeNumber < layerTwoEdgeCount; edgeNumber++){
            Object[] edgeValues = DBConnect.getEdgeEntry(saveNr,2, edgeNumber);
            Neuron preNeuron = hiddenNeuronsTwo.get((Integer) edgeValues[0]);
            Neuron nextNeuron = outputNeurons.get((Integer) edgeValues[1]);
            Double weight = (Double) edgeValues[2];
            Edge edge = connectNeurons(preNeuron,nextNeuron);
            edge.setNewWeight(weight);
        }


    }
}
