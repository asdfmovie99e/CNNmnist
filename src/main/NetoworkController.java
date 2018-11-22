package main;

public class NetoworkController {
    //das netz ist so gedacht, dass die erste schicht eine normle input schicht ist. die zweite ist eine convolutional schicht und die 3. maxpooling und die 4. vollvermascht und die letzt output
    private static final int ANZAHL_INPUT_NEURONEN = 748;
    private static final int ANZAHL_HIDDEN_NEURONEN_ONE = 1;
    private static final int ANZAHL_HIDDEN_NEURONEN_TWO = 1;
    private static final int ANZAHL_HIDDEN_NEURONEN_THREE = 1; // eigentlich variabel. muss mal mit rumgespielt werden
    private static final int ANZAHL_OUTPUT_NEURONEN = 10;
    // es wird von einer 4x4 convolution schicht und einer 2x2 max pooling schicht ausgegangen
    private static final int ANZAHL_EGDES =     (ANZAHL_INPUT_NEURONEN  *  ANZAHL_INPUT_NEURONEN) * 16 +
                                                ANZAHL_HIDDEN_NEURONEN_ONE +
                                                ANZAHL_HIDDEN_NEURONEN_TWO * ANZAHL_HIDDEN_NEURONEN_THREE +
                                                ANZAHL_HIDDEN_NEURONEN_THREE *ANZAHL_OUTPUT_NEURONEN;
    private static final int ANZAHL_BILDER = 59000;
    private static final int PIXEL_X = 28;
    private static final int PIXEL_Y = 28;
    private static InputNeuron[] inputNeurons = new InputNeuron[ANZAHL_INPUT_NEURONEN];
    private static HiddenNeuron[] hiddenNeuronsOne= new HiddenNeuron[ANZAHL_HIDDEN_NEURONEN_ONE];
    private static HiddenNeuron[] hiddenNeuronsTwo= new HiddenNeuron[ANZAHL_HIDDEN_NEURONEN_TWO];
    private static HiddenNeuron[] hiddenNeuronsThree= new HiddenNeuron[ANZAHL_HIDDEN_NEURONEN_THREE];
    private static OutputNeuron[] outputNeurons = new OutputNeuron[ANZAHL_OUTPUT_NEURONEN];

    public static void startLearning(){
        createNeuronNetwork();
        sendForward();
    }

    private static void createNeuronNetwork(){
        for(int i = 0; i < ANZAHL_INPUT_NEURONEN; i++){
            inputNeurons[i]  = new InputNeuron(i);
        }
        for(int i = 0; i < ANZAHL_HIDDEN_NEURONEN_ONE; i++){
            hiddenNeuronsOne[i] = new HiddenNeuron(i);
        }
        for(int i = 0; i < ANZAHL_HIDDEN_NEURONEN_TWO; i++){
            hiddenNeuronsTwo[i] = new HiddenNeuron(i);
        }
        for(int i = 0; i < ANZAHL_HIDDEN_NEURONEN_THREE; i++){
            hiddenNeuronsThree[i] = new HiddenNeuron(i);
        }
    }

    private static void sendForward(){

    }

}
