public class StudentRecordManagement {
    static class Student {
        int rollNumber;
        String name;
        int age;
        String grade;
        Student next;

        Student(int rollNumber, String name, int age, String grade) {
            this.rollNumber = rollNumber;
            this.name = name;
            this.age = age;
            this.grade = grade;
        }

        @Override
        public String toString() {
            return "Roll=" + rollNumber + ", Name=" + name + ", Age=" + age + ", Grade=" + grade;
        }
    }

    private Student head;

    public void addAtBeginning(Student s) {
        s.next = head;
        head = s;
    }

    public void addAtEnd(Student s) {
        if (head == null) {
            head = s;
            return;
        }
        Student current = head;
        while (current.next != null) current = current.next;
        current.next = s;
    }

    public void addAtPosition(int position, Student s) {
        if (position <= 0 || head == null) {
            addAtBeginning(s);
            return;
        }
        Student current = head;
        for (int i = 0; i < position - 1 && current.next != null; i++) current = current.next;
        s.next = current.next;
        current.next = s;
    }

    public boolean deleteByRoll(int roll) {
        if (head == null) return false;
        if (head.rollNumber == roll) {
            head = head.next;
            return true;
        }
        Student prev = head;
        while (prev.next != null && prev.next.rollNumber != roll) prev = prev.next;
        if (prev.next == null) return false;
        prev.next = prev.next.next;
        return true;
    }

    public Student searchByRoll(int roll) {
        Student current = head;
        while (current != null) {
            if (current.rollNumber == roll) return current;
            current = current.next;
        }
        return null;
    }

    public boolean updateGrade(int roll, String grade) {
        Student s = searchByRoll(roll);
        if (s == null) return false;
        s.grade = grade;
        return true;
    }

    public void displayAll() {
        if (head == null) {
            System.out.println("No student records.");
            return;
        }
        Student current = head;
        while (current != null) {
            System.out.println(current);
            current = current.next;
        }
    }

    public static void main(String[] args) {
        StudentRecordManagement list = new StudentRecordManagement();
        list.addAtEnd(new Student(101, "Alice", 20, "A"));
        list.addAtBeginning(new Student(100, "Bob", 19, "B"));
        list.addAtPosition(2, new Student(105, "Charlie", 21, "C"));
        System.out.println("All records:");
        list.displayAll();
        System.out.println("Search roll 105: " + list.searchByRoll(105));
        System.out.println("Update grade of 105 to A+: " + list.updateGrade(105, "A+"));
        System.out.println("After update: " + list.searchByRoll(105));
        System.out.println("Delete roll 100: " + list.deleteByRoll(100));
        System.out.println("All records after delete:");
        list.displayAll();
    }
}
