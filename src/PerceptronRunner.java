import java.util.Scanner;

/**
 * Configures and executes the perceptron process.
 *
 * @author Brandon Park
 * @version 9/15/21
 */
public class PerceptronRunner
{
   static String configFilePath;
   static Config config;

   static Perceptron perceptron = new Perceptron();

   // Running related
   static boolean useWeightsFile;
   static String weightsFilePath;
   static boolean useTestingFile;
   static String testingFilePath;

   // Training related
   static int maxIterations;
   static double lambda;
   static double errorThreshold;
   static double minRandom;
   static double maxRandom;
   static boolean useTrainingFile;
   static String trainingFilePath;
   static boolean saveWeights;
   static String savedWeightsFilePath;

   static double[][] inputSets;
   static double[] outputSets;

   /**
    * Automatically configures the network using options from a configuration file.
    */
   private static void autoConfigNetwork()
   {
      perceptron.inputNodes = Integer.parseInt(config.get("inputNodes", "intPos"));    // Number of input nodes
      perceptron.a = new double[perceptron.inputNodes];

      perceptron.hiddenNodes = Integer.parseInt(config.get("hiddenNodes", "intPos"));  // Number of hidden nodes
      perceptron.h = new double[perceptron.hiddenNodes];

      perceptron.Theta[0] = new double[perceptron.hiddenNodes];
      perceptron.Theta[1] = new double[1];
   }

   /**
    * Manually configures the network using options from the console inputted by the user.
    */
   private static void manualConfigNetwork()
   {
      System.out.println();
      System.out.println("Reading configuration options.");

      perceptron.inputNodes = Integer.parseInt(ConsoleHandler.input("Number of input nodes", "intPos"));    // Number of input nodes
      perceptron.a = new double[perceptron.inputNodes];

      perceptron.hiddenNodes = Integer.parseInt(ConsoleHandler.input("Number of hidden nodes", "intPos"));  // Number of hidden nodes
      perceptron.h = new double[perceptron.hiddenNodes];

      perceptron.Theta[0] = new double[perceptron.hiddenNodes];
      perceptron.Theta[1] = new double[1];
   }

   /**
    * Automatically configures the runtime options using options from a configuration file.
    *
    * @param operation the operation being executed on the network, must be "run" or "train".
    */
   private static void autoConfig(String operation)
   {
      if (operation.equals("run"))
      {
         useWeightsFile = Boolean.parseBoolean(config.get("useWeightsFile", "boolean"));         // Use weights file
         if (useWeightsFile) weightsFilePath = config.get("weightsFilePath", "filePath");        // Weights file path
         useTestingFile = Boolean.parseBoolean(config.get("useTestingFile", "boolean"));         // Use testing file
         if (useTestingFile) testingFilePath = config.get("testingFilePath", "filePath");        // Testing file path
      }

      else if (operation.equals("train"))
      {
         lambda = Double.parseDouble(config.get("lambda", "doublePos"));                         // Lambda
         maxIterations = Integer.parseInt(config.get("maxIterations", "intPos"));                // Max iterations
         errorThreshold = Double.parseDouble(config.get("errorThreshold", "doublePos"));         // Error threshold
         minRandom = Double.parseDouble(config.get("minRandom", "double"));                      // Min random
         maxRandom = Double.parseDouble(config.get("maxRandom", "double"));                      // Max random

         useTrainingFile = Boolean.parseBoolean(config.get("useTrainingFile", "boolean"));       // Use training file
         if (useTrainingFile) trainingFilePath = config.get("trainingFilePath", "filePath");     // Training file path

         saveWeights = Boolean.parseBoolean(config.get("saveWeights", "boolean"));               // Save weights
         if (saveWeights) savedWeightsFilePath = config.get("savedWeightsFilePath", "filePath"); // Saved weights file path
      }
   }

