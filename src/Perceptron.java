/**
 * Models a one hidden layer perceptron algorithm with a single output and any number of input and hidden nodes.
 *
 * @author Brandon Park
 * @version 9/15/21
 */
public class Perceptron
{
   public double[][][] W;
   public double[][] Theta = new double[2][];

   public double[] a;
   public int inputNodes;

   public double[] h;
   public int hiddenNodes;

   public double F;

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
    * Calculates fPrime of an input, which for sigmoid is F(input)(1-F(input)).
    *
    * @param input the input value;
    * @return returns fPrime of input as a double.
    */
   private double fPrime(double input)
   {
      double F = sigmoid(input);
      return F * (1.0 - F);
   }

   /**
    * Runs the perceptron, propagating each activation result forward.
    */
   public void run()
   {
      for (int j = 0; j < hiddenNodes; j++)  // Calculates hidden activations
      {
         Theta[0][j] = 0;
         for (int k = 0; k < inputNodes; k++)
         {
            Theta[0][j] += W[0][k][j] * a[k];
         }
         h[j] = sigmoid(Theta[0][j]);
      }

      Theta[1][0] = 0;
      for (int j = 0; j < hiddenNodes; j++)  // Calculates output activation
      {
         Theta[1][0] += W[1][j][0] * h[j];
      }

      F = sigmoid(Theta[1][0]);
   }

   /**
    * Runs the perceptron on a set of inputs and compares the outputs with an output set.
    *
    * @param trainInput  the input values of the training set.
    * @param trainOutput the output values of the training set.
    */
   public void runWithTrue(double[][] trainInput, double[] trainOutput)
   {
      double omega;
      for (int t = 0; t < trainOutput.length; t++)
      {
         a = trainInput[t];
         run();

         System.out.println();
         System.out.print("Inputs:");
         for (int i = 0; i < inputNodes; i++)
         {
            System.out.print(" " + a[i]);
         }

         omega = trainOutput[t] - F;
         System.out.print(", T: " + trainOutput[t] + ", F: " + F + ", Error: " + 0.5 * omega * omega);
      }
   }

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
   public void train(int maxIterations, double lambda, double errorThreshold, double[][] trainInput, double[] trainOutput)
   {
      boolean done = false;

      double totalError, omega, psi;
      double[] Omega = new double[hiddenNodes];
      double[] Psi = new double[hiddenNodes];
      double[][][] deltaW = WeightsHandler.initWeightsArray(inputNodes, hiddenNodes);

      int iteration = 0;

      while (!done)
      {
         totalError = 0;

         for (int t = 0; t < trainInput.length; t++)
         {
            a = trainInput[t];
            run();

            omega = trainOutput[t] - F;
            totalError += 0.5 * omega * omega;
            psi = omega * fPrime(Theta[1][0]);

            for (int j = 0; j < hiddenNodes; j++)  // Calculates weight changes for hidden layer
            {
               deltaW[1][j][0] = lambda * psi * h[j];
            }

            for (int k = 0; k < inputNodes; k++)   // Calculates weight changes for input layer
            {
               for (int j = 0; j < hiddenNodes; j++)
               {
                  Omega[j] = psi * W[1][j][0];
                  Psi[j] = Omega[j] * fPrime(Theta[0][j]);
                  deltaW[0][k][j] = lambda * Psi[j] * a[k];
               }
            }

            for (int j = 0; j < hiddenNodes; j++)  // Applies weight changes for hidden layer
            {
               W[1][j][0] += deltaW[1][j][0];
            }

            for (int i = 0; i < inputNodes; i++)   // Applies weight changes for input layer
            {
               for (int j = 0; j < hiddenNodes; j++)
               {
                  W[0][i][j] += deltaW[0][i][j];
               }
            }
         }  // (int t = 0; t < trainInput.length; t++;

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
      }     // (!done)

      runWithTrue(trainInput, trainOutput);
   }
}
