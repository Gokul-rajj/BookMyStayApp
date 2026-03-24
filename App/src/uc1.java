import java.util.*;

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Room Inventory
class RoomInventory {

    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory() {
        rooms.put("Single", 2);
        rooms.put("Double", 2);
        rooms.put("Suite", 1);
    }

    public void bookRoom(String roomType) throws InvalidBookingException {

        // Validate room type
        if (!rooms.containsKey(roomType)) {
            throw new InvalidBookingException("Error: Invalid room type selected.");
        }

        int available = rooms.get(roomType);

        // Validate availability
        if (available <= 0) {
            throw new InvalidBookingException("Error: No rooms available for type: " + roomType);
        }

        // Update inventory
        rooms.put(roomType, available - 1);

        System.out.println("Booking confirmed for room type: " + roomType);
    }
}

// Validator
class InvalidBookingValidator {

    public static void validateRoomType(String roomType) throws InvalidBookingException {
        if (roomType == null || roomType.trim().isEmpty()) {
            throw new InvalidBookingException("Error: Room type cannot be empty.");
        }
    }
}

// Main Class
class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        String[] bookingRequests = {"Single", "Suite", "Luxury", "Double"};

        for (String roomType : bookingRequests) {

            try {

                // Input validation
                InvalidBookingValidator.validateRoomType(roomType);

                // Process booking
                inventory.bookRoom(roomType);

            } catch (InvalidBookingException e) {

                // Graceful error handling
                System.out.println(e.getMessage());
            }
        }

        System.out.println("System continues running safely.");
    }
}