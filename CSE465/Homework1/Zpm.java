package CSE465.Homework1;
import java.io.File;
import java.io.IOException;

/** Interpreter that executs Z+- programs.
 *
 *@author Lillian Elliott 
 * 
 */
public class Zpm {
  /** Takes Z+- code as file with .zpm extention through CMD line argument.
   * 
   * @param args Array of CMD arguments.
   */
  public static void main(String[] args) {
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

    
  }

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
