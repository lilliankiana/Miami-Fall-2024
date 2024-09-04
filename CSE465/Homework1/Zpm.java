package CSE465.Homework1;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

      String line = scanner.nextLine();
      List<String> lineData = new ArrayList<String>();

      // Splits line by spaces unless there are quotes
      Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(line);
      while (m.find()) {
        lineData.add(m.group(1));
      }

      switchCase(lineData);
    }


    scanner.close();
  }

  /** Checks if the start of the line is a function
   * 
   * @param lineData ArrayList containing the line data
   */
  private static void switchCase(List<String> lineData) {
    switch(lineData.get(0)) {
      case "PRINT":
        printing(lineData);
        break;
      case "FOR":
        looping(lineData);
        break;
      default:
        String variable = lineData.get(0);
        String operation = lineData.get(1);
        String value = lineData.get(2);
        variableChange(variable, operation, value);
    }
  }

  /** Changes that variable information based on the command
   * 
   * @param variable Variable to be changed
   * @param operation Operation to be performed
   * @param value The new value to be used
   */
  private static void variableChange(String variable, String operation, String value) {
    // Checks if value given is another variable
    value = initializeError(value);

    if (operation.equals("=")) {
      valueList.put(variable, value);
    } else {
      // Checks variable initialization
      initializeError(variable);
      // Checks for runtime errors with type issues
      int type = errorCheck(valueList.get(variable), value);

      try {
        // Type checking for operations
        if (operation.equals("+=") && type == 0) {
          stringAdd(variable, value);
        } else if (type == 1) {
          integerOperation(variable, value, operation);
        } else {
          // Unsupported string operations
          throw new UnsupportedOperationException();
        }
      } catch (UnsupportedOperationException ex) {
          System.out.println("RUNTIME ERROR: line " + lineNumber);
          System.exit(0);
      }
    }
  }

  private static String initializeError(String value) {
    char firstChar = value.charAt(0);
    if (valueList.containsKey(value)) {
      return valueList.get(value);
    } 

    try {
      // It's a variable if it is not an int & doesn't contain quotes
      if (firstChar != '"' && !Character.isDigit(firstChar)) {
        throw new UnsupportedOperationException();
      }
    } catch (UnsupportedOperationException ex) {
      System.out.println("RUNTIME ERROR: line " + lineNumber);
      System.exit(0);
    }
    return value;
  }

  /** Performs calculations on variables that are integers
   * 
   * @param variable Variable to be changed
   * @param operation Operation to be performed
   * @param value The new value to be used
   */
  private static void integerOperation(String variable, String value, String operation) {
    switch(operation) {
      case "+=":
        int sum = Integer.parseInt(valueList.get(variable)) + Integer.parseInt(value);
        valueList.put(variable, String.valueOf(sum));
        break;
      case "*=":
        int product = Integer.parseInt(valueList.get(variable)) * Integer.parseInt(value);
        valueList.put(variable, String.valueOf(product));
        break;
      case "-=":
        int difference = Integer.parseInt(valueList.get(variable)) - Integer.parseInt(value);
        valueList.put(variable, String.valueOf(difference));
        break;
      default:
    };
  }

  /** Adds strings together
   * 
   * @param variable Variable to be changed
   * @param value The new value to be used
   */
  private static void stringAdd(String variable, String value) {
    String original = valueList.get(variable).substring(0, valueList.get(variable).length() - 1);
    value = value.substring(1, value.length());
    valueList.put(variable, original+value);
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
        System.exit(0);
    }

    if (checkFirst) { // Is an Int
      return 0;
    } else { // Is a string
      return 1;
    }
  }

  /** Performs the loop function
   * 
   * @param lineData ArrayList containing the line data
   */
  private static void looping(List<String> lineData) {
    // Amount of times to repeat
    int iterations = Integer.parseInt(lineData.get(1));
    
    // Seperate statements by getting index and seperating
    ArrayList<List<String>> loopList = new ArrayList<>();
    int index = 2;
    for (int i = 2; i < lineData.size(); i++) {
      if (lineData.get(i).equals(";")) {
        loopList.add(lineData.subList(index, i));
        index = i + 1;
      }

    }

    // Performs operations specified amount of times
    for (int i = 0; i < iterations; i++) {
      // Send information to switchCase() like a new line
      for (int j = 0; j < loopList.size(); j++) {
        switchCase(loopList.get(j));
      }
    }
  }

  /** Prints the variable with value
   * 
   * @param lineData Array of the line's string values
   */
  private static void printing(List<String> lineData) {
    String variable = lineData.get(1);
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
