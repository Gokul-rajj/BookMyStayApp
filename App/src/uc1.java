/**
 * Book My Stay - Hotel Booking Management System
 * ------------------------------------------------
 * Use Case 2: Basic Room Types & Static Availability
 *
 * This program demonstrates:
 * - Abstract class (Room)
 * - Inheritance (SingleRoom, DoubleRoom, SuiteRoom)
 * - Polymorphism (Room reference)
 * - Encapsulation (private fields + getters)
 * - Static availability using variables
 *
 * @author YourName
 * @version 2.1
 */

// Abstract class
abstract class Room {

    // Encapsulated attributes
    private String roomType;
    private int numberOfBeds;
    private double price;

    // Constructor
    public Room(String roomType, int numberOfBeds, double price) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.price = price;
    }

    // Getters
    public String getRoomType() {
        return roomType;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public double getPrice() {
        return price;
    }

    // Abstract method (can be extended later)
    public abstract void displayRoomDetails();
}


// Single Room class
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 1000.0);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("Room Type: " + getRoomType());
        System.out.println("Beds: " + getNumberOfBeds());
        System.out.println("Price: ₹" + getPrice());
    }
}


// Double Room class
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 2000.0);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("Room Type: " + getRoomType());
        System.out.println("Beds: " + getNumberOfBeds());
        System.out.println("Price: ₹" + getPrice());
    }
}


// Suite Room class
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 5000.0);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("Room Type: " + getRoomType());
        System.out.println("Beds: " + getNumberOfBeds());
        System.out.println("Price: ₹" + getPrice());
    }
}


// Main class (Entry Point)
class UseCase2RoomInitialization {

    public static void main(String[] args) {

        // Application info
        String appName = "Book My Stay - Hotel Booking System";
        String version = "Version 2.1";

        System.out.println("==========================================");
        System.out.println(" Welcome to " + appName);
        System.out.println(" " + version);
        System.out.println("==========================================\n");

        // Create room objects (Polymorphism)
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Static availability (simple variables)
        int singleRoomAvailability = 5;
        int doubleRoomAvailability = 3;
        int suiteRoomAvailability = 2;

        // Display details
        System.out.println("---- Room Details & Availability ----\n");

        singleRoom.displayRoomDetails();
        System.out.println("Available: " + singleRoomAvailability + "\n");

        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleRoomAvailability + "\n");

        suiteRoom.displayRoomDetails();
        System.out.println("Available: " + suiteRoomAvailability + "\n");

        System.out.println("Application terminated successfully!");
    }
}