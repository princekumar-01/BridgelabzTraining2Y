import java.util.Comparator;

public class InventoryManagementSystem {
    static class Item {
        String name;
        int itemId;
        int quantity;
        double price;
        Item next;

        Item(String name, int itemId, int quantity, double price) {
            this.name = name;
            this.itemId = itemId;
            this.quantity = quantity;
            this.price = price;
        }

        @Override
        public String toString() {
            return "ID=" + itemId + ", Name=" + name + ", Qty=" + quantity + ", Price=" + price;
        }
    }

    private Item head;

    public void addAtBeginning(Item item) {
        item.next = head;
        head = item;
    }

    public void addAtEnd(Item item) {
        if (head == null) {
            head = item;
            return;
        }
        Item current = head;
        while (current.next != null) current = current.next;
        current.next = item;
    }

    public void addAtPosition(int position, Item item) {
        if (position <= 0 || head == null) {
            addAtBeginning(item);
            return;
        }
        Item current = head;
        for (int i = 0; i < position - 1 && current.next != null; i++) current = current.next;
        item.next = current.next;
        current.next = item;
    }

    public boolean removeById(int id) {
        if (head == null) return false;
        if (head.itemId == id) {
            head = head.next;
            return true;
        }
        Item prev = head;
        while (prev.next != null && prev.next.itemId != id) prev = prev.next;
        if (prev.next == null) return false;
        prev.next = prev.next.next;
        return true;
    }

    public boolean updateQuantity(int id, int quantity) {
        Item item = searchById(id);
        if (item == null) return false;
        item.quantity = quantity;
        return true;
    }

    public Item searchById(int id) {
        Item current = head;
        while (current != null) {
            if (current.itemId == id) return current;
            current = current.next;
        }
        return null;
    }

    public Item searchByName(String name) {
        Item current = head;
        while (current != null) {
            if (current.name.equals(name)) return current;
            current = current.next;
        }
        return null;
    }

    public double totalValue() {
        double total = 0;
        Item current = head;
        while (current != null) {
            total += current.price * current.quantity;
            current = current.next;
        }
        return total;
    }

    public void sort(Comparator<Item> comparator) {
        head = mergeSort(head, comparator);
    }

    private Item mergeSort(Item node, Comparator<Item> comparator) {
        if (node == null || node.next == null) return node;
        Item slow = node, fast = node.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        Item right = slow.next;
        slow.next = null;
        return merge(mergeSort(node, comparator), mergeSort(right, comparator), comparator);
    }

    private Item merge(Item a, Item b, Comparator<Item> comparator) {
        Item dummy = new Item("", -1, 0, 0);
        Item tail = dummy;
        while (a != null && b != null) {
            if (comparator.compare(a, b) <= 0) {
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }
        tail.next = (a != null) ? a : b;
        return dummy.next;
    }

    public void displayAll() {
        if (head == null) {
            System.out.println("Inventory empty.");
            return;
        }
        Item current = head;
        while (current != null) {
            System.out.println(current);
            current = current.next;
        }
    }

    public static void main(String[] args) {
        InventoryManagementSystem inventory = new InventoryManagementSystem();
        inventory.addAtEnd(new Item("Pen", 1, 50, 2.0));
        inventory.addAtEnd(new Item("Notebook", 2, 20, 5.5));
        inventory.addAtBeginning(new Item("Eraser", 3, 100, 0.5));
        inventory.addAtPosition(2, new Item("Scale", 4, 30, 3.0));
        System.out.println("Inventory:");
        inventory.displayAll();
        System.out.println("Total value: " + inventory.totalValue());
        inventory.updateQuantity(2, 35);
        System.out.println("After updating qty of ID 2: " + inventory.searchById(2));
        System.out.println("Search by name 'Pen': " + inventory.searchByName("Pen"));
        inventory.sort(Comparator.comparing(i -> i.name));
        System.out.println("Sorted by name ascending:");
        inventory.displayAll();
        inventory.sort(Comparator.comparingDouble((Item i) -> i.price).reversed());
        System.out.println("Sorted by price descending:");
        inventory.displayAll();
        System.out.println("Remove ID 3: " + inventory.removeById(3));
        inventory.displayAll();
    }
}
