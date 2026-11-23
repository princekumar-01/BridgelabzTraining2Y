import java.util.ArrayDeque;
import java.util.Deque;

public class QueueUsingStacks {
    private final Deque<Integer> inStack = new ArrayDeque<>();
    private final Deque<Integer> outStack = new ArrayDeque<>();

    public void enqueue(int value) {
        inStack.push(value);
    }

    public int dequeue() {
        if (outStack.isEmpty()) {
            if (inStack.isEmpty()) {
                throw new IllegalStateException("Queue is empty");
            }
            while (!inStack.isEmpty()) {
                outStack.push(inStack.pop());
            }
        }
        return outStack.pop();
    }

    public int peek() {
        if (outStack.isEmpty()) {
            while (!inStack.isEmpty()) {
                outStack.push(inStack.pop());
            }
        }
        return outStack.peek();
    }

    public boolean isEmpty() {
        return inStack.isEmpty() && outStack.isEmpty();
    }

    public static void main(String[] args) {
        QueueUsingStacks queue = new QueueUsingStacks();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        System.out.println("Dequeue: " + queue.dequeue());
        System.out.println("Dequeue: " + queue.dequeue());
        queue.enqueue(4);
        System.out.println("Peek: " + queue.peek());
        System.out.println("Dequeue: " + queue.dequeue());
        System.out.println("Dequeue: " + queue.dequeue());
        System.out.println("Empty: " + queue.isEmpty());
    }
}
