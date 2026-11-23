import java.util.ArrayList;
import java.util.List;

public class RoundRobinScheduling {
    static class Process {
        int pid;
        int burstTime;
        int remaining;
        int priority;
        int completionTime;
        Process next;

        Process(int pid, int burstTime, int priority) {
            this.pid = pid;
            this.burstTime = burstTime;
            this.remaining = burstTime;
            this.priority = priority;
        }

        @Override
        public String toString() {
            return "P" + pid + "(burst=" + burstTime + ", remaining=" + remaining + ", priority=" + priority + ")";
        }
    }

    private Process head;
    private int count;

    public void addAtEnd(Process process) {
        if (head == null) {
            head = process;
            process.next = process;
        } else {
            Process last = head;
            while (last.next != head) last = last.next;
            last.next = process;
            process.next = head;
        }
        count++;
    }

    public boolean removeByPid(int pid) {
        if (head == null) return false;
        if (head.pid == pid && head.next == head) {
            head = null;
            count--;
            return true;
        }
        Process prev = head;
        while (prev.next != head && prev.next.pid != pid) prev = prev.next;
        if (prev.next == head) {
            if (head.pid != pid) return false;
            Process second = head.next;
            prev.next = second;
            head = second;
            count--;
            return true;
        }
        prev.next = prev.next.next;
        count--;
        return true;
    }

    public void display() {
        if (head == null) {
            System.out.println("[empty]");
            return;
        }
        Process node = head;
        do {
            System.out.print(node + " ");
            node = node.next;
        } while (node != head);
        System.out.println();
    }

    public void simulate(int quantum) {
        if (head == null) return;
        int elapsed = 0;
        List<Process> completed = new ArrayList<>();
        Process current = head;
        while (count > 0) {
            int slice = Math.min(quantum, current.remaining);
            elapsed += slice;
            current.remaining -= slice;
            System.out.print("Executed P" + current.pid + " for " + slice + " | Queue: ");
            display();
            if (current.remaining == 0) {
                current.completionTime = elapsed;
                completed.add(current);
                Process next = current.next;
                removeByPid(current.pid);
                current = next;
            } else {
                current = current.next;
            }
        }
        double totalWaiting = 0, totalTurnaround = 0;
        System.out.println("\nProcess statistics:");
        for (Process p : completed) {
            int turnaround = p.completionTime;
            int waiting = turnaround - p.burstTime;
            totalWaiting += waiting;
            totalTurnaround += turnaround;
            System.out.println("P" + p.pid + ": completion=" + p.completionTime
                    + ", turnaround=" + turnaround + ", waiting=" + waiting);
        }
        System.out.println("Average waiting time: " + (totalWaiting / completed.size()));
        System.out.println("Average turn-around time: " + (totalTurnaround / completed.size()));
    }

    public static void main(String[] args) {
        RoundRobinScheduling scheduler = new RoundRobinScheduling();
        scheduler.addAtEnd(new Process(1, 5, 2));
        scheduler.addAtEnd(new Process(2, 3, 1));
        scheduler.addAtEnd(new Process(3, 7, 3));
        System.out.println("Initial queue:");
        scheduler.display();
        System.out.println("Simulating with time quantum 2:");
        scheduler.simulate(2);
    }
}
