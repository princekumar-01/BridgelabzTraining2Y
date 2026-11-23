import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PairWithGivenSum {
    public static boolean hasPair(int[] arr, int target) {
        Set<Integer> seen = new HashSet<>();
        for (int num : arr) {
            if (seen.contains(target - num)) return true;
            seen.add(num);
        }
        return false;
    }

    public static void main(String[] args) {
        int[] arr = {8, 7, 2, 5, 3, 1};
        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("Pair with sum 10 exists: " + hasPair(arr, 10));
        System.out.println("Pair with sum 20 exists: " + hasPair(arr, 20));
    }
}
