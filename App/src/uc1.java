import java.util.*;

// Booking Request
class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Shared Inventory
class RoomInventory {

    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory() {
        rooms.put("Single", 2);
        rooms.put("Double", 2);
    }

    // Critical section protected by synchronization
    public synchronized void allocateRoom(BookingRequest request) {

        int available = rooms.getOrDefault(request.roomType, 0);

        if (available > 0) {
            rooms.put(request.roomType, available - 1);

            System.out.println(Thread.currentThread().getName() +
                    " allocated " + request.roomType +
                    " room to " + request.guestName);
        } else {
            System.out.println(Thread.currentThread().getName() +
                    " failed booking for " + request.guestName +
                    " (No " + request.roomType + " rooms available)");
        }
    }

    public void displayInventory() {
        System.out.println("Final Inventory: " + rooms);
    }
}

// Concurrent Booking Processor
class BookingProcessor implements Runnable {

    private Queue<BookingRequest> bookingQueue;
    private RoomInventory inventory;

    public BookingProcessor(Queue<BookingRequest> bookingQueue, RoomInventory inventory) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        while (true) {

            BookingRequest request;

            // synchronized queue access
            synchronized (bookingQueue) {
                if (bookingQueue.isEmpty())
                    break;

                request = bookingQueue.poll();
            }

            if (request != null) {
                inventory.allocateRoom(request);
            }
        }
    }
}

// Main Class
class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        Queue<BookingRequest> bookingQueue = new LinkedList<>();
        RoomInventory inventory = new RoomInventory();

        // Simulated booking requests
        bookingQueue.add(new BookingRequest("Arun", "Single"));
        bookingQueue.add(new BookingRequest("Meena", "Single"));
        bookingQueue.add(new BookingRequest("Karthik", "Single"));
        bookingQueue.add(new BookingRequest("Divya", "Double"));
        bookingQueue.add(new BookingRequest("Rahul", "Double"));

        // Multiple threads (guests)
        Thread t1 = new Thread(new BookingProcessor(bookingQueue, inventory), "Thread-1");
        Thread t2 = new Thread(new BookingProcessor(bookingQueue, inventory), "Thread-2");
        Thread t3 = new Thread(new BookingProcessor(bookingQueue, inventory), "Thread-3");

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inventory.displayInventory();
    }
}