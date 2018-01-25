package chart.parliament;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

/**
 * @author Pedro Victori
 *
 */

public class MathTools {

    //Suppress default constructor for noninstantiability.
    private MathTools(){
        throw new AssertionError();
    }

    public static int[] randomIntegers (int n, int min, int max){ //returns n random numbers picked from the interval  [min,max]
        int[] randomNumbers = new int[n];
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=min; i<=max; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        for (int i=0; i<n; i++) {
            randomNumbers[i] = list.get(i);
        }
        return randomNumbers;
    }
    public static int nextMultipleOfTen (int number){
        int result = (int) (Math.round((number + 5)/ 10.0) * 10.0);
        return result;
    }

    public static int binomialCoefficient (int n, int k){
        if(k==0||n==k){return 1;}
        else{return binomialCoefficient(n-1,k-1)+binomialCoefficient(n-1,k);}
    }

    public static double[] binomialDistribution (int n, double p){
        double[] dist = new double[n+1];
        for(int x = 0;x<=n;x++){
            dist[x] = binomialCoefficient(n,x)*Math.pow(p,x)*Math.pow(1-p,n-x);
        }
        return dist;
    }

    public static double[] multiplyArrayByDouble(double[] array,double n){
        double[] result = new double[array.length];
        for(int i=0;i<array.length;i++){
            result[i]=array[i]*n;
        }
        return result;
    }

    public static ArrayList<Integer> doubleArrayDistributionToInt(double[] array){ //we round down the doubles in array and adjust so the sum of the int[] remains the same than the array's.
        System.out.println("distribución bruta de escaños: "+Arrays.toString(array));//testing
        double sum = DoubleStream.of(array).sum();
        System.out.println("suma escaños:"+ Double.toString(sum)); //testing
        int[] result= new int[array.length];

        for(int i=0;i<array.length;i++){
            result[i] = (int) Math.floor(array[i]);
        }

        System.out.println("distribución sin reparto de sobras: "+Arrays.toString(result));//testing
        int intSum = IntStream.of(result).sum();

        ArrayList<Integer> resultList = new ArrayList<Integer>();
        for(int i=0;i<result.length;i++){resultList.add(result[i]);} //we convert the int[] into an ArrayList out of necessity

        int nLeft = (int) Math.round(sum) - intSum; //we calculate if there's any unit left to give
        System.out.println("Sobran: "+Integer.toString(nLeft));//testing

        if(nLeft>0){//if there are left seats, we give one to each party starting by the smallest one.
            ArrayList<Integer> pending = new ArrayList<Integer>();
            for(int i=0;i<resultList.size();i++){pending.add(resultList.get(i));} //we copy resultList into pending

            int holder = pending.get(findBiggest(pending))*2;

            for(int i=0;i<nLeft;i++){
                int index = findSmallest(pending); //we find the index of the lowest number
                resultList.set(index, resultList.get(index)+1); //we addNewStage 1 to that element in the real list
                pending.set(index,holder); //we addNewStage an impossibly big number to pending so that it's no longer the smallest but holds the place in the index
            }
        }
        return resultList;
    }

    /**
     * Finds the smallest number in the List and return its index
     * @param list List of Number
     * @return the index of the number, an int
     */
    public static int findSmallest(List<? extends Number> list){
        int index = 0;
        for(int i=1;i<list.size();i++){
            Double atI = list.get(i).doubleValue();
            Double atIndex = list.get(index).doubleValue();
            if(atI<atIndex){
                index = i;
            }
        }
        return index;
    }

    /**
     * finds the biggest number in the ArrayList and return its index
     * @param arrayList The target list
     * @return The index of the number, an int
     */
    public static int findBiggest(ArrayList<Integer> arrayList){
        int index = 0;
        for(int i=1;i<arrayList.size();i++){
            if(arrayList.get(i)>arrayList.get(index)){
                index = i;
            }
        }
        return index;
    }

    /**
     * takes a group of t elements, organizes them in an approximation of a 3x1 rectangle, with yi rows of 3xi elements and one row of z elements.
     * @param t The number of elements
     * @return An array of the values x,yi,z.
     */
    //z is the remainder, t-3xi*yi. The methods gives a result in the form {3xi,yi,z}
    public static int[] rectangle3xRemainder(int t){
        double rx = Math.sqrt(t/3);
        int x = 3*(int) Math.floor(rx);
        double y = t/x;
        int yi = (int) Math.floor(y);
        int z = t-x*yi;
        int[] out = {x,yi,z};
        System.out.println("x,y,z: "+Arrays.toString(out));
        return out;
    }

    //takes a grid of numbers filled from left to right (the first row, z, sometimes will only be partially filled)
    //and downwards and gives the indexes of the elements if taken as columns,
    public static int[] horizontalLinesToVertical(int x, int y,int z,int gap){
        int t = x*y+z;
        int[] order = new int[t];
        int a=0;
        int g1=gap*y;
        int zi=0;
        for(int ti=0,yi=0;ti<t;ti++,yi++){
            int pos = yi*x+a+z;

            if(ti==g1&&zi<z){//we sort the z row into the general order
                order[ti]=zi;
                ti++;
                zi++;
                g1+=y+1;
            }
            order[ti]=pos;

            if(yi==y-1){
                yi=-1;
                a++;
            }
        }
        return order;
    }

    /**
     * Gets all Integer in a range
     * @param start The lower limit of the range, inclusive.
     * @param end The upper limit of the range, inclusive.
     * @return A list of Integer that contains every Integer between start and end.
     */
    public static ArrayList<Integer> getAllIntInRange(int start, int end){
        ArrayList<Integer> ints = new ArrayList<Integer>();
        for(int i=start;i<=end;i++){
            ints.add(i);
        }
        return ints;
    }

    /**
     * Round a double to a certain number of decimals.
     * @param value The number that is going to be round.
     * @param places The number of decimals it will contain.
     * @return The rounded number.
     * @throws IllegalArgumentException If places &lt;0.
     */
    public static double round(double value, int places) { //rounds a double 'value' for it to have 'places' number of decimals
        if (places < 0) throw new IllegalArgumentException(); //places needs to be >=0

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
