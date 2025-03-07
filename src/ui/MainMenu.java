package ui;

import api.HotelResource;
import model.Customer;

import java.util.Scanner;

public class MainMenu {

    private static MainMenu instance;
    private HotelResource hotelResource;
    private AdminMenu adminMenu;
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
                    break;
                case "2":
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
