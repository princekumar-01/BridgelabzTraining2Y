import java.util.Arrays;

public class MergeSortTimeComplexity {
    /*
     * TC-3: MERGE SORT TIME COMPLEXITY
     *
     * Split tree for {8, 61, 2, 3, 4, 0}:
     *                         [8, 61, 2, 3, 4, 0]
     *                    /                              \
     *             [8, 61, 2]                         [3, 4, 0]
     *             /        \                         /        \
     *          [8]       [61, 2]                  [3]       [4, 0]
     *                    /     \                            /    \
     *                  [61]    [2]                        [4]    [0]
     *
     * Recurrence: T(N) = 2T(N/2) + O(N), with T(1) = O(1).
     * Level 0 performs O(N) merge work.
     * Level 1 has 2 subproblems of N/2, so total work is O(N).
     * Level i has 2^i subproblems of N/2^i, so total work is O(N).
     * There are log2(N) division levels.
     * Total work = O(N) * O(log N) = O(N log N).
     * Best, average, and worst-case time complexity: O(N log N).
     */
    public static void mergeSort(int[] array, int left, int right) {
        if (left >= right) {
            return;
        }

        int middle = left + (right - left) / 2;
        mergeSort(array, left, middle);
        mergeSort(array, middle + 1, right);
        merge(array, left, middle, right);
    }

    private static void merge(int[] array, int left, int middle, int right) {
        int[] temporary = new int[right - left + 1];
        int first = left;
        int second = middle + 1;
        int index = 0;

        while (first <= middle && second <= right) {
            temporary[index++] = array[first] <= array[second]
                    ? array[first++] : array[second++];
        }
        while (first <= middle) {
            temporary[index++] = array[first++];
        }
        while (second <= right) {
            temporary[index++] = array[second++];
        }
        System.arraycopy(temporary, 0, array, left, temporary.length);
    }

    public static void main(String[] args) {
        int[] array = {8, 61, 2, 3, 4, 0};
        mergeSort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));
    }
}
