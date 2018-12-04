package main;
//:)
import helper.LearnObserver;
import helper.TestPictureCreator;

public class NetworkController {
    public static final double EPSILON = 0.01d;
    //das netz ist so gedacht, dass die erste schicht eine normle input schicht ist.
    // die zweite ist eine convolutional schicht und die 3. maxpooling
    // die 4. wieder conv dann wieder maxpool und an 6. stelle output
    private static final int ANZAHL_INPUT_NEURONEN = 784;
    private static final int ANZAHL_HIDDEN_NEURONEN_ONE = 784; //conv mit 3x3 filter
    private static final int ANZAHL_HIDDEN_NEURONEN_TWO = 196; // max pool 2x2 wird zu 1
    private static final int ANZAHL_HIDDEN_NEURONEN_THREE = 196; //conv mit 3x3 filter
    private static final int ANZAHL_HIDDEN_NEURONEN_FOUR = 49; // max pool 2x2 wird zu 1
    private static final int ANZAHL_HIDDEN_NEURONEN_FIVE = 100;// voll vermaschte hidden schicht
    private static final int ANZAHL_OUTPUT_NEURONEN = 10;
    // es wird von einer 4x4 convolution schicht und einer 2x2 max pooling schicht ausgegangen
    private static final int ANZAHL_EGDES = 123456789; // muss noch manuel eingegeben werden
    private static final int ANZAHL_BILDER = 59000;
    private static final int PIXEL_X = 28;
    private static final int PIXEL_Y = 28;
    private static InputNeuron[] inputNeurons = new InputNeuron[ANZAHL_INPUT_NEURONEN];
    private static HiddenNeuron[] hiddenNeuronsOne = new HiddenNeuron[ANZAHL_HIDDEN_NEURONEN_ONE];
    private static HiddenNeuron[] hiddenNeuronsTwo = new HiddenNeuron[ANZAHL_HIDDEN_NEURONEN_TWO];
    private static HiddenNeuron[] hiddenNeuronsThree = new HiddenNeuron[ANZAHL_HIDDEN_NEURONEN_THREE];
    private static HiddenNeuron[] hiddenNeuronsFour = new HiddenNeuron[ANZAHL_HIDDEN_NEURONEN_FOUR];
    private static HiddenNeuron[] hiddenNeuronsFive = new HiddenNeuron[ANZAHL_HIDDEN_NEURONEN_FIVE];
    private static OutputNeuron[] outputNeurons = new OutputNeuron[ANZAHL_OUTPUT_NEURONEN];
    private static int edgeCounter = 0;

    public static void startLearning() {
        createNeuronNetwork();

        for (int pictureNumber = 0; pictureNumber < ANZAHL_BILDER; pictureNumber++) {
            sendForward(PictureCoderOld.get2DPictureArray(pictureNumber));
            backwardPropagation(PictureCoderOld.getLabel(pictureNumber));
            resetNeurons();

            if(pictureNumber % 50 == 0){
                LearnObserver.showResults();
                System.out.println(pictureNumber);
            }
            if(pictureNumber % 1000 == 0){
                LearnObserver.reset();
            }
        }
    }

    private static void resetNeurons() {
        for (int i = 0; i < ANZAHL_HIDDEN_NEURONEN_ONE; i++){
            hiddenNeuronsOne[i].resetInputSum();
        }
        for (int i = 0; i < ANZAHL_HIDDEN_NEURONEN_TWO; i++){
            (hiddenNeuronsTwo[i]).resetInputSum();
        }
        for (int i = 0; i < ANZAHL_HIDDEN_NEURONEN_THREE; i++){
            hiddenNeuronsThree[i].resetInputSum();
        }
        for (int i = 0; i < ANZAHL_HIDDEN_NEURONEN_FOUR; i++){
            hiddenNeuronsFour[i].resetInputSum();
        }
        for (int i = 0; i < ANZAHL_HIDDEN_NEURONEN_FIVE; i++){
            hiddenNeuronsFive[i].resetInputSum();
        }
    }

