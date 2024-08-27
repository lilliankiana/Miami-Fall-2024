/*
 * Author: Lillian Elliott
 * Date: Fall 2024
 * Course: CSE374 Algorithms
 * 
 * Description: Obtain the location of two numbers that
 *  add up to the target total. The array of numbers and 
 *  target is obtained from user input.  
 * 
 */
import java.util.Scanner;

public class Quiz1 {
    public static int[] findNums(int[] nums, int target) {
        // Traverse array to check for target
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (i != j & nums[i] + nums[j] == target) {
                    return new int[] {i,j};
                }
            }
        }

        // If targets were not found
        return null;
    }

    public static void main(String[] args) {
        Scanner inputScan = new Scanner(System.in); // Create Scanner object
        System.out.println("Enter array of numbers seperated by a comma:");
        String userInput = inputScan.nextLine(); // Get line with numbers
        System.out.println("Enter target number:");
        String targetInput = inputScan.nextLine(); // Get line with target

        // Initialize values
        int target = Integer.parseInt(targetInput);
        String[] StrArray = userInput.split(","); // Split line values
        int[] nums = new int[StrArray.length];
        // Parse string values into int array
        for(int i = 0; i < StrArray.length; i++) {
            nums[i] = Integer.parseInt(StrArray[i]);
        }

        // Call class
        int[] result = findNums(nums, target);

        // Print results
        if (result == null) {
            System.err.println("Error: Numbers not found.");
        } else {
            System.out.println("Because nums[" + result[0] + "] + nums["
            + result[1] + "] == " + nums[result[0]] + " + " + nums[result[1]] 
            + " == " + target + ", we return [" + result[0]
            + ", " + result[1] + "].");
        }
    }
}
