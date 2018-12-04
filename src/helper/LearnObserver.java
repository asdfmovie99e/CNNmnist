package helper;

public class LearnObserver {
    private static int[] timesSuccessfull = new int[10];
    private static int[] timesTried = new int[10];


    public static void incSucces(int zahl){
        timesSuccessfull[zahl]++;
    }

    public static void incTried(int zahl){
        timesTried[zahl]++;
    }

    public static void reset(){
         timesSuccessfull = new int[10];
         timesTried = new int[10];
    }

    public static void showResults(){
        for(int i = 0; i < 10;i++){
            if(timesTried[i] == 0) return;
            System.out.println("Die Zahl " + i + " ist zu " + 100 * timesSuccessfull[i] / timesTried[i] + " richtig.");
        }
    }
}