    private static void backwardPropagation(Integer trueLabel) {
        OutputNeuron highestOutput = null;
        for(OutputNeuron outputNeuron: outputNeurons){
            if(trueLabel == outputNeuron.getId()){
                outputNeuron.sendDeltaToEdge(1);
                LearnObserver.incTried(outputNeuron.getId());
            }else{
                outputNeuron.sendDeltaToEdge(0);
            }

            if(highestOutput == null || highestOutput.getOutputvalue() < outputNeuron.getOutputvalue()){
                highestOutput = outputNeuron;
            }
        }
        if(highestOutput.getId() == trueLabel){
            LearnObserver.incSucces(trueLabel);
        }
        for(HiddenNeuron hiddenNeuron: hiddenNeuronsFive){
            hiddenNeuron.sendDeltaToEdge();
        }
        for(HiddenNeuron hiddenNeuron: hiddenNeuronsFour){
            hiddenNeuron.sendDeltaToEdge();
        }
        for(HiddenNeuron hiddenNeuron: hiddenNeuronsThree){
            hiddenNeuron.sendDeltaToEdge();
        }
        for(HiddenNeuron hiddenNeuron: hiddenNeuronsTwo){
            hiddenNeuron.sendDeltaToEdge();
        }
        for(HiddenNeuron hiddenNeuron: hiddenNeuronsOne){
            hiddenNeuron.sendDeltaToEdge();
        }

    }

    private static void createNeuronNetwork() {
        for (int i = 0; i < ANZAHL_INPUT_NEURONEN; i++) {
            inputNeurons[i] = new InputNeuron(i);
        }
        for (int i = 0; i < ANZAHL_HIDDEN_NEURONEN_ONE; i++) {
            hiddenNeuronsOne[i] = new HiddenNeuron(i);
        }
        for (int i = 0; i < ANZAHL_HIDDEN_NEURONEN_TWO; i++) {
            hiddenNeuronsTwo[i] = new PoolHiddenNeuron(i);
        }
        for (int i = 0; i < ANZAHL_HIDDEN_NEURONEN_THREE; i++) {
            hiddenNeuronsThree[i] = new HiddenNeuron(i);
        }
        for (int i = 0; i < ANZAHL_HIDDEN_NEURONEN_FOUR; i++) {
            hiddenNeuronsFour[i] = new PoolHiddenNeuron(i);
        }
        for (int i = 0; i < ANZAHL_HIDDEN_NEURONEN_FIVE; i++) {
            hiddenNeuronsFive[i] = new HiddenNeuron(i);
        }
        for (int i = 0; i < ANZAHL_OUTPUT_NEURONEN; i++) {
            outputNeurons[i] = new OutputNeuron(i);
        }
        createFirstEdgeLayer();
        createSecondEdgeLayer();
        createThirdEdgeLayer();
        createFourthEdgeLayer();
        createFifthEdgeLayer();
        createSixthEdgeLayer();
    }

    private static void sendForward(int[][] pixelArray) {
        /*for (int i = 0; i < ANZAHL_INPUT_NEURONEN; i++) {// input neuronen senden schleife
            for(int yAxis = 0; yAxis < 28; yAxis++){
                for(int xAxis = 0; xAxis < 28; xAxis++){
                    try{
                    inputNeurons[xAxis + 28*yAxis].receiveInput(pixelArray[xAxis][yAxis] / 255d);

                        }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            inputNeurons[i].activateOutgoingEdges();
            TestPictureCreator.passValue(i,inputNeurons[i].getOutputvalue()); // DEBUG PURPOSE

        }*/
        for(int yAxis = 0; yAxis < 28; yAxis++) {
            for (int xAxis = 0; xAxis < 28; xAxis++) {
                inputNeurons[xAxis + 28*yAxis].receiveInput(pixelArray[xAxis][yAxis] / 255d);
                inputNeurons[xAxis + 28*yAxis].activateOutgoingEdges();
               // TestPictureCreator.passValue(xAxis + 28*yAxis,inputNeurons[xAxis + 28*yAxis].getOutputvalue()); // DEBUG PURPOSE
            }
        }



        //TestPictureCreator.createPic();// debug
        for (int i = 0; i < ANZAHL_HIDDEN_NEURONEN_ONE; i++){
            hiddenNeuronsOne[i].activateOutgoingEdges();
        }
        for (int i = 0; i < ANZAHL_HIDDEN_NEURONEN_TWO; i++){
            hiddenNeuronsTwo[i].activateOutgoingEdges();

        }
        for (int i = 0; i < ANZAHL_HIDDEN_NEURONEN_THREE; i++){
            hiddenNeuronsThree[i].activateOutgoingEdges();
        }
        for (int i = 0; i < ANZAHL_HIDDEN_NEURONEN_FOUR; i++){
            hiddenNeuronsFour[i].activateOutgoingEdges();
            TestPictureCreator.passValue(i,hiddenNeuronsFour[i].getOutputvalue()); // DEBUG PURPOSE
        }
        TestPictureCreator.createPic(7);// debug
        for (int i = 0; i < ANZAHL_HIDDEN_NEURONEN_FIVE; i++){
            hiddenNeuronsFive[i].activateOutgoingEdges();
        }
    }

