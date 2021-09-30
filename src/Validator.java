import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validates Strings to ensure they are of a certain type and meet certain restrictions.
 *
 * @author Brandon Park
 * @version 9/27/21
 */
public class Validator
{
   /**
    * Determines whether a String is a valid integer.
    *
    * @param s a String to be validated.
    * @return returns whether s can be successfully parsed as an integer.
    */
   public static boolean isValidInt(String s)
   {
      boolean isValid;

      try
      {
         Integer.parseInt(s);
         isValid = true;
      }
      catch (Exception e)
      {
         isValid = false;
      }

      return isValid;
   }

   /**
    * Determines whether a String is a valid integer greater than a given minimum (inclusive).
    *
    * @param s   a String to be validated.
    * @param min an integer denoting the minimum valid value (inclusive).
    * @return returns whether s can be successfully parsed as an integer greater than or equal to min.
    */
   public static boolean isValidInt(String s, int min)
   {
      boolean isValid;

      try
      {
         int n = Integer.parseInt(s);
         isValid = n >= min;
      }
      catch (Exception e)
      {
         isValid = false;
      }

      return isValid;
   }

   /**
    * Determines whether a String is a valid integer greater than a given minimum (inclusive) and lesser than a given maximum (exclusive).
    *
    * @param s   a String to be validated.
    * @param min an integer denoting the minimum valid value (inclusive).
    * @param max an integer denoting the maximum valid value (exclusive).
    * @return returns whether s can be successfully parsed as an integer greater than or equal to min and less than max.
    */
   public static boolean isValidInt(String s, int min, int max)
   {
      boolean isValid;

      try
      {
         int n = Integer.parseInt(s);
         isValid = n >= min && n < max;
      }
      catch (Exception e)
      {
         isValid = false;
      }

      return isValid;
   }

   /**
    * Determines whether a String is a valid double.
    *
    * @param s a String to be validated.
    * @return returns whether s can be successfully parsed as a double.
    */
   public static boolean isValidDouble(String s)
   {
      boolean isValid;

      try
      {
         Double.parseDouble(s);
         isValid = true;
      }
      catch (Exception e)
      {
         isValid = false;
      }

      return isValid;
   }

   /**
    * Determines whether a String is a valid double greater than a given minimum (inclusive).
    *
    * @param s   a String to be validated.
    * @param min a double denoting the minimum valid value (inclusive).
    * @return returns whether s can be successfully parsed as a double greater than or equal to min.
    */
   public static boolean isValidDouble(String s, double min)
   {
      boolean isValid;

      try
      {
         double n = Double.parseDouble(s);
         isValid = n >= min;
      }
      catch (Exception e)
      {
         isValid = false;
      }

      return isValid;
   }

   /**
    * Determines whether a String is a valid double greater than a given minimum (inclusive) and lesser than a given maximum (exclusive).
    *
    * @param s   a String to be validated.
    * @param min a double denoting the minimum valid value (inclusive).
    * @param max a double denoting the maximum valid value (exclusive).
    * @return returns whether s can be successfully parsed as a double greater than or equal to min and less than max.
    */
   public static boolean isValidDouble(String s, double min, double max)
   {
      boolean isValid;

      try
      {
         double n = Double.parseDouble(s);
         isValid = n >= min && n < max;
      }
      catch (Exception e)
      {
         isValid = false;
      }

      return isValid;
   }

   /**
    * Determines whether a String is a valid boolean.
    *
    * @param s a String to be validated.
    * @return returns whether s can be successfully parsed as a boolean.
    */
   public static boolean isValidBoolean(String s)
   {
      boolean isValid;

      try
      {
         Boolean.parseBoolean(s);
         isValid = true;
      }
      catch (Exception e)
      {
         isValid = false;
      }

      return isValid;
   }

   /**
    * Determines whether a String is a valid file path with a .json or .txt extension.
    *
    * @param s a String to be validated.
    * @return returns whether s can be successfully parsed as a JSON or text file path.
    */
   public static boolean isValidFilePath(String s)
   {
      Pattern pattern = Pattern.compile(".(json|txt)$");
      Matcher matcher = pattern.matcher(s);
      return matcher.find();
   }
}
