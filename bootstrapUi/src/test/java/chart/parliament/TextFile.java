package chart.parliament;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TextFile{
    private String fileName;
    private String path = "/";

    //Constructor
    public TextFile(String fileName){
        this.fileName = "F:\\" + fileName;
    }

    public int getLinesNumber(){
        int lines = 0;
        try{
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while (reader.readLine() != null){
                lines++;
            }
            reader.close();
        } catch(Exception ex) {
            ex.printStackTrace();
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        return lines;
    }
    public String[] parseCSVLine(int nLine){ //We input the number of the line we want to read, it returns every CSV as an element of an array.
        String separator = ",";
        String[] parsedCSVLine = getLine(nLine).split(separator);
        return parsedCSVLine;
    }

    public ArrayList<String> getAllLines(){
        ArrayList<String> lines = new ArrayList<String>();
        String line = null;

        try{
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while ((line = reader.readLine()) != null){
                lines.add(line);
            }
            reader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
        }
        return lines;
    }
    public String getLine(int nLine){
        // This will reference one line at a time
        String line = null;
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        int counter = 0;
        try {
            bufferedReader = new BufferedReader(new FileReader(fileName));

            while((line = bufferedReader.readLine()) != null) {
                counter++;
                if(counter == nLine) break;
            }

        }

        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }

        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }

        finally
        {
            if(fileReader != null){
                // Always close files.
                try{
                    bufferedReader.close();
                }
                catch(IOException e){
                    System.out.println("IO exception at finally block");
                    e.printStackTrace();
                }
            }
        }

        return line;
    }
}
