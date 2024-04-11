import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class isolationForest
{
    public static void main(String[] args)
    {
        //User should provide the file poth in the terminal when they call this command
        if(args.length < 1)
        {
            System.out.println("No file path provided, try again");
            return;
        }

        //Getting filepath
        String filePath = args[0];

        //Create a file object
        File fileIn = new File(filePath);

        try{
            //Creating a file reader object
            FileReader reader = new FileReader(fileIn);

            //Feed the filereader to a buffered reader for efficiency
            BufferedReader fastReader = new BufferedReader(reader);

            String currentLine;
            int currentBit;
            int subtotal;
            ArrayList<Integer> dataSet = new ArrayList<>();

            //Each line of the file is just one bit for a total of 125 bytes in each file, so we should
            //read them in in groups of 8 and convert to their respective integers
            for(int j = 0; j < 125; j++)
            {
                subtotal = 0;
                for(int i = 7; i >= 0; i--)
                {
                    currentLine = fastReader.readLine();
                    currentBit = Integer.parseInt(currentLine);
                    subtotal += currentBit * 2^i;
                }

                dataSet.add(subtotal);
            }

            //Close reader
            fastReader.close();
        }
        catch (IOException error)
        {
            //Handle any exceptions that might occur
            error.printStackTrace();
        }

        //Now that we have our dataset translate from bits to their corresponding decimal values, we can begin
        //the isolation forest method
    }
}
