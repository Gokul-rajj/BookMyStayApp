/**
 * Book My Stay - Hotel Booking Management System
 * ------------------------------------------------
 * Use Case 3: Centralized Room Inventory Management
 *
 * This program demonstrates:
 * - Centralized inventory using HashMap
 * - Encapsulation of inventory logic
 * - O(1) lookup and updates
 * - Separation of concerns (Room vs Inventory)
 *
 * @author YourName
 * @version 3.1
 */

import java.util.HashMap;
import java.util.Map;

// Inventory class (Single Source of Truth)
class RoomInventory {

    // HashMap to store room type and availability
    private Map<String, Integer> inventory;

    // Constructor to initialize inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        // Initial room availability
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Get availability of a room type
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability (controlled modification)
    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Display full inventory
    public void displayInventory() {
        System.out.println("---- Current Room Inventory ----");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}


// Main class (Entry Point)
class UseCase3InventorySetup {

    public static void main(String[] args) {

        // Application info
        String appName = "Book My Stay - Hotel Booking System";
        String version = "Version 3.1";

        System.out.println("==========================================");
        System.out.println(" Welcome to " + appName);
        System.out.println(" " + version);
        System.out.println("==========================================\n");

        // Initialize inventory (centralized)
        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        // Demonstrate retrieval
        System.out.println("\nAvailable Single Rooms: " +
                inventory.getAvailability("Single Room"));

        // Demonstrate update
        System.out.println("\nUpdating Single Room availability...");
        inventory.updateAvailability("Single Room", 4);

        // Display updated inventory
        System.out.println();
        inventory.displayInventory();

        System.out.println("\nApplication terminated successfully!");
    }
}