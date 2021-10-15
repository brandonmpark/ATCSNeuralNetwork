import java.util.*;
import java.io.*;

/**
 * Reads or writes stored weight values in a text file.
 *
 * @author Brandon Park
 * @version 10/15/21
 */
public class WeightsHandler
{
   /**
    * Initializes the weights to random values within a certain range.
    *
    * @param inputNodes  the number of input nodes in the network.
    * @param hiddenNodes the number of hidden nodes in the network.
    * @param outputNodes the number of output nodes in the network.
    * @param min         the lower bound of the random generation (inclusive).
    * @param max         the upper bound of the random generation (exclusive).
    * @return returns the randomly initialized weights.
    */
   public static double[][][] randomizeWeights(int inputNodes, int hiddenNodes, int outputNodes, double min, double max)
   {
      double[][][] W = initWeightsArray(inputNodes, hiddenNodes, outputNodes);

      for (int n = 0; n < W.length; n++)
      {
         for (int a = 0; a < W[n].length; a++)
         {
            for (int b = 0; b < W[n][a].length; b++)
            {
               W[n][a][b] = RandomGenerator.random(min, max);
            }
         }
      }

      return W;
   }  // public static double[][][] randomizeWeights(int inputNodes, int hiddenNodes, int outputNodes, double min, double max)

   /**
    * Initializes the dimensions of the weights array.
    *
    * @param inputNodes  the number of input nodes in the network.
    * @param hiddenNodes the number of hidden nodes in the network.
    * @param outputNodes the number of output nodes in the network.
    * @return returns an empty weights array with the correct dimensions.
    */
   public static double[][][] initWeightsArray(int inputNodes, int hiddenNodes, int outputNodes)
   {
      double[][][] W = new double[2][][];
      W[0] = new double[inputNodes][hiddenNodes];
      W[1] = new double[hiddenNodes][outputNodes];

      return W;
   }

   /**
    * Reads in weights from the console inputted by the user.
    *
    * @param inputNodes  the number of input nodes in the network.
    * @param hiddenNodes the number of hidden nodes in the network.
    * @param outputNodes the number of output nodes in the network.
    * @return returns the inputted weights.
    */
   public static double[][][] inputWeights(int inputNodes, int hiddenNodes, int outputNodes)
   {
      System.out.println("Inputting weights manually has not been implemented yet -- ending process.");
      System.exit(1);
      return null;
   }

   /**
    * Reads in weights from a weights file.
    *
    * @param inputNodes      the number of input nodes in the network.
    * @param hiddenNodes     the number of hidden nodes in the network.
    * @param outputNodes     the number of output nodes in the network.
    * @param weightsFilePath the file path of the weights file.
    * @return returns the read weights.
    */
   public static double[][][] readWeights(int inputNodes, int hiddenNodes, int outputNodes, String weightsFilePath)
   {
      double[][][] W;

      try
      {
         FileReader fileReader = new FileReader(weightsFilePath);
         Scanner scanner = new Scanner(fileReader);

         W = initWeightsArray(inputNodes, hiddenNodes, outputNodes);

         int input = scanner.nextInt();
         int hidden = scanner.nextInt();
         int output = scanner.nextInt();

         if (input != inputNodes || hidden != hiddenNodes || output != outputNodes)
         {
            System.out.println("Weights file does not match network structure -- getting weights manually.");
            W = inputWeights(inputNodes, hiddenNodes, outputNodes);
         }

         else
         {
            while (scanner.hasNext())
            {
               int n = scanner.nextInt();
               int a = scanner.nextInt();
               int b = scanner.nextInt();
               W[n][a][b] = scanner.nextDouble();
            } // (scanner.hasNext())
         }

         scanner.close();
      }  // try
      catch (FileNotFoundException e)
      {
         System.out.println("Weights file not found -- getting weights manually.");
         W = inputWeights(inputNodes, hiddenNodes, outputNodes);
      }
      return W;
   }  // public static double[][][] readWeights(int inputNodes, int hiddenNodes, int outputNodes, String weightsFilePath)

   /**
    * Writes weights from an array to the file.
    *
    * @param W               the weights array.
    * @param weightsFilePath the file path of the weight file to be written to.
    */
   public static void writeWeights(double[][][] W, String weightsFilePath)
   {
      try
      {
         FileWriter writer = new FileWriter(weightsFilePath);

         String result = "";
         result += W[0].length + " " + W[1].length + " " + W[1][0].length + "\n\n";

         for (int n = 0; n < W.length; n++)
         {
            for (int a = 0; a < W[n].length; a++)
            {
               for (int b = 0; b < W[n][a].length; b++)
               {
                  result += n + " " + a + " " + b + " ";
                  result += W[n][a][b] + "\n";
               }
            }
         }

         writer.write(result);
         writer.close();
      }  // try
      catch (IOException e)
      {
         System.exit(1);
      }
   }  // public static void writeWeights(double[][][] W, String weightsFilePath)
}  // public class WeightsHandler
