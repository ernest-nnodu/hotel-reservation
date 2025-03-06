package ui;

import api.HotelResource;

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

    private void displayGreetingMessage() {
        String message = "WELCOME TO HOTEL RESERVATION APPLICATION";

        printLineSeperator();
        System.out.println(message);
        printLineSeperator();

    }

    private void displayMainMenu() {
        System.out.println("Main Menu");
        printLineSeperator();
        String menu = """
                1. Find and reserve a room
                2. See my reservations
                3. Create an account
                4. Admin
                5. Exit
                """;
        System.out.println(menu);
        printLineSeperator();
    }

    private void runAdminMenu() {
        adminMenu.start();
    }

    private void printLineSeperator() {
        System.out.println("=====================================================================");
    }
}
