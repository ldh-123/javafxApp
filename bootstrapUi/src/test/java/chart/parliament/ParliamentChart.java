package chart.parliament;

import javafx.collections.ObservableMap;
import javafx.scene.chart.Chart;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ParliamentChart extends Chart {

    private int totalSeats;
    private double mpCirclesRadius;
    private double diagramMaxRadius;
    private double centerX;

    private double centerY;

    /**
     * Map where the keys are party names and the values are ArrayList containing all the MPs from that party.
     */
    private ObservableMap<String, ArrayList<Mp>> data; //TODO: react to changes in data.

    private ArrayList<MpCircle> circles;

    //Constructor
    public ParliamentChart(ObservableMap<String, ArrayList<Mp>> data, int totalSeats, double mpCirclesRadius, double diagramMaxRadius, double centerX, double centerY){
        this.data = data;
        this.totalSeats = totalSeats;
        this.mpCirclesRadius = mpCirclesRadius;
        this.diagramMaxRadius = diagramMaxRadius;
        this.centerX = centerX;
        this.centerY = centerY;

        makeCircles();
    }

    /**
     * @return The data represented by the chart.
     */
    public ObservableMap<String, ArrayList<Mp>> getData(){
        return data;
    }

    /**
     * @param data The data the chart will represent.
     */
    public void setData(ObservableMap<String, ArrayList<Mp>> data) {
        this.data = data;
    }

    private void makeCircles(){
        int[] series = triangularSeries(totalSeats, 3); //this array contains the size of every row, from the smallest to the biggest
        System.out.println(Arrays.toString(series));//testing
        int y = series.length;

		/*number of points along a circle we need (we need x-1 because t for x0 is 0)
		 * increT is the separation angle between points. Each point will be the center of a MpCircle
		 * PI = 180º in radians -> we want a semi circle
		 */

        double rowRadius = diagramMaxRadius;
        double rowGap = diagramMaxRadius/y;
        double maxGap = mpCirclesRadius*3;

        if(rowGap>maxGap){
            rowGap=maxGap;
            rowRadius=rowGap*y*1.3;
        }

        ArrayList<ArrayList<MpCircle>> rows = new ArrayList<ArrayList<MpCircle>>();
        for(int i=y-1;i>=0;i--){//loop through every row, we read series starting from the last number, the biggest.
            double t = Math.PI;
            int x = series[i];
            double increT = Math.PI/(x-1);
            ArrayList<MpCircle> thisRow = new ArrayList<MpCircle>();

            for(int j=0;j<x;j++){//loop through every element in each row
                double MPcircleCenterX = centerX+rowRadius*Math.cos(t);
                double MPcircleCenterY = centerY+rowRadius*Math.sin(t);
                MpCircle mpCircle = new MpCircle(MPcircleCenterX, MPcircleCenterY, mpCirclesRadius, Color.GRAY);
                getChartChildren().add(mpCircle.getCircle());
                thisRow.add(mpCircle);
                t += increT;
            }
            rowRadius -= rowGap;
            rows.add(thisRow);
        }

        circles=reorderInDiagonals(rows); //references from left side of the diagram to the right

        //we reverse the rows so we can get the references from right to left, in order to make the diagram more symmetrical
        ArrayList<ArrayList<MpCircle>> reverseRows = new ArrayList<ArrayList<MpCircle>>();
        for(ArrayList<MpCircle> thisRow:rows){
            ArrayList<MpCircle> thisReverseRow = thisRow;
            Collections.reverse(thisReverseRow);
            reverseRows.add(thisReverseRow);
        }
        ArrayList<MpCircle> circlesReverse = new ArrayList<MpCircle>();
        circlesReverse = reorderInDiagonals(reverseRows);


        //we assign its MP to each circle
        boolean left=true; //we use this variable to alternately process each party's seats: firstly the leftiest party, then the rightiest party, and so on.
        String party;
        ArrayList<String> parties = new ArrayList<String>(data.keySet()); //we get all the party names into an ArrayList
        int nParties = parties.size();
        for(int i=0,iL=0,iR=nParties-1,nL=0,nR=0;i<nParties&&iR>=0;i++){//executes nParties times.

            if(left){
                party = parties.get(iL); //each side, left and right, has its own counter of parties, that start from opposite limits of the List
                Color partyColor = Color.web(data.get(party).get(0).getColorHTML()); //we get the color from the first member of the party
                ArrayList<Mp> partyMembers = data.get(party);

                for(int s=0;s<party.length();){ //loops through every seat of this party and fill them

                    MpCircle thisCircle = circles.get(nL); //nL keeps the count of the filled seats and the current position, relative to the left edge

					/*
					 * if the circle already has an assigned MP that means that it has already filled from the other edge,
					 * so we skip it without incrementing s
					 */
                    if(thisCircle.getMp()==null){
                        thisCircle.getCircle().setFill(partyColor);
                        thisCircle.setMp(partyMembers.get(s));
                        s++;
                        left = false;
                    }
                    nL++;
                }

                iL++;
            }

            else{
                party = parties.get(iR); //see comment at party=parties.get(iL) (around line 111)
                Color partyColor = Color.web(data.get(party).get(0).getColorHTML()); //we get the color from the first member of the party
                ArrayList<Mp> partyMembers = data.get(party);

                for(int s=0;s<party.length();){ //loops through every seat of this party and fill them

                    MpCircle thisCircle = circlesReverse.get(nR); //nR keeps the count of the filled seats and the current position, relative to the right edge

                    if(thisCircle.getMp()==null){ //see comment in analogous block above
                        thisCircle.getCircle().setFill(partyColor);
                        thisCircle.setMp(partyMembers.get(s));
                        s++;
                        left = true;
                    }
                    nR++;
                }

                iR--;
            }
        }
    }

    /**
     * Given the series S[0],S[1]...,S[n];S[n]=S[n-1]+a
     * t=sum{i=S[0]=b;i&lt;=m}=bm+c; c=aT(m-1); T(x)=(x(x+1))/2
     * Any number f can, of course, be decomposed as: f=t+r; t&gt;r
     * This method calculates, given f and a, the series S[n-1]+a with the biggest possible integer b&lt;m, then calculates r and while r&gt;0,
     * adds 1 to every number in the series starting by the biggest, then return the series.
     * @param f the total number to be decomposed
     * @param a the increment per element of the series
     * @return The series defined above -modified by distributing r among the biggest elements- which sum is f.
     */
    private int[] triangularSeries(int f, int a){
        double b = f;
        double m = 1;

        while(b>m){
            double c = a*((m-1)*m)/2;
            b = (f-c)/m;
            m++;
        }

        m--;
        double c = a*((m-1)*m)/2;
        b = (f-c)/m;

        int mi = (int)Math.floor(m-1);
        int bi = (int) Math.floor(b);
        int ci = a*((mi-1)*mi)/2;
        int t = bi*mi+ci;
        int r = f-t;

        int[] series = new int[mi];
        int s = bi; //S[0]=b
        series[0]=s;
        for(int i=1;i<mi;i++){
            s+=a;
            series[i] = s;
        }

        //now we addNewStage 1 to every number while  r>0, starting by the biggest
        for(int i=0,j=1;i<r;i++,j++){
            if(j>mi){j=1;}
            series[mi-j]++;
        }

        return series;
    }

    /** reorders the references to the circles in diagonal order. Given:
     *  row0= 0 1 2 3 4 5 6
     *  row1= 0 1 2 3
     *  row2= 0
     *
     *  it would return:
     *  0 (at row0,pos0), 1 (0,1), 0 (1,0), 2 (0,2), 1 (1,1), 0 (2,0), 3 (3,0), 2 (1,2), 4 (4,0), 5 (5,0), 6 (6,0).
     */
    private ArrayList<MpCircle> reorderInDiagonals(ArrayList<ArrayList<MpCircle>> rows){

        ArrayList<MpCircle> circlesRef = new ArrayList<MpCircle>();
        int nRow=0;
        int n=0;
        MpCircle chosen = rows.get(nRow).get(n);
        circlesRef.add(chosen);

        for(int i=1,max=1;i<totalSeats;){

            for(int j=0,m=max;j<=max && j<rows.size();j++){

                if(m>=rows.get(j).size()){
                    //System.out.println("heyye");
                    break;
                }

                chosen = rows.get(j).get(m);
                circlesRef.add(chosen);
                i++;
                //System.out.println("max: "+Integer.toString(max)+". j: "+Integer.toString(j)+". m: "+Integer.toString(m)+". i: "+Integer.toString(i));
                m--;
            }

            max++;
        }

        return circlesRef;
    }

    @Override
    protected void layoutChartChildren(double top, double left, double width, double height) {
        // TODO Implement this!
    }
}
