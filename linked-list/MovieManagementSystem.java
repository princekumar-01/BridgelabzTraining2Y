public class MovieManagementSystem {
    static class Movie {
        String title;
        String director;
        int year;
        double rating;
        Movie prev;
        Movie next;

        Movie(String title, String director, int year, double rating) {
            this.title = title;
            this.director = director;
            this.year = year;
            this.rating = rating;
        }

        @Override
        public String toString() {
            return "Title=" + title + ", Director=" + director + ", Year=" + year + ", Rating=" + rating;
        }
    }

    private Movie head;
    private Movie tail;

    public void addAtBeginning(Movie m) {
        if (head == null) {
            head = tail = m;
            return;
        }
        m.next = head;
        head.prev = m;
        head = m;
    }

    public void addAtEnd(Movie m) {
        if (tail == null) {
            head = tail = m;
            return;
        }
        tail.next = m;
        m.prev = tail;
        tail = m;
    }

    public void addAtPosition(int position, Movie m) {
        if (position <= 0) {
            addAtBeginning(m);
            return;
        }
        Movie current = head;
        for (int i = 0; i < position && current != null; i++) current = current.next;
        if (current == null) {
            addAtEnd(m);
            return;
        }
        Movie prev = current.prev;
        m.prev = prev;
        m.next = current;
        prev.next = m;
        current.prev = m;
    }

    public boolean removeByTitle(String title) {
        Movie current = head;
        while (current != null) {
            if (current.title.equals(title)) {
                if (current.prev != null) current.prev.next = current.next;
                else head = current.next;
                if (current.next != null) current.next.prev = current.prev;
                else tail = current.prev;
                current.prev = null;
                current.next = null;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void searchByDirector(String director) {
        System.out.println("Movies by director '" + director + "':");
        Movie current = head;
        boolean found = false;
        while (current != null) {
            if (current.director.equals(director)) {
                System.out.println(current);
                found = true;
            }
            current = current.next;
        }
        if (!found) System.out.println("No movies found.");
    }

    public void searchByRating(double rating) {
        System.out.println("Movies with rating " + rating + ":");
        Movie current = head;
        boolean found = false;
        while (current != null) {
            if (current.rating == rating) {
                System.out.println(current);
                found = true;
            }
            current = current.next;
        }
        if (!found) System.out.println("No movies found.");
    }

    public boolean updateRating(String title, double rating) {
        Movie current = head;
        while (current != null) {
            if (current.title.equals(title)) {
                current.rating = rating;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void displayForward() {
        System.out.print("Forward:  ");
        Movie current = head;
        while (current != null) {
            System.out.print("[" + current.title + "] ");
            current = current.next;
        }
        System.out.println();
    }

    public void displayReverse() {
        System.out.print("Reverse:  ");
        Movie current = tail;
        while (current != null) {
            System.out.print("[" + current.title + "] ");
            current = current.prev;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        MovieManagementSystem list = new MovieManagementSystem();
        list.addAtEnd(new Movie("Inception", "Nolan", 2010, 8.8));
        list.addAtBeginning(new Movie("Interstellar", "Nolan", 2014, 8.7));
        list.addAtPosition(1, new Movie("The Matrix", "Wachowski", 1999, 8.7));
        list.displayForward();
        list.displayReverse();
        list.searchByDirector("Nolan");
        list.searchByRating(8.7);
        System.out.println("Update rating: " + list.updateRating("Inception", 9.0));
        list.displayForward();
        System.out.println("Remove 'The Matrix': " + list.removeByTitle("The Matrix"));
        list.displayForward();
    }
}
