package CSE374;

// Java implementation of QuickSort
class QS_Class {

    // Implement the quickSort here: you could add any function you need.

    // Driver Code
    public static void main(String[] args)
    {
        int[] arr = { 10, 7, 8, 9, 1, 5 };
        int N = arr.length;

        // Function call
        quickSort(arr, 0, N - 1);
        System.out.println("Sorted array:");
        printArr(arr);
    }

    /** Prints sorted array.
     * 
     * @param arr Sorted array of integers.
     */
    private static void printArr(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

    /** Sorts the array from least to greatest.
     * 
     * @param arr Array to be sorted
     * @param i Starting index
     * @param j Ending index
     */
    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {

            int pivot = arr[high];
            int i = low - 1;
            for (int j = low; j < high; j++) {
                if (arr[j] < pivot) {
                    i++;
                    swap(arr, i, j);
                }
            }
            swap(arr, i + 1, high);
            int partition = i + 1;

            quickSort(arr, low, partition - 1);
            quickSort(arr, partition + 1, high);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

// This code is contributed by Ayush Choudhary
// Improved by Ajay Virmoti
