package ui;

import api.AdminResource;

import java.util.Scanner;

public class AdminMenu {

    private static AdminMenu instance;
    private AdminResource adminResource;
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
            System.out.println("Admin Menu");
            displayAdminMenu();
            String userInput = scanner.nextLine();

            switch (userInput) {
                case "1":
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "4":
                    break;
                case "5":
                    running = false;
                    break;
                default:
                    System.out.println("Error: wrong menu option provide");
            }
        } while (running);
    }

    private void displayAdminMenu() {
        String menu = """
                1. See all Customers
                2. See all Rooms
                3. See all Reservations
                4. Add a Room
                5. Back to Main Menu
                """;
        displayLineSeperator();
        System.out.println(menu);
        displayLineSeperator();
    }

    private void displayLineSeperator() {
        System.out.println("=====================================================================");
    }
}
