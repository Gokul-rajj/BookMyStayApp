import java.util.*;

// Reservation class
class Reservation {
    String reservationId;
    String guestName;
    String roomType;
    double price;

    public Reservation(String reservationId, String guestName, String roomType, double price) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.price = price;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }
}

// Booking History (stores confirmed bookings)
class BookingHistory {

    // List to maintain insertion order
    private List<Reservation> reservations = new ArrayList<>();

    public void addReservation(Reservation r) {
        reservations.add(r);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}

// Booking Report Service
class BookingReportService {

    public void generateReport(List<Reservation> reservations) {

        System.out.println("---- Booking History Report ----");

        double totalRevenue = 0;

        for (Reservation r : reservations) {
            System.out.println(
                    r.getReservationId() + " | " +
                            r.getGuestName() + " | " +
                            r.getRoomType() + " | ₹" +
                            r.getPrice()
            );

            totalRevenue += r.getPrice();
        }

        System.out.println("-------------------------------");
        System.out.println("Total Bookings: " + reservations.size());
        System.out.println("Total Revenue: ₹" + totalRevenue);
    }
}

// Main Class
class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulate confirmed bookings
        history.addReservation(new Reservation("RES101", "Arun", "Single Room", 2000));
        history.addReservation(new Reservation("RES102", "Meena", "Double Room", 3500));
        history.addReservation(new Reservation("RES103", "Karthik", "Suite", 6000));

        // Admin requests report
        reportService.generateReport(history.getReservations());
    }
}