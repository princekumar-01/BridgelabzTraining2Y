import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSumProblem {
    public static int[] findIndices(int[] arr, int target) {
        Map<Integer, Integer> seen = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            int complement = target - arr[i];
            if (seen.containsKey(complement)) {
                return new int[]{seen.get(complement), i};
            }
            seen.put(arr[i], i);
        }
        return new int[]{-1, -1};
    }

    public static void main(String[] args) {
        int[] arr = {2, 7, 11, 15};
        int target = 9;
        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("Target: " + target);
        System.out.println("Indices: " + Arrays.toString(findIndices(arr, target)));
        System.out.println("Indices for target 20: " + Arrays.toString(findIndices(arr, 20)));
    }
}
