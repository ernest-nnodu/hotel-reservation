package ui;

import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainMenu {

    private static MainMenu instance;
    private final HotelResource hotelResource;
    private final AdminMenu adminMenu;
    private final Scanner scanner;

    private MainMenu() {
        hotelResource = HotelResource.getInstance();
        adminMenu = AdminMenu.getInstance();
        scanner = new Scanner(System.in);
    }

    public static MainMenu getInstance() {
        if (instance == null) {
            instance = new MainMenu();
        }
        return instance;
    }

    public void start() {
        displayGreetingMessage();
        runMainMenu();
    }

    private void runMainMenu() {
        boolean running = true;

        do {
            displayMainMenu();
            String userInput = scanner.nextLine();

            switch (userInput) {
                case "1":
                    processFindAndReserveRoomRequest();
                    break;
                case "2":
                    processSeeMyReservationRequest();
                    break;
                case "3":
                    processCreateAccountRequest();
                    break;
                case "4":
                    runAdminMenu();
                    break;
                case "5":
                    running = false;
                    break;
                default:
                    System.out.println("Error: invalid menu option provided");
            }
        } while (running);
    }

    private void processFindAndReserveRoomRequest() {
        Date checkInDate = getDate("Enter CheckIn Date mm/dd/yyyy");
        Date checkOutDate = getDate("Enter Checkout Date mm/dd/yyyy");

        List<IRoom> availableRooms = hotelResource.findRoom(checkInDate, checkOutDate);

        if (availableRooms.isEmpty()) {
            System.out.println("Sorry no available room within the specified dates");
            recommendedRooms(checkInDate, checkOutDate);
            return;
        }

        for (IRoom room : availableRooms) {
            System.out.println(room);
        }

        processRoomBooking(checkInDate, checkOutDate);
    }

    private void recommendedRooms(Date checkInDate, Date checkOutDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(checkInDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date futureCheckinDate = calendar.getTime();

        calendar.setTime(checkOutDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date futureCheckoutDate = calendar.getTime();

        List<IRoom> recommendedRooms = hotelResource.findRoom(futureCheckinDate, futureCheckoutDate);

        if (recommendedRooms.isEmpty()) {
            return;
        }

        System.out.println("These are rooms we recommend, and are available a week later" +
                " from your checkin and checkout dates");

        for (IRoom room : recommendedRooms) {
            System.out.println(room);
        }

        processRoomBooking(futureCheckinDate, futureCheckoutDate);
    }

    private void processRoomBooking(Date checkInDate, Date checkOutDate) {

        String response = getResponse("Would you like to book a room? y/n");

        if (response.equalsIgnoreCase("n")) {
            return;
        }

        response = getResponse("Do you have an account with us? y/n");

        if (response.equalsIgnoreCase("n")) {
            System.out.println("Please create an account before booking a room");
            return;
        }

        System.out.println("Enter email format: name@domain.com");
        String email = scanner.nextLine();

        System.out.println("What room number would you like to reserve:");
        String roomNumber = scanner.nextLine();
        IRoom room = hotelResource.getRoom(roomNumber);

        try {
            System.out.println(hotelResource.bookRoom(email, room, checkInDate, checkOutDate));
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private String getResponse(String prompt) {
        boolean validInput = false;
        String response = "";

        System.out.println(prompt);

        while (!validInput) {
            response = scanner.nextLine().trim();
            if (!(response.equalsIgnoreCase("y") || response.equalsIgnoreCase("n"))) {
                System.out.println("Please enter y or n");
                continue;
            }
            validInput = true;
        }
        return response;
    }

    private Date getDate(String prompt) {
        boolean validDate = false;
        Date date = null;

        while (!validDate) {
            System.out.println(prompt);

            date = createDate(scanner.nextLine().trim());
            if (date == null) {
                System.out.println("Wrong date format!");
                continue;
            }

            Date currentDate = new Date();
            if (date.before(currentDate)) {
                System.out.println("Past dates not allowed!");
                continue;
            }

            validDate = true;
        }
        return date;
    }

    private Date createDate(String date) {
        //Date formatting to ensure date format pattern is entered by user (www.google.com)
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateFormat.setLenient(false);
        try {
           return dateFormat.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    private void processSeeMyReservationRequest() {
        System.out.println("Enter Email format: name@domain.com");
        String email = scanner.nextLine().trim();
        List<Reservation> reservations = hotelResource.getCustomerReservations(email);

        if (reservations.isEmpty()) {
            System.out.println("Sorry there are no reservations for the customer provided!");
            return;
        }

        for (Reservation reservation : reservations) {
            System.out.println(reservation);
            System.out.println();
        }
    }

    private void processCreateAccountRequest() {
        String email = getNewCustomerEmail();
        String firstName = getNewCustomerName("Enter first name: ");
        String lastName = getNewCustomerName("Enter last name: ");

        try {
            hotelResource.createCustomer(email, firstName, lastName);
            System.out.println("Customer created!");
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private String getNewCustomerName(String prompt) {
        boolean validName = false;
        String name = "";

        while (!validName) {
            System.out.println(prompt);
            name = scanner.nextLine();
            if (name.isBlank()) {
                System.out.println("Name must not be empty");
                continue;
            }
            validName = true;
        }
        return name;
    }

    private String getNewCustomerEmail() {
        boolean validEmail = false;
        String email = "";

        while (!validEmail) {
            System.out.println("Enter email format: name@domain.com");
            email = scanner.nextLine().trim();

            if (email.isBlank()) {
                System.out.println("Email must not be empty");
                continue;
            }

            Customer exisitingCustomer = hotelResource.getCustomer(email);
            if (exisitingCustomer != null) {
                System.out.println("Customer with the same email already exist!");
                continue;
            }
            validEmail = true;
        }
        return email;
    }

    private void runAdminMenu() {
        adminMenu.start();
    }

    private void displayGreetingMessage() {
        String message = "WELCOME TO HOTEL RESERVATION APPLICATION";

        printLineSeperator();
        System.out.println(message);
        printLineSeperator();

    }

    private void displayMainMenu() {
        printLineSeperator();
        System.out.println("Main Menu");
        printLineSeperator();
        String menu = """
                1. Find and reserve a room
                2. See my reservations
                3. Create an account
                4. Admin
                5. Exit""";
        System.out.println(menu);
        printLineSeperator();
        System.out.println("Enter menu option number:");
    }

    private void printLineSeperator() {
        System.out.println("=====================================================================");
    }
}
