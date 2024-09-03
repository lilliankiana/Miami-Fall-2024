package CSE465.Homework1;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/** Interpreter that executs Z+- programs.
 *
 *@author Lillian Elliott 
 * 
 */
public class Zpm {
  /**
   * ArrayList holding variables in Zpm file
   */
  public static HashMap<String, String> valueList = new HashMap<>();

  public static Integer lineNumber = 0;
  /** Takes Z+- code as file with .zpm extention through CMD line argument.
   * 
   * @param args Array of CMD arguments.
   * @throws FileNotFoundException 
   */
  public static void main(String[] args) throws FileNotFoundException {
    //Checking command line arguments
    if (args.length == 0) {
      System.out.println("No file was given, please try again.");
      return;
    }

    String fileName = args[0];
    int check = fileCheck(fileName);

    if (check == 1) {
      System.out.println("Sucessfully obtained file");
    } else {
      System.out.println("File issues were detected, please try again.");
      return;
    }

    readfile(fileName);
  }

  /** Parses Zpm file
   * 
   * @param fileName name of file
   * @throws FileNotFoundException 
   */
  private static void readfile(String fileName) throws FileNotFoundException {
    Scanner scanner = new Scanner(new File(fileName));
    
    // Parse through file line by line
    while (scanner.hasNextLine()) {
      lineNumber++;
      // Gets line in array without spaces
      String[] line = scanner.nextLine().split(" ");
      switchCase(line[0], line);
    }


    scanner.close();
  }

  private static void switchCase(String first, String[] line) {
    switch(first) {
      case "PRINT":
        printing(line);
        break;
      case "FOR":
        looping(line);
        break;
      default:
        String variable = line[0];
        String operation = line[1];
        String value = line [2];
        variableChange(variable, operation, value);

    }
  }

  private static void variableChange(String variable, String operation, String value) {
    if (operation.equals("=")) {
      valueList.put(variable, value);
    } else {
      // Checks if value given is another variable
      if (valueList.get(value) != null) {
        value = valueList.get(value);
      }
      int type = errorCheck(valueList.get(variable), value);
      //try {
        //if (operation.equals("+=")) {

        //} else if (operation.equals("*=") && type == 1){

        //} else if (operation.equals("-=") && type == 1)
        //switch(operation) {
          //case "+=":
          //case "*=":
          //case "-=":
          //default:
            //throw new 
        //}
      //} catch (UnsupportedOperationException ex) {
        //  System.out.println("RUNTIME ERROR: line" + lineNumber);
      //}
    }
  }

  /** Checks if the two values are compatible and their types
   * 
   * @param firstValue String of original value
   * @param secondValue String of value to compute
   * @return int value correlates with type, 0 = string, 1 = int
   */
  private static int errorCheck(String firstValue, String secondValue) {
    boolean checkFirst = false;
    boolean checkSecond = false;
    if (firstValue.charAt(0) == '"') {
      checkFirst = true;
    }

    if (secondValue.charAt(0) == '"') {
      checkSecond = true;
    }

    try {
      if (checkFirst != checkSecond) {
        throw new UnsupportedOperationException();
      }
    } catch (UnsupportedOperationException ex) {
        System.out.println("RUNTIME ERROR: line" + lineNumber);
    }

    if (checkFirst) { // Is an integer
      return 1;
    } else { // Is a String
      return 0;
    }
  }

  private static void looping(String[] line) {
    int iterations = Integer.parseInt(line[0]);
    for (int i = 0; i < iterations; i++) {
      // Send information to variableChange()
    }
  }

  /** Prints the variable with value
   * 
   * @param line Array of the line's string values
   */
  private static void printing(String[] line) {
    String variable = line[1];
    String value = valueList.get(variable);
    if (value.charAt(0) == '"') {
      value = value.substring(1, value.length() - 1);
    }
    System.out.println(variable + "=" + value);
  }

  /** Checks if file is valid
   * 
   * @param fileName name of file
   * @return int for check in main
   */
  private static int fileCheck(String fileName) {
    //Checking file type
    if ((fileName.length() <= 4)) {
      System.out.println("File name too short");
      return 0;
    }

    String ext = fileName.substring(fileName.length()-4);
    if (!(ext.equals(".zpm"))) {
      System.out.println("File name is not valid, please provide .zpm file.");
      return 0;
    }

    File zpmFile = new File(fileName);

    // Exception handling file access
    try {
      if (!zpmFile.isFile()) {
        throw new IOException("File does not exist.");
      }
    } catch (IOException e) {
      System.err.println("Error occurred:" + e.getMessage());
      return 0;
    }
    // No file issues were caught
    return 1;
  }
}
