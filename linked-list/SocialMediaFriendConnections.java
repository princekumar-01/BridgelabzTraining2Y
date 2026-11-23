import java.util.ArrayList;
import java.util.List;

public class SocialMediaFriendConnections {
    static class User {
        int userId;
        String name;
        int age;
        List<Integer> friends = new ArrayList<>();
        User next;

        User(int userId, String name, int age) {
            this.userId = userId;
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "ID=" + userId + ", Name=" + name + ", Age=" + age + ", Friends=" + friends;
        }
    }

    private User head;

    public void addUser(User user) {
        if (head == null) {
            head = user;
            return;
        }
        User current = head;
        while (current.next != null) current = current.next;
        current.next = user;
    }

    public User searchById(int id) {
        User current = head;
        while (current != null) {
            if (current.userId == id) return current;
            current = current.next;
        }
        return null;
    }

    public void searchByName(String name) {
        System.out.println("Users named '" + name + "':");
        User current = head;
        boolean found = false;
        while (current != null) {
            if (current.name.equalsIgnoreCase(name)) {
                System.out.println(current);
                found = true;
            }
            current = current.next;
        }
        if (!found) System.out.println("No users found.");
    }

    public boolean addFriendConnection(int id1, int id2) {
        User u1 = searchById(id1);
        User u2 = searchById(id2);
        if (u1 == null || u2 == null || id1 == id2) return false;
        if (!u1.friends.contains(id2)) u1.friends.add(id2);
        if (!u2.friends.contains(id1)) u2.friends.add(id1);
        return true;
    }

    public boolean removeFriendConnection(int id1, int id2) {
        User u1 = searchById(id1);
        User u2 = searchById(id2);
        if (u1 == null || u2 == null) return false;
        boolean removed = u1.friends.remove(Integer.valueOf(id2));
        removed |= u2.friends.remove(Integer.valueOf(id1));
        return removed;
    }

    public List<Integer> mutualFriends(int id1, int id2) {
        List<Integer> mutual = new ArrayList<>();
        User u1 = searchById(id1);
        User u2 = searchById(id2);
        if (u1 == null || u2 == null) return mutual;
        for (int friend : u1.friends) {
            if (u2.friends.contains(friend)) mutual.add(friend);
        }
        return mutual;
    }

    public void displayFriends(int id) {
        User user = searchById(id);
        System.out.println("Friends of user " + id + ": " + (user == null ? "user not found" : user.friends));
    }

    public int countFriends(int id) {
        User user = searchById(id);
        return user == null ? 0 : user.friends.size();
    }

    public void displayAllUsers() {
        User current = head;
        while (current != null) {
            System.out.println(current);
            current = current.next;
        }
    }

    public static void main(String[] args) {
        SocialMediaFriendConnections network = new SocialMediaFriendConnections();
        network.addUser(new User(1, "Alice", 22));
        network.addUser(new User(2, "Bob", 23));
        network.addUser(new User(3, "Charlie", 21));
        network.addUser(new User(4, "Diana", 24));
        network.addFriendConnection(1, 2);
        network.addFriendConnection(1, 3);
        network.addFriendConnection(2, 4);
        network.addFriendConnection(3, 4);
        network.displayAllUsers();
        network.displayFriends(1);
        System.out.println("Friend count for 1: " + network.countFriends(1));
        System.out.println("Mutual friends of 1 and 4: " + network.mutualFriends(1, 4));
        network.searchByName("Bob");
        System.out.println("Remove connection 1-3: " + network.removeFriendConnection(1, 3));
        network.displayFriends(1);
    }
}