    private static void createFirstEdgeLayer() {
        int neuronOneRoot = (int) Math.pow(ANZAHL_HIDDEN_NEURONEN_ONE, 0.5d);
        for (int i = 0; i < ANZAHL_HIDDEN_NEURONEN_ONE; i++) {
            for (int i1 = -1; i1 <= 1; i1++) {
                for (int i2 = -1; i2 <= 1; i2++) {
                    int outgoingIdent = i + i1 - (neuronOneRoot * i2);
                    if (0 <= outgoingIdent && outgoingIdent < ANZAHL_INPUT_NEURONEN) {
                        connectNeurons(inputNeurons[outgoingIdent], hiddenNeuronsOne[i]);
                    }
                }
            }
        }
    }

    private static void createSecondEdgeLayer() {
        int neuronOneRoot = (int) Math.pow(ANZAHL_HIDDEN_NEURONEN_ONE, 0.5d);//es ist kein fehler, dass es wieder neuronOneRoot ist
        for (int i = 0; i < ANZAHL_HIDDEN_NEURONEN_TWO; i++) {
            for (int i1 = 0; i1 <= 1; i1++) {
                for (int i2 = 0; i2 <= 1; i2++) {
                    int outgoingIdent = i2 + i1 * neuronOneRoot + (i % (neuronOneRoot / 2)) * 2 + (i - (i % (neuronOneRoot / 2))) * 4;//formel nicht anpacken. werde ich nie wieder verstehen
                    if (0 <= outgoingIdent && outgoingIdent < ANZAHL_HIDDEN_NEURONEN_ONE) {
                        connectNeurons(hiddenNeuronsOne[outgoingIdent], hiddenNeuronsTwo[i]);
                    }
                }
            }
        }
    }

    private static void createThirdEdgeLayer() {
        int neuronThreeRoot = (int) Math.pow(ANZAHL_HIDDEN_NEURONEN_THREE, 0.5d);
        for (int i = 0; i < ANZAHL_HIDDEN_NEURONEN_THREE; i++) {
            for (int i1 = -1; i1 <= 1; i1++) {
                for (int i2 = -1; i2 <= 1; i2++) {
                    int outgoingIdent = i + i1 - (neuronThreeRoot * i2);
                    if (0 <= outgoingIdent && outgoingIdent < ANZAHL_HIDDEN_NEURONEN_TWO) {
                        connectNeurons(hiddenNeuronsTwo[outgoingIdent], hiddenNeuronsThree[i]);
                    }
                }
            }
        }

    }

    private static void createFourthEdgeLayer() {
        int neuronThreeRoot = (int) Math.pow(ANZAHL_HIDDEN_NEURONEN_THREE, 0.5d);//es ist kein fehler, dass es wieder neuronThreeRoot ist
        for (int i = 0; i < ANZAHL_HIDDEN_NEURONEN_FOUR; i++) {
            for (int i1 = 0; i1 <= 1; i1++) {
                for (int i2 = 0; i2 <= 1; i2++) {
                    int outgoingIdent = i2 + i1 * neuronThreeRoot + (i % (neuronThreeRoot / 2)) * 2 + (i - (i % (neuronThreeRoot / 2))) * 4;//formel nicht anpacken. werde ich nie wieder verstehen
                    if (0 <= outgoingIdent && outgoingIdent < ANZAHL_HIDDEN_NEURONEN_THREE) {
                        connectNeurons(hiddenNeuronsThree[outgoingIdent], hiddenNeuronsFour[i]);
                    }
                }
            }
        }
    }

    private static void createFifthEdgeLayer() {
        for (int i = 0; i < ANZAHL_HIDDEN_NEURONEN_FOUR; i++) {
            for (int i1 = 0; i1 < ANZAHL_HIDDEN_NEURONEN_FIVE; i1++) {
                connectNeurons(hiddenNeuronsFour[i], hiddenNeuronsFive[i1]);
            }
        }
    }

    private static void createSixthEdgeLayer() {
        for (int i = 0; i < ANZAHL_HIDDEN_NEURONEN_FIVE; i++) {
            for (int i1 = 0; i1 < ANZAHL_OUTPUT_NEURONEN; i1++) {
                connectNeurons(hiddenNeuronsFive[i], outputNeurons[i1]);
            }
        }
    }


    private static void connectNeurons(Neuron previousNeuron, Neuron nextNeuron) {
        Edge currentEdge = null;
        currentEdge = new Edge(edgeCounter, previousNeuron, nextNeuron);
        previousNeuron.addOutgoingEdge(currentEdge);
        nextNeuron.addIncomingEdge(currentEdge);
        edgeCounter++;
    }
}
