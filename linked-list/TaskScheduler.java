public class TaskScheduler {
    static class Task {
        int taskId;
        String name;
        int priority;
        String dueDate;
        Task next;

        Task(int taskId, String name, int priority, String dueDate) {
            this.taskId = taskId;
            this.name = name;
            this.priority = priority;
            this.dueDate = dueDate;
        }

        @Override
        public String toString() {
            return "ID=" + taskId + ", Name=" + name + ", Priority=" + priority + ", Due=" + dueDate;
        }
    }

    private Task head;
    private Task current;

    public void addAtEnd(Task task) {
        if (head == null) {
            head = task;
            task.next = task;
            current = head;
            return;
        }
        Task last = head;
        while (last.next != head) last = last.next;
        last.next = task;
        task.next = head;
    }

    public void addAtBeginning(Task task) {
        if (head == null) {
            addAtEnd(task);
            return;
        }
        Task last = head;
        while (last.next != head) last = last.next;
        task.next = head;
        last.next = task;
        head = task;
        current = head;
    }

    public void addAtPosition(int position, Task task) {
        if (position <= 0 || head == null) {
            addAtBeginning(task);
            return;
        }
        Task prev = head;
        for (int i = 0; i < position - 1 && prev.next != head; i++) prev = prev.next;
        task.next = prev.next;
        prev.next = task;
    }

    public boolean removeById(int id) {
        if (head == null) return false;
        if (head.taskId == id && head.next == head) {
            head = null;
            current = null;
            return true;
        }
        Task prev = head;
        while (prev.next != head && prev.next.taskId != id) prev = prev.next;
        if (prev.next == head) {
            if (head.taskId != id) return false;
            Task second = head.next;
            prev.next = second;
            head = second;
            if (current.taskId == id) current = head;
            return true;
        }
        Task target = prev.next;
        prev.next = target.next;
        if (target == current) current = target.next;
        target.next = null;
        return true;
    }

    public Task viewCurrent() {
        return current;
    }

    public void moveToNext() {
        if (current != null) current = current.next;
    }

    public void displayAll() {
        if (head == null) {
            System.out.println("No tasks.");
            return;
        }
        Task node = head;
        do {
            System.out.println(node);
            node = node.next;
        } while (node != head);
    }

    public void searchByPriority(int priority) {
        System.out.println("Tasks with priority " + priority + ":");
        if (head == null) return;
        Task node = head;
        do {
            if (node.priority == priority) System.out.println(node);
            node = node.next;
        } while (node != head);
    }

    public static void main(String[] args) {
        TaskScheduler scheduler = new TaskScheduler();
        scheduler.addAtEnd(new Task(1, "Write report", 2, "2026-12-01"));
        scheduler.addAtEnd(new Task(2, "Fix bug", 1, "2026-11-25"));
        scheduler.addAtBeginning(new Task(3, "Review code", 3, "2026-11-20"));
        scheduler.addAtPosition(2, new Task(4, "Update docs", 2, "2026-12-10"));
        System.out.println("All tasks:");
        scheduler.displayAll();
        System.out.println("Current: " + scheduler.viewCurrent());
        scheduler.moveToNext();
        System.out.println("After moving next: " + scheduler.viewCurrent());
        scheduler.searchByPriority(2);
        System.out.println("Remove task 2: " + scheduler.removeById(2));
        System.out.println("All tasks after removal:");
        scheduler.displayAll();
    }
}
