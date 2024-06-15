import java.util.Scanner;

public class RailGuardian {
    // Global variables to track system states
    private static boolean login = false;
    private static boolean issue = false;
    private static boolean open = true;
    private static boolean train = false;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        // Main loop to display menu and get user input
        do {
            displayMenu();
            choice = scanner.nextInt();

            // Handling user choice
            switch (choice) {
                case 1 -> openBarrier();
                case 2 -> closeBarrier();
                case 3 -> checkBarrierStatus();
                case 4 -> setTrainApproaching();
                case 5 -> checkTrainApproaching();
                case 6 -> railwayCrossingAlert();
                case 7 -> checkTrackReport();
                case 8 -> setTrackIssue();
                case 9 -> login();
                case 10 -> logout();
                case 0 -> System.out.println(" Exit !");
                default -> System.out.println(" Invalid Input !");
            }

            System.out.println();
        } while (choice != 0);

        // Close scanner to avoid resource leak
        scanner.close();
    }

    // Function to display the main menu
    private static void displayMenu() {
        System.out.println("\t\t\t\tRAIL GUARDIAN");
        System.out.println("\t\t\t___________________________");
        System.out.println("\t\t\t 1. Open Barrier");
        System.out.println("\t\t\t 2. Close Barrier");
        System.out.println("\t\t\t 3. Check Barrier Status");
        System.out.println("\t\t\t 4. Set Train Approaching");
        System.out.println("\t\t\t 5. Check Train Approaching");
        System.out.println("\t\t\t 6. Railway Crossing Alert");
        System.out.println("\t\t\t 7. Check Track Report");
        System.out.println("\t\t\t 8. Set Track Issue");
        System.out.println("\t\t\t 9. Login");
        System.out.println("\t\t\t10. Logout");
        System.out.println("\t\t\t 0. Exit");
        System.out.print(" Enter your choice: ");
    }

    // Function to handle user login
    private static void login() {
        System.out.print(" Enter username: ");
        String name = scanner.next();
        System.out.print(" Enter password: ");
        String pass = scanner.next();

        if (name.equals("admin") && pass.equals("admin123")) {
            System.out.println(" Authentication successful!");
            login = true;
        } else {
            System.out.println(" Authentication failed. Access denied!");
        }
    }

    // Function to handle user logout
    private static void logout() {
        login = false;
        System.out.println(" \nYou are logged out !");
    }

    // Function to open the barrier
    private static void openBarrier() {
        if (login) {
            if (!train) {
                if (open) {
                    System.out.println(" Barriers are already opened!");
                } else {
                    open = true;
                    displaySignal(true);
                    System.out.println(" \nBarriers opened!");
                }
            } else {
                System.out.println(" Cannot open barrier. Train is approaching.");
            }
        } else {
            requestLogin();
        }
    }

    // Function to close the barrier
    private static void closeBarrier() {
        if (login) {
            if (!open) {
                System.out.println(" Barriers are already closed!");
            } else {
                open = false;
                displaySignal(false);
                System.out.println(" \nBarriers closed!");
            }
        } else {
            requestLogin();
        }
    }

    // Function to check the status of the barrier
    private static void checkBarrierStatus() {
        System.out.println(open ? " Barriers are open." : " Barriers are closed.");
    }

    // Function to set the train approaching status
    private static void setTrainApproaching() {
        System.out.println("\t\t\t 1. For train approaching ");
        System.out.println("\t\t\t 0. For train gone ");
        System.out.print(" Enter: ");
        boolean isApproaching = scanner.nextInt() == 1;
        train = isApproaching;

        if (train) {
            open = false;
            System.out.println(" Train is approaching ");
        } else {
            open = true;
            System.out.println(" Train is gone ");
        }

        displaySignal(open);
    }

    // Function to check if a train is approaching
    private static void checkTrainApproaching() {
        if (login) {
            if (train) {
                System.out.println(" Train is approaching ");
            } else if (open) {
                System.out.println(" Train is gone");
            } else {
                System.out.println(" Train is not approaching");
            }
        } else {
            requestLogin();
        }
    }

    // Function to handle railway crossing alert
    private static void railwayCrossingAlert() {
        if (login) {
            cameraAlert();
        } else {
            requestLogin();
        }
    }

    // Function to check the track report
    private static void checkTrackReport() {
        if (login) {
            System.out.println(issue ? " Track is not clear to go" : " Track is clear to go");
        } else {
            requestLogin();
        }
    }

    // Function to set the track issue status
    private static void setTrackIssue() {
        System.out.println("\t\t\t1. For track issue detection");
        System.out.println("\t\t\t0. For track issue resolve");
        System.out.print("Enter: ");
        int choice = scanner.nextInt();

        if (choice == 1) {
            issue = true;
            System.out.println(" Issue detected on track");
            trackMsg();
        } else if (choice == 0) {
            issue = false;
            System.out.println(" Track is clear for train");
        } else {
            System.out.println(" Invalid option!");
        }
    }

    // Helper function to request login if the user is not logged in
    private static void requestLogin() {
        System.out.println(" \nYou are not logged in");
        System.out.println(" Login First");
    }

    // Function to display the signal for opening/closing the barrier
    private static void displaySignal(boolean isOpen) {
        String signal = isOpen ? "GO" : "STOP";
        String light = isOpen ? "GREEN LIGHT" : "RED LIGHT";

        try {
            for (int i = 3; i > 0; i--) {
                System.out.println(i);
                Thread.sleep(1000);
            }
            System.out.println(signal);
            Thread.sleep(1000);
            System.out.println(light);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Function to handle the camera alert
    private static void cameraAlert() {
        System.out.print("Enter the alert message for train engineer: ");
        scanner.nextLine(); // Consume newline
        String message = scanner.nextLine();

        System.out.println("\n\t\t\t1. to send \tOR  Any key to cancel");
        System.out.print(" Enter: ");
        int choice = scanner.nextInt();

        if (choice == 1) {
            deliverAlert(message);
        } else {
            System.out.println(" Alert message canceled");
        }
    }

    // Function to display track messages
    private static void trackMsg() {
        String[] alerts = {
                "\t\t\t1. Heavy object reported on track",
                "\t\t\t2. Track has technical issue",
                "\t\t\t3. Tracks are disconnected",
                "\t\t\t4. Other train is changing the track"
        };

        for (String alert : alerts) {
            System.out.println(alert);
        }
        System.out.print(" Select the alert issue: ");
        int choice = scanner.nextInt();

        try {
            Thread.sleep(2000);
            System.out.print(".");
            Thread.sleep(2000);
            System.out.print(".");
            Thread.sleep(2000);
            System.out.print(".");
            Thread.sleep(2000);
            System.out.println(" Delivered!");
            System.out.println(" Delivered Track Alert:");
            System.out.println(" Stop the train because,");
            System.out.println(alerts[choice - 1]);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Function to deliver the alert message
    private static void deliverAlert(String message) {
        try {
        	
            for(int i=0;i<3;i++) {
        	Thread.sleep(1000);
            System.out.print(".");
            }
            
            System.out.println(" Delivered!");
            System.out.println(" Delivered ALERT:");
            System.out.println(" Alert sent to all nearest Train Engineers");
            System.out.println(" ALERT: Issue detected on the railway crossing.");
            System.out.println(" Message: " + message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
