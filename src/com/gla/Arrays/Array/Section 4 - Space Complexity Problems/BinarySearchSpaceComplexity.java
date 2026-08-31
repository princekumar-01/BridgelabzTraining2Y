public class BinarySearchSpaceComplexity {
    /*
     * SC-2: BINARY SEARCH SPACE COMPLEXITY
     *
     * Iterative binary search stores only low, high, and middle indices.
     * These variables occupy constant memory regardless of array size.
     * Auxiliary space complexity: O(1).
     * A recursive implementation would instead use O(log N) call-stack space.
     */
    public static int binarySearch(int[] array, int target) {
        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            int middle = low + (high - low) / 2;
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
        System.out.println(binarySearch(array, 4));
    }
}
