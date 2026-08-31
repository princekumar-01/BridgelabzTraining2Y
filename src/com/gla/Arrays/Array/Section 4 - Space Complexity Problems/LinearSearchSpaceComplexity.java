public class LinearSearchSpaceComplexity {
    /*
     * SC-1: LINEAR SEARCH SPACE COMPLEXITY
     *
     * Iterative linear search uses only a loop index and the target value.
     * The amount of additional memory does not grow with the input array.
     * Auxiliary space complexity: O(1).
     * The input array itself is not counted as auxiliary space.
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
        System.out.println(linearSearch(array, 4));
    }
}
