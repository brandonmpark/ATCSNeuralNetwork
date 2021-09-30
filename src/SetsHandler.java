import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Handles reading input sets from files and the console.
 *
 * @author Brandon Park
 * @version 9/27/21
 */
public class SetsHandler
{
   /**
    * Reads testing sets from the console.
    *
    * @param inputNodes the number of input nodes in the network.
    * @return returns an array of testing sets.
    */
   public static double[][] inputTestingSets(int inputNodes)
   {
      System.out.println();
      System.out.println("Reading testing input sets.");

      String input = ConsoleHandler.input("Number of testing sets", "intPos");
      int numSets = Integer.parseInt(input);

      double[][] inputSets = new double[numSets][inputNodes];

      for (int t = 0; t < numSets; t++)
      {
         System.out.println();
         System.out.println("Testing Set #" + (t + 1) + ".");
         for (int i = 0; i < inputSets[t].length; i++)
         {
            inputSets[t][i] = Double.parseDouble(ConsoleHandler.input("Input " + i, "double"));
         }
      }

      return inputSets;
   }

   /**
    * Reads testing sets from a file.
    *
    * @param inputNodes the number of input nodes in the network.
    * @param filePath   the file path of the testing sets file.
    * @return returns an array of testing sets.
    */
   public static double[][] readTestingSets(int inputNodes, String filePath)
   {
      double[][] inputSets;

      try
      {
         FileReader fileReader = new FileReader(filePath);
         Scanner scanner = new Scanner(fileReader);

         int numSets = scanner.nextInt();
         inputSets = new double[numSets][inputNodes];

         for (int t = 0; t < inputSets.length; t++)
         {
            for (int i = 0; i < inputSets[t].length; i++)
            {
               inputSets[t][i] = scanner.nextDouble();
            }
         }

         scanner.close();
      }
      catch (FileNotFoundException e)
      {
         System.out.println("Testing file not found -- getting testing input sets manually.");
         inputSets = inputTestingSets(inputNodes);
      }

      return inputSets;
   }

   /**
    * Reads training sets from the console.
    *
    * @param inputNodes the number of input nodes in the network.
    * @return returns an array of training sets.
    */
   public static Object[] inputTrainingSets(int inputNodes)
   {
      System.out.println();
      System.out.println("Reading training input sets.");

      String input = ConsoleHandler.input("Number of training sets", "intPos");
      int numSets = Integer.parseInt(input);

      double[][] inputSets = new double[numSets][inputNodes];
      double[] outputSets = new double[numSets];
      Object[] trainingSets;

      for (int t = 0; t < numSets; t++)
      {
         System.out.println("Training Set #" + (t + 1) + ".");
         for (int i = 0; i < inputSets[t].length; i++)
         {
            inputSets[t][i] = Double.parseDouble(ConsoleHandler.input("Input " + i, "double"));
         }
         outputSets[t] = Integer.parseInt(ConsoleHandler.input("Output", "double"));
      }

      trainingSets = new Object[]{inputSets, outputSets};
      return trainingSets;
   }

   /**
    * Reads training sets from a file.
    *
    * @param inputNodes the number of input nodes in the network.
    * @param filePath   the file path of the training sets file.
    * @return returns an array of training sets.
    */
   public static Object[] readTrainingSets(int inputNodes, String filePath)
   {
      Object[] trainingSets;

      try
      {
         double[][] inputSets;
         double[] outputSets;

         FileReader fileReader = new FileReader(filePath);
         Scanner scanner = new Scanner(fileReader);

         int numSets = scanner.nextInt();
         inputSets = new double[numSets][inputNodes];
         outputSets = new double[numSets];

         for (int t = 0; t < inputSets.length; t++)
         {
            for (int i = 0; i < inputSets[t].length; i++)
            {
               inputSets[t][i] = scanner.nextDouble();
            }
            outputSets[t] = scanner.nextDouble();
         }

         trainingSets = new Object[]{inputSets, outputSets};
      }
      catch (FileNotFoundException e)
      {
         System.out.println("Training file not found -- getting training input sets manually.");
         trainingSets = inputTrainingSets(inputNodes);
      }

      return trainingSets;
   }
}
