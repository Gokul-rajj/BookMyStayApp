import java.util.*;

// Add-On Service class
class AddOnService {
    String serviceName;
    double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public String getServiceName() {
        return serviceName;
    }
}

// Add-On Service Manager
class AddOnServiceManager {

    // Map ReservationID -> List of Services
    private Map<String, List<AddOnService>> reservationServices = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {
        reservationServices
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    // Get services for reservation
    public List<AddOnService> getServices(String reservationId) {
        return reservationServices.getOrDefault(reservationId, new ArrayList<>());
    }

    // Calculate additional cost
    public double calculateTotalServiceCost(String reservationId) {
        double total = 0;

        List<AddOnService> services = reservationServices.get(reservationId);

        if (services != null) {
            for (AddOnService service : services) {
                total += service.getCost();
            }
        }

        return total;
    }
}

// Main Class
class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId = "RES101";

        // Guest selects services
        manager.addService(reservationId, new AddOnService("Breakfast", 500));
        manager.addService(reservationId, new AddOnService("Airport Pickup", 1200));
        manager.addService(reservationId, new AddOnService("Spa", 1500));

        // Display selected services
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Selected Add-On Services:");

        for (AddOnService service : manager.getServices(reservationId)) {
            System.out.println(service.getServiceName() + " - ₹" + service.getCost());
        }

        // Calculate total additional cost
        double totalCost = manager.calculateTotalServiceCost(reservationId);

        System.out.println("Total Additional Cost: ₹" + totalCost);
    }
}