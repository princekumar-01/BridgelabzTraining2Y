import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class SlidingWindowMaximum {
    public static int[] maxInWindows(int[] arr, int k) {
        if (arr == null || k <= 0 || k > arr.length) {
            throw new IllegalArgumentException("Invalid array or window size");
        }
        int[] result = new int[arr.length - k + 1];
        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < arr.length; i++) {
            while (!deque.isEmpty() && deque.peekFirst() <= i - k) deque.pollFirst();
            while (!deque.isEmpty() && arr[deque.peekLast()] <= arr[i]) deque.pollLast();
            deque.addLast(i);
            if (i >= k - 1) result[i - k + 1] = arr[deque.peekFirst()];
        }
        return result;
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, -1, -3, 5, 3, 6, 7};
        int k = 3;
        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("Window size: " + k);
        System.out.println("Maxima: " + Arrays.toString(maxInWindows(arr, k)));
    }
}
