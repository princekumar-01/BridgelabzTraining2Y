import java.util.Arrays;

public class MergeSortSpaceComplexity {
    /*
     * SC-3: MERGE SORT SPACE COMPLEXITY
     *
     * Merging needs temporary storage because values from two sorted portions
     * must be preserved while they are copied back in sorted order. Across the
     * active merge, the temporary array can contain N elements, requiring O(N)
     * auxiliary space. The recursion stack adds O(log N), which is dominated by
     * O(N), so the overall auxiliary space complexity is O(N).
     *
     * Insertion Sort is in-place and uses O(1) auxiliary space. Typical in-place
     * Quick Sort uses O(log N) average stack space and O(N) stack space in its
     * worst case. Therefore, standard Merge Sort is less space-efficient, though
     * it guarantees O(N log N) running time and is stable.
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
