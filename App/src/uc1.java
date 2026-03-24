import java.io.*;
import java.util.*;

// Reservation class (Serializable for persistence)
class Reservation implements Serializable {
    String reservationId;
    String guestName;
    String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType;
    }
}

// System State class to store inventory and bookings
class SystemState implements Serializable {
    Map<String, Integer> inventory;
    List<Reservation> bookings;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    // Save system state
    public static void saveState(SystemState state) {
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving state.");
        }
    }

    // Load system state
    public static SystemState loadState() {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("System state restored from file.");
            return (SystemState) in.readObject();

        } catch (Exception e) {
            System.out.println("No previous state found. Starting fresh.");
            return null;
        }
    }
}

// Main Class
class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        // Try to restore state
        SystemState state = PersistenceService.loadState();

        Map<String, Integer> inventory;
        List<Reservation> bookings;

        if (state == null) {
            // Initialize new state
            inventory = new HashMap<>();
            inventory.put("Single", 2);
            inventory.put("Double", 2);

            bookings = new ArrayList<>();

            bookings.add(new Reservation("RES101", "Arun", "Single"));
            bookings.add(new Reservation("RES102", "Meena", "Double"));

            System.out.println("New system state created.");

        } else {
            inventory = state.inventory;
            bookings = state.bookings;
        }

        // Display recovered state
        System.out.println("Current Inventory: " + inventory);

        System.out.println("Booking History:");
        for (Reservation r : bookings) {
            System.out.println(r);
        }

        // Save state before shutdown
        PersistenceService.saveState(new SystemState(inventory, bookings));
    }
}