public class IterativeBinarySearchComplexity {
    /*
     * TC-2: ITERATIVE BINARY SEARCH TIME COMPLEXITY
     *
     * Array: {0, 2, 3, 4, 8, 61}, target = 4
     * Step 1: low=0, high=5, mid=2, value=3. Since 4 > 3, low=3.
     * Step 2: low=3, high=5, mid=4, value=8. Since 4 < 8, high=3.
     * Step 3: low=3, high=3, mid=3, value=4. Target found.
     *
     * After k comparisons, the remaining search space is approximately N/2^k.
     * In the worst case, N/2^k <= 1.
     * Therefore 2^k >= N and k >= log2(N).
     * The exact maximum is floor(log2(N)) + 1 comparisons for N >= 1.
     * Final worst-case time complexity: O(log N).
     */
    public static int binarySearch(int[] array, int target) {
        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            int middle = low + (high - low) / 2;
            System.out.printf("low=%d, high=%d, mid=%d, value=%d%n",
                    low, high, middle, array[middle]);

            if (array[middle] == target) {
                return middle;
            } else if (array[middle] < target) {
                low = middle + 1;
            } else {
                high = middle - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] array = {0, 2, 3, 4, 8, 61};
        System.out.println("Target index: " + binarySearch(array, 4));
    }
}
