import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZeroSumSubarrays {
    public static List<int[]> findAll(int[] arr) {
        List<int[]> result = new ArrayList<>();
        Map<Long, List<Integer>> sumIndices = new HashMap<>();
        sumIndices.put(0L, new ArrayList<>(java.util.Collections.singletonList(-1)));
        long prefix = 0;
        for (int i = 0; i < arr.length; i++) {
            prefix += arr[i];
            sumIndices.computeIfAbsent(prefix, k -> new ArrayList<>()).add(i);
        }
        for (Map.Entry<Long, List<Integer>> entry : sumIndices.entrySet()) {
            List<Integer> indices = entry.getValue();
            for (int i = 0; i < indices.size(); i++) {
                for (int j = i + 1; j < indices.size(); j++) {
                    result.add(new int[]{indices.get(i) + 1, indices.get(j)});
                }
            }
        }
        result.sort((a, b) -> a[0] != b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1]));
        return result;
    }

    public static void main(String[] args) {
        int[] arr = {0, 4, -4, 2, -2, 1, -1};
        System.out.println("Array: " + Arrays.toString(arr));
        for (int[] pair : findAll(arr)) {
            System.out.println("Subarray [" + pair[0] + ", " + pair[1] + "]");
        }
    }
}
