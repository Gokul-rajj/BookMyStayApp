/**
 * Book My Stay - Hotel Booking Management System
 * ------------------------------------------------
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * This program demonstrates:
 * - FIFO queue processing
 * - Unique room allocation using Set
 * - HashMap for mapping room types to assigned room IDs
 * - Inventory synchronization
 * - Prevention of double-booking
 *
 * @author YourName
 * @version 6.0
 */

import java.util.*;

// -------------------- RESERVATION --------------------

class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}


// -------------------- BOOKING REQUEST QUEUE --------------------

class BookingRequestQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
    }

    public Reservation getNextRequest() {
        return queue.poll(); // FIFO
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}


// -------------------- INVENTORY SERVICE --------------------

class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void reduceAvailability(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\n---- Updated Inventory ----");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}


// -------------------- BOOKING SERVICE --------------------

class BookingService {

    private InventoryService inventoryService;

    // Map room type -> set of allocated room IDs
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();

    // Global set to ensure uniqueness
    private Set<String> allAllocatedRoomIds = new HashSet<>();

    public BookingService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // Generate unique room ID
    private String generateRoomId(String roomType) {
        return roomType.replace(" ", "").substring(0, 3).toUpperCase()
                + "-" + UUID.randomUUID().toString().substring(0, 5);
    }

    // Process booking request
    public void processReservation(Reservation reservation) {

        String roomType = reservation.getRoomType();

        // Check availability
        if (inventoryService.getAvailability(roomType) <= 0) {
            System.out.println("Booking failed for " + reservation.getGuestName()
                    + " (No rooms available)");
            return;
        }

        // Generate unique room ID
        String roomId;
        do {
            roomId = generateRoomId(roomType);
        } while (allAllocatedRoomIds.contains(roomId));

        // Store in global set
        allAllocatedRoomIds.add(roomId);

        // Map room type to allocated IDs
        allocatedRooms.putIfAbsent(roomType, new HashSet<>());
        allocatedRooms.get(roomType).add(roomId);

        // Reduce inventory immediately
        inventoryService.reduceAvailability(roomType);

        // Confirm reservation
        System.out.println("Booking Confirmed!");
        System.out.println("Guest: " + reservation.getGuestName());
        System.out.println("Room Type: " + roomType);
        System.out.println("Allocated Room ID: " + roomId);
        System.out.println("-----------------------------");
    }
}


// -------------------- MAIN CLASS --------------------

class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        String appName = "Book My Stay - Hotel Booking System";
        String version = "Version 6.0";

        System.out.println("==========================================");
        System.out.println(" Welcome to " + appName);
        System.out.println(" " + version);
        System.out.println("==========================================\n");

        // Initialize services
        BookingRequestQueue queue = new BookingRequestQueue();
        InventoryService inventory = new InventoryService();
        BookingService bookingService = new BookingService(inventory);

        // Add booking requests (FIFO)
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Single Room"));
        queue.addRequest(new Reservation("Charlie", "Single Room")); // should fail
        queue.addRequest(new Reservation("David", "Suite Room"));

        // Process queue
        while (!queue.isEmpty()) {
            Reservation r = queue.getNextRequest();
            bookingService.processReservation(r);
        }

        // Show updated inventory
        inventory.displayInventory();

        System.out.println("\nAll bookings processed safely without double-booking.");
    }
}