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
      System.out.println("No file was given");
      return;
    }

    String filename = args[0];

    // Checking file type
    if (filename.length() < 4) { //} || filename.substring(filename.length() - 4) != ".zpm") {
      System.out.println("File name is not valid, please provide .zpm file.");
      return;
    }

    File zpmFile = new File(filename);

    // Exception handling file access
    try {
      if (!zpmFile.isFile()) {
        throw new IOException("File does not exist.");
      }
    } catch (IOException e) {
      System.err.println("Error occurred:" + e.getMessage());
    }

    System.out.println("Sucessfully obtained file");
  }
}
