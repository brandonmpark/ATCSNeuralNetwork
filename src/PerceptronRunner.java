import java.util.Scanner;

/**
 * Configures and executes the perceptron process.
 *
 * @author Brandon Park
 * @version 11/9/21
 */
public class PerceptronRunner
{
   static String configPath;
   static Config config;

   static Perceptron perceptron;

   static double[][] inputSets;
   static double[][] outputSets;

   static String weightsPath;

   // Running related
   static boolean useTestingWeights;
   static boolean useTestingSets;
   static String testingSetsPath;

   // Training related
   static boolean useTrainingWeights;
   static boolean useTrainingSets;
   static String trainingSetsPath;
   static double lambda;
   static int maxIterations;
   static double errorThreshold;
   static double minRandom;
   static double maxRandom;
   static boolean saveWeights;
   static String savedWeightsPath;
   static int autosaveInterval;

   /**
    * Automatically configures the network using options from a configuration file.
    */
   private static void autoConfigNetwork()
   {
      int inputNodes = Integer.parseInt(config.get("inputNodes", "intPos"));    // Number of input nodes
      int hiddenNodes = Integer.parseInt(config.get("hiddenNodes", "intPos"));  // Number of hidden nodes
      int outputNodes = Integer.parseInt(config.get("outputNodes", "intPos"));  // Number of output nodes

      perceptron = new Perceptron(inputNodes, hiddenNodes, outputNodes);
   }

   /**
    * Manually configures the network using options from the console inputted by the user.
    */
   private static void manualConfigNetwork()
   {
      System.out.println();
      System.out.println("Reading configuration options.");

      int inputNodes = Integer.parseInt(ConsoleHandler.input("Number of input nodes", "intPos"));    // Number of input nodes
      int hiddenNodes = Integer.parseInt(ConsoleHandler.input("Number of hidden nodes", "intPos"));  // Number of hidden nodes
      int outputNodes = Integer.parseInt(ConsoleHandler.input("Number of output nodes", "intPos"));  // Number of output nodes

      perceptron = new Perceptron(inputNodes, hiddenNodes, outputNodes);
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
         useTestingWeights = Boolean.parseBoolean(config.get("useTestingWeights", "boolean"));     // Use testing weights
         if (useTestingWeights) weightsPath = config.get("weightsPath", "filePath");               // Weights path
         useTestingSets = Boolean.parseBoolean(config.get("useTestingSets", "boolean"));           // Use testing sets
         if (useTestingSets) testingSetsPath = config.get("testingSetsPath", "filePath");          // Testing sets path
      }

