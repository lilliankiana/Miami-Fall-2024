import java.util.Scanner;

/** Obtain the location of two numbers that add up to the target total. 
 *  The array of numbers and target is obtained from user input.
 *
 * @author Lillian Elliott Date: Fall 2024 Course: CSE374 Algorithms
 * 
 */
public class Quiz1 {

  /** findNums(): Parses through array of numbers.
   *  Finds the location of the two ints that add up to the target.
   *
   * @param nums Array of integers
   * @param target Chosen integer for sum
   * @return Array of number locations
   */
  public static int[] findNums(int[] nums, int target) {
    // Traverse array to check for target
    for (int i = 0; i < nums.length; i++) {
      for (int j = 0; j < nums.length; j++) {
        if (i != j & nums[i] + nums[j] == target) {
          return new int[] {i, j};
        }
      }
    }
    // If targets were not found
    return null;
  }

  /** Main.
   *
   * @param args String array
   */
  public static void main(String[] args) {
    Scanner inputScan = new Scanner(System.in); // Create Scanner object
    System.out.println("Enter array of numbers seperated by a comma:");
    String userInput = inputScan.nextLine(); // Get line with numbers
    System.out.println("Enter target number:");
    String targetInput = inputScan.nextLine(); // Get line with target

    inputScan.close(); // Close Scanner

    // Initialize values
    int target = Integer.parseInt(targetInput);
    String[] strArray = userInput.split(","); // Split line values
    int[] nums = new int[strArray.length];
    // Parse string values into int array
    for (int i = 0; i < strArray.length; i++) {
      nums[i] = Integer.parseInt(strArray[i]);
    }

    // Call class
    int[] result = findNums(nums, target);

    // Print results
    if (result == null) {
      System.err.println("Error: Numbers not found.");
    } else {
      System.out.println("Because nums[" + result[0] + "] + nums[" + result[1] + "] == "
          + nums[result[0]] + " + " + nums[result[1]] + " == " + target + ", we return ["
          + result[0] + ", " + result[1] + "].");
    }
  }
}
