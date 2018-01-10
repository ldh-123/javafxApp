package chart.parliament;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Test extends Application{
    //TESTING VALUES
    private int totalSeats = 350;
    private double mpCirclesRadius = 10;
    private double diagramMaxRadius = 700;
    private double centerX = 600;
    private double centerY = 500;
    private String pathToPhoto = "/images/char-60.png";

    private ObservableMap<String, ArrayList<Mp>> dataObs;
    //----------

    @Override
    public void start(Stage primaryStage) {

        double centerModifier=1;
        if(totalSeats>200){
            centerModifier = totalSeats/250;
        }
        Map<String, ArrayList<Mp>> data = new HashMap<String, ArrayList<Mp>>();
        String party1 = "Socialist Party";
        ArrayList<Mp> mpsParty1 = genParty(party1, 80, "red");
        data.put(party1, mpsParty1);

        String party2 = "Progressive Party";
        ArrayList<Mp> mpsParty2 = genParty(party2, 80, "red");
        data.put(party2, mpsParty2);

        String party3 = "Moderate Party";
        ArrayList<Mp> mpsParty3 = genParty(party3, 80, "red");
        data.put(party3, mpsParty3);

        String party4 = "Liberal Party";
        ArrayList<Mp> mpsParty4 = genParty(party4, 80, "red");
        data.put(party4, mpsParty4);

        String party5 = "Conservative Party";
        ArrayList<Mp> mpsParty5 = genParty(party5, 80, "red");
        data.put(party5, mpsParty5);

        dataObs = FXCollections.observableMap(data);



        ParliamentChart chart = new ParliamentChart(dataObs, totalSeats, mpCirclesRadius, diagramMaxRadius, centerX, centerY*centerModifier);

        Pane pane = new Pane(chart);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public ArrayList<Mp> genParty(String partyName, int seats, String colorParty){

        ArrayList<Mp> mpsParty = new ArrayList<Mp>();
        for (int i = 0; i < seats; i++) {
            String name = TextTools.genRandomName("startsyl.txt", null, 2, false)+" "+TextTools.genRandomName("startsyl.txt", null, 3, false);
            File photoFile = new File(Test.class.getResource(pathToPhoto).toExternalForm());
            Mp mp = new Mp(name, partyName, photoFile, colorParty);
            mpsParty.add(mp);
        }
        return mpsParty;
    }

    public static String genRandomName(String sylFile,String prefixFile,int nSyl,boolean prefix){ //generates a random name of nSyl syllables taken from filename, with randomly occurring prefix when prefix=TRUE
        String name=null;
        TextFile syllables = new TextFile(sylFile);
        int linesNumber = syllables.getLinesNumber(); //number of lines in this file
        int[] randomNumbers = MathTools.randomIntegers(nSyl+2,1,linesNumber); //we generate an extra random number to decide if we add a prefix, and another one to decide which prefix
        for (int i=0;i<nSyl;i++){
            String newPart = syllables.getLine(randomNumbers[i]);
            if(name==null) name = newPart;
            else name += newPart;
        }

        name = TextTools.firstLetterToUpperCase(name);
        if(prefix && randomNumbers[nSyl]%2==0){ //if the random number is even (p=0.5) we add a prefix) [nSyl] indicates the number before last, the last one would be nSyl+1, since the length of the array is nSyl+2
            TextFile prefixes = new TextFile(prefixFile);
            int pLinesNumber = prefixes.getLinesNumber(); //number of lines in this file
            long dPrefixNumber = Math.round(((double)randomNumbers[nSyl+1]*(double)pLinesNumber)/(double)linesNumber);//we transform the random number (which is between 0 and linesNumber from syllables file) into a number that is between 0 and the numbers of lines of the prefix file
            int prefixNumber = Math.toIntExact(dPrefixNumber);
            if(prefixNumber==0)prefixNumber=1;
            name = prefixes.getLine(prefixNumber) + " "+ name;
        }

        return name;
    }

}