      else if (operation.equals("train"))
      {
         useTrainingWeights = Boolean.parseBoolean(config.get("useTrainingWeights", "boolean"));   // Use training weights
         if (useTrainingWeights) weightsPath = config.get("weightsPath", "filePath");              // Weights path
         useTrainingSets = Boolean.parseBoolean(config.get("useTrainingSets", "boolean"));         // Use training sets
         if (useTrainingSets) trainingSetsPath = config.get("trainingSetsPath", "filePath");       // Training sets path

         lambda = Double.parseDouble(config.get("lambda", "doublePos"));                           // Lambda
         maxIterations = Integer.parseInt(config.get("maxIterations", "intPos"));                  // Max iterations
         errorThreshold = Double.parseDouble(config.get("errorThreshold", "doublePos"));           // Error threshold
         minRandom = Double.parseDouble(config.get("minRandom", "double"));                        // Min random
         maxRandom = Double.parseDouble(config.get("maxRandom", "double"));                        // Max random

         saveWeights = Boolean.parseBoolean(config.get("saveWeights", "boolean"));                 // Save weights
         if (saveWeights)
         {
            savedWeightsPath = config.get("savedWeightsPath", "filePath");                         // Saved weights path
            autosaveInterval = Integer.parseInt(config.get("autosaveInterval", "int"));            // Autosave interval
         }
      }  // else if (operation.equals("train"))
   }     // private static void autoConfig(String operation)

   /**
    * Manually configures the runtime options using options from the console inputted by the user.
    *
    * @param operation the operation being executed on the network, must be "run" or "train".
    */
   private static void manualConfig(String operation)
   {
      if (operation.equals("run"))
      {
         useTestingWeights = Boolean.parseBoolean(ConsoleHandler.input("useTestingWeights", "boolean"));     // Use testing weights
         if (useTestingWeights) weightsPath = ConsoleHandler.input("weightsPath", "filePath");               // Weights path
         useTestingSets = Boolean.parseBoolean(ConsoleHandler.input("useTestingSets", "boolean"));           // Use testing sets
         if (useTestingSets) testingSetsPath = ConsoleHandler.input("testingSetsPath", "filePath");          // Testing sets path
      }

      else if (operation.equals("train"))
      {
         useTrainingWeights = Boolean.parseBoolean(ConsoleHandler.input("useTrainingWeights", "boolean"));   // Use training weights
         if (useTrainingWeights) weightsPath = ConsoleHandler.input("weightsPath", "filePath");              // Weights path
         useTrainingSets = Boolean.parseBoolean(ConsoleHandler.input("useTrainingSets", "boolean"));         // Use training sets
         if (useTrainingSets) trainingSetsPath = ConsoleHandler.input("trainingSetsPath", "filePath");       // Training sets path

         lambda = Double.parseDouble(ConsoleHandler.input("lambda", "doublePos"));                           // Lambda
         maxIterations = Integer.parseInt(ConsoleHandler.input("maxIterations", "intPos"));                  // Max iterations
         errorThreshold = Double.parseDouble(ConsoleHandler.input("errorThreshold", "doublePos"));           // Error threshold
         minRandom = Double.parseDouble(ConsoleHandler.input("minRandom", "double"));                        // Min random
         maxRandom = Double.parseDouble(ConsoleHandler.input("maxRandom", "double"));                        // Max random

         saveWeights = Boolean.parseBoolean(ConsoleHandler.input("saveWeights", "boolean"));                 // Save weights
         if (saveWeights)
         {
            savedWeightsPath = ConsoleHandler.input("savedWeightsPath", "filePath");                         // Saved weights path
            autosaveInterval = Integer.parseInt(ConsoleHandler.input("autosaveInterval", "int"));            // Autosave interval
         }
      }  // else if (operation.equals("train"))
   }     // private static void manualConfig(String operation)

   /**
    * Runs the network on a testing set.
    */
   private static void runNetwork()
   {
      if (config != null) autoConfig("run");
      else manualConfig("run");

      if (useTestingWeights)
         perceptron.W = WeightsHandler.readWeights(perceptron.inputNodes, perceptron.hiddenNodes, perceptron.outputNodes, weightsPath);
      else perceptron.W = WeightsHandler.inputWeights(perceptron.inputNodes, perceptron.hiddenNodes, perceptron.outputNodes);

      if (useTestingSets) inputSets = SetsHandler.readTestingSets(perceptron.inputNodes, testingSetsPath);
      else inputSets = SetsHandler.inputTestingSets(perceptron.inputNodes);

      for (int t = 0; t < inputSets.length; t++)
      {
         perceptron.runWithOutput(inputSets[t]);
      }
   }     // private static void runNetwork()

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

      if (useTrainingWeights)
         perceptron.W = WeightsHandler.readWeights(perceptron.inputNodes, perceptron.hiddenNodes, perceptron.outputNodes, weightsPath);
      else
         perceptron.W = WeightsHandler.randomizeWeights(perceptron.inputNodes, perceptron.hiddenNodes, perceptron.outputNodes, minRandom, maxRandom);

      Object[] trainingSets;
      if (useTrainingSets)
         trainingSets = SetsHandler.readTrainingSets(perceptron.inputNodes, perceptron.outputNodes, trainingSetsPath);
      else trainingSets = SetsHandler.inputTrainingSets(perceptron.inputNodes, perceptron.outputNodes);

      inputSets = (double[][]) trainingSets[0];
      outputSets = (double[][]) trainingSets[1];

      perceptron.train(maxIterations, lambda, errorThreshold, inputSets, outputSets, savedWeightsPath, autosaveInterval);

      if (saveWeights) WeightsHandler.writeWeights(perceptron.W, savedWeightsPath);
   }  // private static void trainNetwork()

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
         configPath = args[0];
         config = new Config(configPath);
         autoConfigNetwork();
      }
      else manualConfigNetwork();

      System.out.println();
      System.out.println("Network configuration");
      System.out.println(" - Number of input nodes: " + perceptron.inputNodes);
      System.out.println(" - Number of hidden nodes: " + perceptron.hiddenNodes);
      System.out.println(" - Number of output nodes: " + perceptron.outputNodes);

      System.out.println();
      System.out.print("Run or train network? ");
      String option = scanner.nextLine();
      if (option.equals("run")) runNetwork();
      else if (option.equals("train")) trainNetwork();
   }  // public static void main(String[] args)
}     // public class PerceptronRunner
