public class UndoRedoTextEditor {
    static class State {
        String text;
        State prev;
        State next;

        State(String text) {
            this.text = text;
        }
    }

    private final int limit;
    private State head;
    private State tail;
    private State current;
    private int size;

    public UndoRedoTextEditor(int limit) {
        this.limit = limit;
    }

    public void addState(String text) {
        if (tail == null) {
            State node = new State(text);
            head = tail = current = node;
            size = 1;
            return;
        }
        if (current != tail) {
            current.next = null;
            tail = current;
        }
        State node = new State(text);
        node.prev = tail;
        tail.next = node;
        tail = node;
        current = node;
        size++;
        while (size > limit && head != null) {
            head = head.next;
            if (head != null) head.prev = null;
            size--;
        }
    }

    public String undo() {
        if (current == null || current.prev == null) return null;
        current = current.prev;
        return current.text;
    }

    public String redo() {
        if (current == null || current.next == null) return null;
        current = current.next;
        return current.text;
    }

    public String displayCurrent() {
        if (current == null) {
            System.out.println("No text state.");
            return null;
        }
        System.out.println("Current text: \"" + current.text + "\"");
        return current.text;
    }

    public int historySize() {
        return size;
    }

    public static void main(String[] args) {
        UndoRedoTextEditor editor = new UndoRedoTextEditor(10);
        editor.addState("");
        editor.addState("Hello");
        editor.addState("Hello world");
        editor.addState("Hello world!");
        editor.displayCurrent();
        System.out.println("Undo -> \"" + editor.undo() + "\"");
        System.out.println("Undo -> \"" + editor.undo() + "\"");
        System.out.println("Redo -> \"" + editor.redo() + "\"");
        editor.addState("Hello Java");
        editor.displayCurrent();
        System.out.println("Redo after new action: " + editor.redo());
        System.out.println("History size: " + editor.historySize());
    }
}
