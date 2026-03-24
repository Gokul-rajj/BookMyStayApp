import java.util.*;

// Reservation class
class Reservation {
    String reservationId;
    String roomType;
    String roomId;
    boolean active;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
        this.active = true;
    }
}

// Inventory Manager
class RoomInventory {

    Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public void increaseInventory(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void displayInventory() {
        System.out.println("Current Inventory: " + inventory);
    }
}

// Cancellation Service
class CancellationService {

    Stack<String> rollbackStack = new Stack<>();

    public void cancelReservation(String reservationId,
                                  Map<String, Reservation> reservations,
                                  RoomInventory inventory) {

        if (!reservations.containsKey(reservationId)) {
            System.out.println("Error: Reservation does not exist.");
            return;
        }

        Reservation r = reservations.get(reservationId);

        if (!r.active) {
            System.out.println("Error: Reservation already cancelled.");
            return;
        }

        // Record room id in rollback stack
        rollbackStack.push(r.roomId);

        // Restore inventory
        inventory.increaseInventory(r.roomType);

        // Update reservation status
        r.active = false;

        System.out.println("Reservation " + reservationId + " cancelled.");
        System.out.println("Released Room ID: " + rollbackStack.peek());
    }
}

// Main Class

class UseCase10BookingCancellation {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        Map<String, Reservation> reservations = new HashMap<>();

        // Simulate confirmed bookings
        reservations.put("RES101", new Reservation("RES101", "Single", "S1"));
        reservations.put("RES102", new Reservation("RES102", "Double", "D1"));

        CancellationService cancelService = new CancellationService();

        // Cancel booking
        cancelService.cancelReservation("RES101", reservations, inventory);

        // Attempt invalid cancellation
        cancelService.cancelReservation("RES101", reservations, inventory);

        // Attempt cancellation of non-existent booking
        cancelService.cancelReservation("RES999", reservations, inventory);

        // Display updated inventory
        inventory.displayInventory();
    }
}