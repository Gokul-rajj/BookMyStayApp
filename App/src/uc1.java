/**
 * Book My Stay - Hotel Booking Management System
 * ------------------------------------------------
 * Use Case 4: Room Search & Availability Check
 *
 * This program demonstrates:
 * - Read-only access to inventory
 * - Filtering available rooms
 * - Separation of concerns (Search vs Inventory)
 * - Defensive programming (only valid rooms shown)
 *
 * @author YourName
 * @version 4.0
 */

import java.util.*;

// -------------------- ROOM DOMAIN --------------------

// Abstract Room class
abstract class Room {

    private String roomType;
    private int beds;
    private double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getBeds() {
        return beds;
    }

    public double getPrice() {
        return price;
    }

    public abstract void displayDetails();
}

// Concrete Room types
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 1000);
    }

    public void displayDetails() {
        System.out.println("Room: " + getRoomType());
        System.out.println("Beds: " + getBeds());
        System.out.println("Price: ₹" + getPrice());
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 2000);
    }

    public void displayDetails() {
        System.out.println("Room: " + getRoomType());
        System.out.println("Beds: " + getBeds());
        System.out.println("Price: ₹" + getPrice());
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 5000);
    }

    public void displayDetails() {
        System.out.println("Room: " + getRoomType());
        System.out.println("Beds: " + getBeds());
        System.out.println("Price: ₹" + getPrice());
    }
}


// -------------------- INVENTORY (READ-ONLY ACCESS) --------------------

class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 0); // unavailable (to test filtering)
        inventory.put("Suite Room", 2);
    }

    // Read-only method
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Return all room types
    public Set<String> getAllRoomTypes() {
        return inventory.keySet();
    }
}


// -------------------- SEARCH SERVICE --------------------

class RoomSearchService {

    public void searchAvailableRooms(RoomInventory inventory, List<Room> rooms) {

        System.out.println("---- Available Rooms ----\n");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            // Defensive check: show only available rooms
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available);
                System.out.println("---------------------------");
            }
        }
    }
}


// -------------------- MAIN CLASS --------------------

class UseCase4RoomSearch {

    public static void main(String[] args) {

        String appName = "Book My Stay - Hotel Booking System";
        String version = "Version 4.0";

        System.out.println("==========================================");
        System.out.println(" Welcome to " + appName);
        System.out.println(" " + version);
        System.out.println("==========================================\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Create room objects (domain model)
        List<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());

        // Search service (read-only operation)
        RoomSearchService searchService = new RoomSearchService();
        searchService.searchAvailableRooms(inventory, rooms);

        System.out.println("\nSearch completed. No inventory was modified.");
    }
}