package chart.parliament;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextTools {
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

        name = firstLetterToUpperCase(name);
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

    public static String firstLetterToUpperCase(String value) {
        // Convert String to char array.
        char[] array = value.toCharArray();
        // Modify first element in array.
        array[0] = Character.toUpperCase(array[0]);
        // Return string.
        return new String(array);
    }

    public static boolean containsOnlyLetters(String test){ //see regex
        Pattern pattern = Pattern.compile("^[a-zA-ZñáéíóúÁÉÍÓÚ]+$");
        Matcher matcher = pattern.matcher(test);
        return matcher.matches();
    }

}