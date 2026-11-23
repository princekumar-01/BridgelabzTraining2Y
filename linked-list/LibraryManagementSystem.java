public class LibraryManagementSystem {
    static class Book {
        String title;
        String author;
        String genre;
        int bookId;
        boolean available;
        Book prev;
        Book next;

        Book(String title, String author, String genre, int bookId, boolean available) {
            this.title = title;
            this.author = author;
            this.genre = genre;
            this.bookId = bookId;
            this.available = available;
        }

        @Override
        public String toString() {
            return "ID=" + bookId + ", Title=" + title + ", Author=" + author
                    + ", Genre=" + genre + ", Available=" + available;
        }
    }

    private Book head;
    private Book tail;
    private int count;

    public void addAtBeginning(Book book) {
        if (head == null) {
            head = tail = book;
        } else {
            book.next = head;
            head.prev = book;
            head = book;
        }
        count++;
    }

    public void addAtEnd(Book book) {
        if (tail == null) {
            head = tail = book;
        } else {
            tail.next = book;
            book.prev = tail;
            tail = book;
        }
        count++;
    }

    public void addAtPosition(int position, Book book) {
        if (position <= 0) {
            addAtBeginning(book);
            return;
        }
        Book current = head;
        for (int i = 0; i < position && current != null; i++) current = current.next;
        if (current == null) {
            addAtEnd(book);
            return;
        }
        Book prev = current.prev;
        book.prev = prev;
        book.next = current;
        prev.next = book;
        current.prev = book;
        count++;
    }

    public boolean removeById(int id) {
        Book current = head;
        while (current != null) {
            if (current.bookId == id) {
                if (current.prev != null) current.prev.next = current.next;
                else head = current.next;
                if (current.next != null) current.next.prev = current.prev;
                else tail = current.prev;
                current.prev = null;
                current.next = null;
                count--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void searchByTitle(String title) {
        System.out.println("Books with title '" + title + "':");
        Book current = head;
        boolean found = false;
        while (current != null) {
            if (current.title.equalsIgnoreCase(title)) {
                System.out.println(current);
                found = true;
            }
            current = current.next;
        }
        if (!found) System.out.println("No books found.");
    }

    public void searchByAuthor(String author) {
        System.out.println("Books by author '" + author + "':");
        Book current = head;
        boolean found = false;
        while (current != null) {
            if (current.author.equalsIgnoreCase(author)) {
                System.out.println(current);
                found = true;
            }
            current = current.next;
        }
        if (!found) System.out.println("No books found.");
    }

    public boolean updateAvailability(int id, boolean available) {
        Book current = head;
        while (current != null) {
            if (current.bookId == id) {
                current.available = available;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void displayForward() {
        System.out.print("Forward:  ");
        Book current = head;
        while (current != null) {
            System.out.print("[ID " + current.bookId + "] ");
            current = current.next;
        }
        System.out.println();
    }

    public void displayReverse() {
        System.out.print("Reverse:  ");
        Book current = tail;
        while (current != null) {
            System.out.print("[ID " + current.bookId + "] ");
            current = current.prev;
        }
        System.out.println();
    }

    public int countBooks() {
        return count;
    }

    public static void main(String[] args) {
        LibraryManagementSystem library = new LibraryManagementSystem();
        library.addAtEnd(new Book("1984", "Orwell", "Dystopian", 1, true));
        library.addAtBeginning(new Book("The Hobbit", "Tolkien", "Fantasy", 2, false));
        library.addAtPosition(1, new Book("Dune", "Herbert", "Sci-Fi", 3, true));
        library.displayForward();
        library.displayReverse();
        System.out.println("Total books: " + library.countBooks());
        library.searchByTitle("Dune");
        library.searchByAuthor("Orwell");
        System.out.println("Update availability of ID 1 to false: " + library.updateAvailability(1, false));
        library.displayForward();
        System.out.println("Remove ID 2: " + library.removeById(2));
        System.out.println("Total books now: " + library.countBooks());
        library.displayForward();
    }
}
