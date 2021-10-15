import java.util.Scanner;

/**
 * Reads and validates user inputs through the console.
 *
 * @author Brandon Park
 * @version 10/15/21
 */
public class ConsoleHandler
{
   static Scanner scanner = new Scanner(System.in);

   /**
    * Reads in an input with a specified label and type, rejecting values without the correct type.
    *
    * @param label the name of the inputted field.
    * @param type  the type of the expected value.
    * @return returns the successful console input as a String.
    */
   public static String input(String label, String type)
   {
      boolean failedInput = true;
      String returned = "";

      while (failedInput)
      {
         System.out.print(" - " + label + ": ");
         String input = scanner.nextLine();

         if (type.equals("filePath"))
         {
            if (Validator.isValidFilePath(input)) failedInput = false;
            else System.out.println("   - " + label + " must be a valid file path (.json or .txt).");
         }
         else if (type.equals("int"))
         {
            if (Validator.isValidInt(input)) failedInput = false;
            else System.out.println("   - " + label + " must be a valid integer.");
         }
         else if (type.equals("intPos"))
         {
            if (Validator.isValidInt(input, 0)) failedInput = false;
            else System.out.println("   - " + label + " must be a valid non-negative integer.");
         }
         else if (type.equals("double"))
         {
            if (Validator.isValidDouble(input)) failedInput = false;
            else System.out.println("   - " + label + " must be a valid double.");
         }
         else if (type.equals("doublePos"))
         {
            if (Validator.isValidDouble(input, 0)) failedInput = false;
            else System.out.println("   - " + label + " must be a valid non-negative double.");
         }
         else if (type.equals("boolean"))
         {
            if (Validator.isValidBoolean(input)) failedInput = false;
            else System.out.println("   - " + label + " must be a valid boolean.");
         }

         if (!failedInput) returned = input;
      }  // while (failedInput)

      return returned;
   }     // public static String input(String label, String type)
}        // public class ConsoleHandler
