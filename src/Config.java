import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Reads and parses configuration values from a configuration file (JSON format).
 *
 * @author Brandon Park
 * @version 10/15/21
 */
public class Config
{
   final String DEFAULT_CONFIG_PATH = "src/config/defaultConfig.json";
   private JSONObject defaultConfig;
   private JSONObject config;

   /**
    * Constructs a new Config object, parsing and storing both defaultConfig and config as JSONObjects.
    * Quits if default config file is missing or malformed, uses default config if config file is missing or malformed.
    *
    * @param filePath the file path of the config file.
    */
   public Config(String filePath)
   {
      JSONParser parser = new JSONParser();

      try
      {
         FileReader fileReader = new FileReader(DEFAULT_CONFIG_PATH);
         defaultConfig = (JSONObject) parser.parse(fileReader);
      }
      catch (FileNotFoundException e)
      {
         System.out.println("Default configuration file not found -- ending process.");
         System.exit(1);
      }
      catch (ParseException | IOException e)
      {
         System.out.println("Malformed default configuration file -- ending process.");
         System.exit(1);
      }

      try
      {
         FileReader fileReader = new FileReader(filePath);
         System.out.println("Configuration file found -- loading configuration options.");
         config = (JSONObject) parser.parse(fileReader);
         System.out.println("Configuration options successfully loaded.");
      }
      catch (FileNotFoundException e)
      {
         System.out.println("Configuration file not found -- using default file.");
         config = defaultConfig;
      }
      catch (ParseException | IOException e)
      {
         System.out.println("Malformed configuration file -- using default file.");
         config = defaultConfig;
      }
   }  // public Config(String filePath)

   /**
    * Retrieves the config value corresponding to a given key.
    *
    * @param key the name of the configuration value to be obtained.
    * @return returns the value in config corresponding to key as a String, or the default one if missing/malformed.
    */
   public String get(String key, String type)
   {
      String defaultValue = "";

      if (defaultConfig.containsKey(key)) defaultValue = defaultConfig.get(key).toString();
      String value = defaultValue;

      if (config.containsKey(key))
      {
         value = config.get(key).toString();

         if (type.equals("filePath") && !Validator.isValidFilePath(value))
         {
            System.out.println(" - " + key + " must be a valid file path (.json or .txt) -- using default value (" + defaultValue + ").");
            value = defaultValue;
         }
         else if (type.equals("intPos") && !Validator.isValidInt(value, 0))
         {
            System.out.println(" - " + key + " must be a valid non-negative integer -- using default value (" + defaultValue + ").");
            value = defaultValue;
         }
         else if (type.equals("doublePos") && !Validator.isValidDouble(value, 0))
         {
            System.out.println(" - " + key + " must be a valid non-negative double -- using default value (" + defaultValue + ").");
            value = defaultValue;
         }
      }  // if (config.containsKey(key))

      return value;
   }     // public String get(String key, String type)
}        // public class Config
