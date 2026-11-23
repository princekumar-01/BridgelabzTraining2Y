import java.util.ArrayDeque;
import java.util.Queue;

public class CircularTourProblem {
    static class Pump {
        int petrol;
        int distance;

        Pump(int petrol, int distance) {
            this.petrol = petrol;
            this.distance = distance;
        }
    }

    public static int findStart(Pump[] pumps) {
        Queue<Integer> queue = new ArrayDeque<>();
        int surplus = 0;
        int start = 0;
        int count = 0;
        while (count < pumps.length * 2) {
            int idx = (start + count) % pumps.length;
            Pump p = pumps[idx];
            surplus += p.petrol - p.distance;
            if (surplus >= 0) {
                queue.offer(idx);
                count++;
            } else {
                if (queue.isEmpty()) {
                    start = idx + 1;
                    surplus = 0;
                } else {
                    int removed = queue.poll();
                    surplus -= pumps[removed].petrol - pumps[removed].distance;
                    start = removed + 1;
                    count--;
                }
            }
            if (queue.size() == pumps.length) return start % pumps.length;
        }
        return -1;
    }

    public static void main(String[] args) {
        Pump[] pumps = {
            new Pump(4, 6),
            new Pump(6, 5),
            new Pump(7, 3),
            new Pump(4, 5)
        };
        int start = findStart(pumps);
        System.out.println("Starting pump index: " + start);
    }
}
