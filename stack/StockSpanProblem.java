import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.Deque;

public class StockSpanProblem {
    public static int[] calculateSpan(int[] prices) {
        int n = prices.length;
        int[] span = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && prices[stack.peek()] <= prices[i]) {
                stack.pop();
            }
            span[i] = stack.isEmpty() ? i + 1 : i - stack.peek();
            stack.push(i);
        }
        return span;
    }

    public static void main(String[] args) {
        int[] prices = {100, 80, 60, 70, 60, 75, 85};
        int[] spans = calculateSpan(prices);
        System.out.println("Prices: " + Arrays.toString(prices));
        System.out.println("Spans:  " + Arrays.toString(spans));
    }
}
