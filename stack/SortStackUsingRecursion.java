import java.util.ArrayDeque;
import java.util.Deque;

public class SortStackUsingRecursion {
    private static void insertInSortedOrder(Deque<Integer> stack, int value) {
        if (stack.isEmpty() || value <= stack.peek()) {
            stack.push(value);
            return;
        }
        int top = stack.pop();
        insertInSortedOrder(stack, value);
        stack.push(top);
    }

    public static void sortStack(Deque<Integer> stack) {
        if (stack.isEmpty()) return;
        int top = stack.pop();
        sortStack(stack);
        insertInSortedOrder(stack, top);
    }

    public static void main(String[] args) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(3);
        stack.push(1);
        stack.push(4);
        stack.push(2);
        stack.push(5);
        System.out.println("Before (top first): " + stack);
        sortStack(stack);
        System.out.println("After (top first):  " + stack);
        System.out.println("Pop order (ascending):");
        while (!stack.isEmpty()) System.out.print(stack.pop() + " ");
        System.out.println();
    }
}