   /**
    * Manually configures the runtime options using options from the console inputted by the user.
    *
    * @param operation the operation being executed on the network, must be "run" or "train".
    */
   private static void manualConfig(String operation)
   {
      if (operation.equals("run"))
      {
         useWeightsFile = Boolean.parseBoolean(ConsoleHandler.input("useWeightsFile", "boolean"));          // Use weights file
         if (useWeightsFile) weightsFilePath = ConsoleHandler.input("weightsFilePath", "filePath");         // Weights file path
         useTestingFile = Boolean.parseBoolean(ConsoleHandler.input("useTestingFile", "boolean"));          // Use testing file
         if (useTestingFile) testingFilePath = ConsoleHandler.input("testingFilePath", "filePath");         // Testing file path
      }

      else if (operation.equals("train"))
      {
         lambda = Double.parseDouble(ConsoleHandler.input("lambda", "doublePos"));                          // Lambda
         maxIterations = Integer.parseInt(ConsoleHandler.input("maxIterations", "intPos"));                 // Max iterations
         errorThreshold = Double.parseDouble(ConsoleHandler.input("errorThreshold", "doublePos"));          // Error threshold
         minRandom = Double.parseDouble(ConsoleHandler.input("minRandom", "double"));                       // Min random
         maxRandom = Double.parseDouble(ConsoleHandler.input("maxRandom", "double"));                       // Max random

         useTrainingFile = Boolean.parseBoolean(ConsoleHandler.input("useTrainingFile", "boolean"));        // Use training file
         if (useTrainingFile) trainingFilePath = ConsoleHandler.input("trainingFilePath", "filePath");      // Training file path

         saveWeights = Boolean.parseBoolean(ConsoleHandler.input("saveWeights", "boolean"));                // Save weights
         if (saveWeights)
            savedWeightsFilePath = ConsoleHandler.input("savedWeightsFilePath", "filePath");  // Saved weights file path
      }
   }

   /**
    * Runs the network on a testing set.
    */
   private static void runNetwork()
   {
      if (config != null) autoConfig("run");
      else manualConfig("run");

      if (useWeightsFile) perceptron.W = WeightsHandler.readWeights(perceptron.inputNodes, perceptron.hiddenNodes, weightsFilePath);
      else perceptron.W = WeightsHandler.inputWeights(perceptron.inputNodes, perceptron.hiddenNodes);

      if (useTestingFile) inputSets = SetsHandler.readTestingSets(perceptron.inputNodes, testingFilePath);
      else inputSets = SetsHandler.inputTestingSets(perceptron.inputNodes);

      System.out.println();
      System.out.println("Outputting results from testing.");

      for (int t = 0; t < inputSets.length; t++)
      {
         perceptron.a = inputSets[t];
         perceptron.run();

         System.out.println();
         System.out.println("Testing Set #" + (t + 1) + ".");

         System.out.print("Inputs:");
         for (int i = 0; i < perceptron.inputNodes; i++)
         {
            System.out.print(" " + perceptron.a[i]);
         }

         System.out.println();
         System.out.println("F: " + perceptron.F);
      }
   }

   /**
    * Trains the network on a training set.
    */
   private static void trainNetwork()
   {
      if (config != null) autoConfig("train");
      else manualConfig("train");

      System.out.println(" - Random weight range: " + minRandom + " to " + maxRandom);
      System.out.println(" - Max iterations: " + maxIterations);
      System.out.println(" - Lambda: " + lambda);

      if (useWeightsFile) perceptron.W = WeightsHandler.readWeights(perceptron.inputNodes, perceptron.hiddenNodes, weightsFilePath);
      else perceptron.W = WeightsHandler.randomizeWeights(perceptron.inputNodes, perceptron.hiddenNodes, minRandom, maxRandom);

      Object[] trainingSets;
      if (useTrainingFile) trainingSets = SetsHandler.readTrainingSets(perceptron.inputNodes, trainingFilePath);
      else trainingSets = SetsHandler.inputTrainingSets(perceptron.inputNodes);

      inputSets = (double[][]) trainingSets[0];
      outputSets = (double[]) trainingSets[1];

      perceptron.train(maxIterations, lambda, errorThreshold, inputSets, outputSets);

      if (saveWeights) WeightsHandler.writeWeights(perceptron.W, savedWeightsFilePath);
   }

   /**
    * Executes the perceptron interface.
    *
    * @param args options from the command line which may have a path to a configuration file.
    */
   public static void main(String[] args)
   {
      Scanner scanner = new Scanner(System.in);

      if (args.length > 0)
      {
         configFilePath = args[0];
         config = new Config(configFilePath);
         autoConfigNetwork();
      }
      else manualConfigNetwork();

      System.out.println();
      System.out.println("Network configuration");
      System.out.println(" - Number of input nodes: " + perceptron.inputNodes);
      System.out.println(" - Number of hidden nodes: " + perceptron.hiddenNodes);

      System.out.println();
      System.out.print("Run or train network? ");
      String option = scanner.nextLine();
      if (option.equals("run")) runNetwork();
      else if (option.equals("train")) trainNetwork();
   }
}