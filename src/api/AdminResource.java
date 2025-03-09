package api;

import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;
import service.CustomerService;
import service.ReservationService;

import java.util.List;

public class AdminResource {

    private static AdminResource instance;
    private final CustomerService customerService;
    private final ReservationService reservationService;

    private AdminResource() {
        customerService = CustomerService.getInstance();
        reservationService = ReservationService.getInstance();
    }

    public static AdminResource getInstance() {
        if (instance == null) {
            instance = new AdminResource();
        }
        return instance;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void addRoom(IRoom room) {
        reservationService.addRoom(room);
    }

    public List<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public void loadTestData() {

        try {
            customerService.addCustomer("johndoe@example.com", "John", "Doe");
            customerService.addCustomer("alicesmith@example.com", "Alice", "Smith");
            customerService.addCustomer("mikejohnson@example.com", "Mike", "Johnson");

            reservationService.addRoom(new Room("101", 80.0, RoomType.SINGLE));
            reservationService.addRoom(new Room("102", 120.0, RoomType.DOUBLE));
            reservationService.addRoom(new Room("103", 85.0, RoomType.SINGLE));
            reservationService.addRoom(new Room("104", 80.0, RoomType.DOUBLE));
            reservationService.addRoom(new Room("105", 90.0, RoomType.SINGLE));
        } catch (IllegalArgumentException ex) {
            System.out.println("Error with loading data");
        }
    }

    public void displayAllReservation() {
        reservationService.printAllReservation();
    }
}
