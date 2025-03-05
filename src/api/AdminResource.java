package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.List;

public class AdminResource {

    private static AdminResource instance;
    private CustomerService customerService;
    private ReservationService reservationService;

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

    public void displayAllReservation() {
        reservationService.printAllReservation();
    }
}
