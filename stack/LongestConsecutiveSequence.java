import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LongestConsecutiveSequence {
    public static int longest(int[] arr) {
        Set<Integer> set = new HashSet<>();
        for (int num : arr) set.add(num);
        int best = 0;
        for (int num : set) {
            if (!set.contains(num - 1)) {
                int current = num;
                int length = 1;
                while (set.contains(current + 1)) {
                    current++;
                    length++;
                }
                best = Math.max(best, length);
            }
        }
        return best;
    }

    public static void main(String[] args) {
        int[] arr = {100, 4, 200, 1, 3, 2};
        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("Longest consecutive sequence length: " + longest(arr));
    }
}
