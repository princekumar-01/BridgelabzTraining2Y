public class TicketReservationSystem {
    static class Ticket {
        int ticketId;
        String customerName;
        String movieName;
        String seatNumber;
        String bookingTime;
        Ticket next;

        Ticket(int ticketId, String customerName, String movieName, String seatNumber, String bookingTime) {
            this.ticketId = ticketId;
            this.customerName = customerName;
            this.movieName = movieName;
            this.seatNumber = seatNumber;
            this.bookingTime = bookingTime;
        }

        @Override
        public String toString() {
            return "Ticket=" + ticketId + ", Customer=" + customerName + ", Movie=" + movieName
                    + ", Seat=" + seatNumber + ", Time=" + bookingTime;
        }
    }

    private Ticket head;

    public void addAtEnd(Ticket ticket) {
        if (head == null) {
            head = ticket;
            ticket.next = ticket;
            return;
        }
        Ticket last = head;
        while (last.next != head) last = last.next;
        last.next = ticket;
        ticket.next = head;
    }

    public boolean removeById(int id) {
        if (head == null) return false;
        if (head.ticketId == id && head.next == head) {
            head = null;
            return true;
        }
        Ticket prev = head;
        while (prev.next != head && prev.next.ticketId != id) prev = prev.next;
        if (prev.next == head) {
            if (head.ticketId != id) return false;
            Ticket second = head.next;
            prev.next = second;
            head = second;
            return true;
        }
        prev.next = prev.next.next;
        return true;
    }

    public void displayCurrentTickets() {
        if (head == null) {
            System.out.println("No booked tickets.");
            return;
        }
        Ticket node = head;
        do {
            System.out.println(node);
            node = node.next;
        } while (node != head);
    }

    public void searchByCustomer(String customer) {
        System.out.println("Tickets for customer '" + customer + "':");
        search(t -> t.customerName.equalsIgnoreCase(customer));
    }

    public void searchByMovie(String movie) {
        System.out.println("Tickets for movie '" + movie + "':");
        search(t -> t.movieName.equalsIgnoreCase(movie));
    }

    private void search(java.util.function.Predicate<Ticket> predicate) {
        if (head == null) return;
        Ticket node = head;
        boolean found = false;
        do {
            if (predicate.test(node)) {
                System.out.println(node);
                found = true;
            }
            node = node.next;
        } while (node != head);
        if (!found) System.out.println("No tickets found.");
    }

    public int count() {
        if (head == null) return 0;
        int count = 0;
        Ticket node = head;
        do {
            count++;
            node = node.next;
        } while (node != head);
        return count;
    }

    public static void main(String[] args) {
        TicketReservationSystem system = new TicketReservationSystem();
        system.addAtEnd(new Ticket(1, "Alice", "Inception", "A1", "10:00"));
        system.addAtEnd(new Ticket(2, "Bob", "Interstellar", "B2", "11:30"));
        system.addAtEnd(new Ticket(3, "Alice", "The Matrix", "C3", "14:00"));
        System.out.println("Current tickets:");
        system.displayCurrentTickets();
        System.out.println("Total booked tickets: " + system.count());
        system.searchByCustomer("Alice");
        system.searchByMovie("Interstellar");
        System.out.println("Remove ticket 2: " + system.removeById(2));
        System.out.println("Total booked tickets now: " + system.count());
        system.displayCurrentTickets();
    }
}
