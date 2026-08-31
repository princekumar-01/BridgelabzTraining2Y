public class LinearSearchTimeComplexity {
    /*
     * TC-1: LINEAR SEARCH TIME COMPLEXITY
     *
     * Sample array: {8, 61, 2, 3, 4, 0}
     *
     * Best case: O(1)
     * Searching for 8 takes one comparison because 8 is the first element.
     *
     * Worst case: O(N)
     * Searching for 0 takes N comparisons because it is the last element.
     * Searching for a missing value such as 5 also examines all N elements.
     *
     * Average case: O(N)
     * A randomly positioned target requires about (N + 1) / 2 comparisons.
     * Big-O ignores the constant factor, so the result is O(N).
     */
    public static int linearSearch(int[] array, int target) {
        for (int index = 0; index < array.length; index++) {
            if (array[index] == target) {
                return index;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] array = {8, 61, 2, 3, 4, 0};
        System.out.println("Index of 8: " + linearSearch(array, 8));
        System.out.println("Index of 0: " + linearSearch(array, 0));
        System.out.println("Index of 5: " + linearSearch(array, 5));
    }
}
