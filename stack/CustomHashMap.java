import java.util.HashMap;
import java.util.Map;

public class CustomHashMap {
    private static class Node {
        int key;
        int value;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private final Node[] buckets;
    private final int capacity;
    private int size = 0;

    public CustomHashMap(int capacity) {
        this.capacity = capacity;
        this.buckets = new Node[capacity];
    }

    private int index(int key) {
        return Math.abs(key % capacity);
    }

    public void put(int key, int value) {
        int idx = index(key);
        Node current = buckets[idx];
        while (current != null) {
            if (current.key == key) {
                current.value = value;
                return;
            }
            current = current.next;
        }
        Node node = new Node(key, value);
        node.next = buckets[idx];
        buckets[idx] = node;
        size++;
    }

    public Integer get(int key) {
        Node current = buckets[index(key)];
        while (current != null) {
            if (current.key == key) return current.value;
            current = current.next;
        }
        return null;
    }

    public boolean remove(int key) {
        int idx = index(key);
        Node current = buckets[idx];
        Node prev = null;
        while (current != null) {
            if (current.key == key) {
                if (prev == null) buckets[idx] = current.next;
                else prev.next = current.next;
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public static void main(String[] args) {
        CustomHashMap map = new CustomHashMap(8);
        map.put(1, 100);
        map.put(9, 900);
        map.put(2, 200);
        System.out.println("Get 1: " + map.get(1));
        System.out.println("Get 9: " + map.get(9));
        System.out.println("Get 5: " + map.get(5));
        System.out.println("Size: " + map.size());
        map.put(1, 111);
        System.out.println("Get 1 after update: " + map.get(1));
        System.out.println("Remove 2: " + map.remove(2));
        System.out.println("Get 2: " + map.get(2));
    }
}
