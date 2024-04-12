import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;

public class isolationForest
{
    public static void main(String[] args)
    {
        //Anomaly threshold
        double anomalyThreshold = .735;
        int numEntries = 1000;

        //ArrayList to store the data
        ArrayList<Integer> dataSet = new ArrayList<>();
        ArrayList<Integer> key = new ArrayList<>();

        //User should provide the file poth in the terminal when they call this command
        if(args.length < 1)
        {
            System.out.println("No test number provided, try again");
            return;
        }

        //Getting filepath
        String testNum = args[0];

        String keyFilePath = "DataFiles\\Key_Test_" + testNum + ".txt";
        String dataFilePath = "DataFiles\\Test_" + testNum + ".txt";

        //Create file objects
        File keyIn = new File(keyFilePath);
        File dataIn = new File(dataFilePath);

        //Keeping track of number of anomalies
        int numAnomalies = 0;
        int numBenign=  0;

        try{
            //Creating a file reader object
            FileReader keyReader = new FileReader(keyIn);
            FileReader dataReader = new FileReader(dataIn);

            //Feed the filereader to a buffered reader for efficiency
            BufferedReader fastKeyReader = new BufferedReader(keyReader);
            BufferedReader fastDataReader = new BufferedReader(dataReader);

            String currentData;
            String currentKey;
            int dataInt;
            int keyInt;

            //Each line of the file is just one bit for a total of 125 bytes in each file, so we should
            //read them in in groups of 8 and convert to their respective integers
            for(int j = 0; j < numEntries; j++)
            {
                currentData = fastDataReader.readLine();
                currentKey = fastKeyReader.readLine();
                dataInt = Integer.parseInt(currentData);
                keyInt = Integer.parseInt(currentKey);

                if(keyInt == 1)
                {
                    numAnomalies++;
                }
                else
                {
                    numBenign++;
                }

                dataSet.add(dataInt);
                key.add(keyInt);
            }

            //Close reader
            fastDataReader.close();
            fastKeyReader.close();
        }
        catch (IOException error)
        {
            //Handle any exceptions that might occur
            error.printStackTrace();
        }

        //Now that we have our dataset translate from bits to their corresponding decimal values, we can begin
        //the isolation forest method
        //The average value of h(x) for a dataset of size 125
        double avgH = (2*(Math.log(numEntries - 1) + .577)) - ((2*(double)(numEntries - 1))/numEntries);

        //Random int generator object
        Random randomInt = new Random();
        int randomIndex;

        ArrayList<Integer> treeInput = new ArrayList<>();
        treeInput = new ArrayList<>(dataSet);

        ArrayList<BinarySearchTree> isolationForest = new ArrayList<>();

        //We'll do a forest of 500 trees and see if that breaks anything
        for(int i = 0; i < 200; i++)
        {
            isolationForest.add(new BinarySearchTree());
        }

        System.out.println("Adding values to trees...");
        //Adding all of the elements randomly to each binary search tree
        for(int i = 0; i < 200; i++)
        {
            while(!treeInput.isEmpty())
            {
                randomIndex = randomInt.nextInt(treeInput.size());
                isolationForest.get(i).insert(isolationForest.get(i).getRoot(), treeInput.get(randomIndex));
                treeInput.remove(randomIndex);
            }

            //Resetting tree input
            treeInput = new ArrayList<>(dataSet);
        }

        //Searching all the BSTs for each element to get the average path length
        double[] pathLengths = new double[numEntries];
        Arrays.fill(pathLengths, 0);

        System.out.println("Calculating path lengths...");
        for(int i = 0; i < numEntries; i++)
        {
            for(int j = 0; j < 200; j++)
            {
                pathLengths[i] += isolationForest.get(j).search(isolationForest.get(j).getRoot(), dataSet.get(i));
            }

            //Dividing to get the average
            pathLengths[i] /= 200;
            //System.out.println(pathLengths[i]);
        }

        //Now for each point we can calculate the anomaly score and determine if the value is anomalous
        double [] anomalyScores = new double[numEntries];

        int suspiciousValues = 0;
        int benignValues = 0;
        int correctDetections = 0;
        int incorrectDetections = 0;
        int correctPass = 0;
        int incorrectPass = 0;

        System.out.println("Calculating anomaly scores...");
        for(int i = 0; i < numEntries; i++)
        {
            anomalyScores[i] = Math.pow(2, -(pathLengths[i]/avgH));
            //System.out.println(anomalyScores[i]);
            if(anomalyScores[i] > anomalyThreshold)
            {
                System.out.println(dataSet.get(i) + " detected as abnormal");
                suspiciousValues++;
                if(key.get(i) == 1)
                {
                    correctDetections++;
                }
                else
                {
                    incorrectDetections++;
                }
            }
            else
            {
                benignValues++;

                if(key.get(i) == 0)
                {
                    correctPass++;
                }
                else
                {
                    incorrectPass++;
                }
            }
        }

        double truePos = (double)correctDetections/numAnomalies;
        double falsePos = (double)incorrectDetections/numBenign;
        double trueNeg = 1 - falsePos;
        double falseNeg = 1 - truePos;

        //Printing out totals
        System.out.println("--------------------------------------------------------");
        System.out.println("Benign values detected: " + benignValues);
        System.out.println("Anomalous values detected: " + suspiciousValues);
        System.out.println("True positive rate: " + truePos);
        System.out.println("True negative rate: " + trueNeg);
        System.out.println("False positive rate: " + falsePos);
        System.out.println("False negative rate: " + falseNeg);

    }
}
