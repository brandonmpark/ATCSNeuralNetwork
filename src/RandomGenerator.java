/**
 * Generates random numbers.
 *
 * @author Brandon Park
 * @version 9/7/21
 */
public class RandomGenerator
{
   /**
    * Generates a random floating point number between min (inclusive) and max (exclusive).
    *
    * @param min the lower bound of the random generation (inclusive).
    * @param max the upper bound of the random generation (exclusive).
    * @return returns a random double between min (inclusive) and max (exclusive).
    */
   public static double random(double min, double max)
   {
      double difference = max - min;
      return difference * Math.random() + min;
   }
}
