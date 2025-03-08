package ui;

import api.AdminResource;
import model.*;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    private static AdminMenu instance;
    private final AdminResource adminResource;
    private final Scanner scanner;

    private AdminMenu() {
        adminResource = AdminResource.getInstance();
        scanner = new Scanner(System.in);
    }

    public static AdminMenu getInstance() {
        if (instance == null) {
            instance = new AdminMenu();
        }
        return instance;
    }

    public void start() {
        runAdminMenu();
    }

    private void runAdminMenu() {
        boolean running = true;

        do {
            displayAdminMenu();
            String userInput = scanner.nextLine();

            switch (userInput) {
                case "1":
                    processSeeAllCustomerRequest();
                    break;
                case "2":
                    processSeeAllRoomRequest();
                    break;
                case "3":
                    processSeeAllReservationRequest();
                    break;
                case "4":
                    processAddRoomRequest();
                    break;
                case "5":
                    addTestData();
                    break;
                case "6":
                    running = false;
                    break;
                default:
                    System.out.println("Error: invalid menu option provided");
            }
        } while (running);
    }

    private void processSeeAllCustomerRequest() {
        List<Customer> customers = adminResource.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("No customer available");
            return;
        }

        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    private void processSeeAllRoomRequest() {
        List<IRoom> rooms = adminResource.getAllRooms();

        if (rooms.isEmpty()) {
            System.out.println("No rooms available");
            return;
        }
        for (IRoom room : rooms) {
            System.out.println(room);
        }
    }

    private void processSeeAllReservationRequest() {
        adminResource.displayAllReservation();
    }

    private void processAddRoomRequest() {
        String roomNumber = getRoomNumber();
        double price = getRoomPrice();
        RoomType roomType = getRoomType();

        IRoom newRoom = new Room(roomNumber, price, roomType);
        try {
            adminResource.addRoom(newRoom);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private RoomType getRoomType() {
        boolean validRoomType = false;
        String roomType =  null;

        System.out.println("Enter room type, 1 for single and 2 for double:");

        while (!validRoomType) {
            roomType = scanner.nextLine().trim();
            if (!(roomType.equals("1") || roomType.equals("2"))) {
                System.out.println("Please enter 1 or 2");
                continue;
            }
            validRoomType = true;
        }
        return roomType.equals("1") ? RoomType.SINGLE : RoomType.DOUBLE;
    }

    private double getRoomPrice() {
        boolean validPrice = false;
        double price = 0.0;

        System.out.println("Enter price per night:");

        while (!validPrice) {
            try {
                price = Double.parseDouble(scanner.nextLine().trim());
            } catch (Exception ex) {
                System.out.println("Please enter a numerical value");
                continue;
            }
            validPrice = true;
        }
        return price;
    }

    private String getRoomNumber() {
        boolean validRoomNumber = false;
        String roomNumber = "";

        while (!validRoomNumber) {
            System.out.println("Enter room number:");
            roomNumber = scanner.nextLine().trim();

            if (roomNumber.isBlank()) {
                System.out.println("Room number must be provided");
                continue;
            }
            validRoomNumber = true;
        }
        return roomNumber;
    }

    private void addTestData() {
        adminResource.loadTestData();
    }

    private void displayAdminMenu() {
        String menu = """
                1. See all Customers
                2. See all Rooms
                3. See all Reservations
                4. Add a Room
                5. Add test data
                6. Back to Main Menu""";
        displayLineSeperator();
        System.out.println("Admin Menu");
        displayLineSeperator();
        System.out.println(menu);
        displayLineSeperator();
        System.out.println("Enter a menu option number:");
    }

    private void displayLineSeperator() {
        System.out.println("=====================================================================");
    }
}
