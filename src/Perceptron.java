/**
 * Models a one hidden layer perceptron algorithm with any number of input, hidden, and output nodes.
 *
 * @author Brandon Park
 * @version 10/15/21
 */
public class Perceptron
{
   public double startTime, endTime;

   public double[][][] W;
   public double[][] Theta = new double[3][];

   public int inputNodes;
   public double[] a;

   public int hiddenNodes;
   public double[] h;

   public int outputNodes;
   public double[] F;

   public double totalError;
   public double[][][] deltaW;

   public double[] omega;
   public double[] Omega;

   public double[] psi;
   public double[] Psi;

   /**
    * Constructs a new Perceptron object and initializes the arrays.
    *
    * @param inputNodes  the number of input nodes in the network.
    * @param hiddenNodes the number of hidden nodes in the network.
    * @param outputNodes the number of output nodes in the network.
    */
   public Perceptron(int inputNodes, int hiddenNodes, int outputNodes)
   {
      this.inputNodes = inputNodes;
      a = new double[inputNodes];

      this.hiddenNodes = hiddenNodes;
      h = new double[hiddenNodes];

      this.outputNodes = outputNodes;
      F = new double[outputNodes];

      Theta[1] = new double[hiddenNodes];
      Theta[2] = new double[outputNodes];

      deltaW = WeightsHandler.initWeightsArray(inputNodes, hiddenNodes, outputNodes);

      omega = new double[outputNodes];
      Omega = new double[hiddenNodes];

      psi = new double[outputNodes];
      Psi = new double[hiddenNodes];
   }  // public Perceptron(int inputNodes, int hiddenNodes, int outputNodes)

   /**
    * Calculates the sigmoid of an input: 1/(1+e^-input)
    *
    * @param input the input value.
    * @return returns the sigmoid of input as a double.
    */
   private double sigmoid(double input)
   {
      return 1.0 / (1.0 + Math.exp(-input));
   }

   /**
    * Calculates f' of an input, which for sigmoid is F(input)(1-F(input)).
    *
    * @param input the input value;
    * @return returns f' of input as a double.
    */
   private double fPrime(double input)
   {
      double f = sigmoid(input);
      return f * (1.0 - f);
   }

   /**
    * Runs the perceptron, propagating each activation result forward.
    */
   public void run()
   {
      for (int j = 0; j < hiddenNodes; j++)  // Calculates hidden activations
      {
         Theta[1][j] = 0.0;
         for (int k = 0; k < inputNodes; k++)
         {
            Theta[1][j] += W[0][k][j] * a[k];
         }
         h[j] = sigmoid(Theta[1][j]);
      }

      for (int i = 0; i < outputNodes; i++)  // Calculates output activations
      {
         Theta[2][i] = 0.0;
         for (int j = 0; j < hiddenNodes; j++)
         {
            Theta[2][i] += W[1][j][i] * h[j];
         }
         F[i] = sigmoid(Theta[2][i]);
      }
   }  // public void run()

   /**
    * Runs the perceptron on a set of inputs and compares the outputs with an output set.
    *
    * @param trainInput  the input values of the training set.
    * @param trainOutput the output values of the training set.
    */
   public void runWithTrue(double[][] trainInput, double[][] trainOutput)
   {
      for (int t = 0; t < trainOutput.length; t++)
      {
         a = trainInput[t];
         run();

         System.out.println();
         System.out.print("Inputs:");
         for (int k = 0; k < inputNodes; k++)
         {
            System.out.print(" " + a[k]);
         }
         System.out.print(", ");

         System.out.print("T:");
         for (int i = 0; i < outputNodes; i++)
         {
            System.out.print(" " + trainOutput[t][i]);
         }
         System.out.print(", ");

         System.out.print("F:");
         for (int i = 0; i < outputNodes; i++)
         {
            System.out.print(" " + F[i]);
         }
      }  // for (int t = 0; t < trainOutput.length; t++)
   }     // public void runWithTrue(double[][] trainInput, double[][] trainOutput)

   /**
    * Trains the perceptron, using the gradient descent algorithm to update the weights until one of the following conditions is met:
    * 1. The max number of iterations is reached.
    * 2. The total error of the training sets is below the threshold.
    *
    * @param maxIterations  the max number of training cycles.
    * @param lambda         the learning rate applied to each weight change.
    * @param errorThreshold the error threshold to be reached.
    * @param trainInput     the input values of the training set.
    * @param trainOutput    the output values of the training set.
    */
   public void train(int maxIterations, double lambda, double errorThreshold, double[][] trainInput, double[][] trainOutput)
   {
      startTime = System.currentTimeMillis();
      boolean done = false;
      int iteration = 0;

      while (!done)
      {
         totalError = 0.0;

         for (int t = 0; t < trainInput.length; t++)
         {
            a = trainInput[t];
            run();

            for (int i = 0; i < outputNodes; i++)  // Calculates weight changes for hidden layer
            {
               omega[i] = trainOutput[t][i] - F[i];
               totalError += 0.5 * omega[i] * omega[i];
               psi[i] = omega[i] * fPrime(Theta[2][i]);

               for (int j = 0; j < hiddenNodes; j++)
               {
                  deltaW[1][j][i] = lambda * psi[i] * h[j];
               }
            }

            for (int j = 0; j < hiddenNodes; j++)  // Calculates weight changes for input layer
            {
               Omega[j] = 0.0;
               for (int i = 0; i < outputNodes; i++)
               {
                  Omega[j] += psi[i] * W[1][j][i];
               }
               Psi[j] = Omega[j] * fPrime(Theta[1][j]);

               for (int k = 0; k < inputNodes; k++)
               {
                  deltaW[0][k][j] = lambda * Psi[j] * a[k];
               }
            }  // for (int j = 0; j < hiddenNodes; j++)

            for (int j = 0; j < hiddenNodes; j++)  // Applies weight changes for hidden layer
            {
               for (int i = 0; i < outputNodes; i++)
               {
                  W[1][j][i] += deltaW[1][j][i];
               }
            }

            for (int k = 0; k < inputNodes; k++)   // Applies weight changes for input layer
            {
               for (int j = 0; j < hiddenNodes; j++)
               {
                  W[0][k][j] += deltaW[0][k][j];
               }
            }
         }  // for (int t = 0; t < trainInput.length; t++)

         iteration++;

         if (iteration > maxIterations)
         {
            System.out.println();
            System.out.println("Max number of iterations reached (" + maxIterations + ").");
            done = true;
         }
         else if (totalError < errorThreshold)
         {
            System.out.println();
            System.out.println(iteration + " total iterations.");
            System.out.println("Error threshold met: " + totalError + " total error compared to threshold " + errorThreshold + ".");
            done = true;
         }
      }     // while (!done)

      endTime = System.currentTimeMillis();
      System.out.println((endTime - startTime) + "ms elapsed.");
      runWithTrue(trainInput, trainOutput);
   }  // public void train(int maxIterations, double lambda, double errorThreshold, double[][] trainInput, double[][] trainOutput)
}     // public class Perceptron
