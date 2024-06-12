import java.util.Scanner;

public class RailGuardian {
    private static boolean login = false;
    private static boolean issue = false;
    private static boolean Open = true;
    private static boolean train = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
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
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    if (login) {
                        if (!TrainApproaching()) {
                            if (Open) {
                                System.out.println(" Barriers are Already opened !");
                            } else {
                                openBarrier();
                            }
                        } else {
                            System.out.println(" Cannot open barrier. Train is approaching.");
                        }
                    } else {
                        System.out.println(" \nYou are not logged in");
                        System.out.println(" Login First");
                    }
                    break;
                case 2:
                    if (login) {
                        if (!Open) {
                            System.out.println(" Barriers are Already Closed");
                        } else {
                            closeBarrier();
                        }
                    } else {
                        System.out.println(" \nYou are not logged in");
                        System.out.println(" Login First");
                    }
                    break;
                case 3:
                    if (checkBarrier()) {
                        System.out.println(" Barriers are open.");
                    } else {
                        System.out.println(" Barriers are close.");
                    }
                    break;
                case 4:
                    boolean isApproaching;
                    System.out.println("\t\t\t 1. For train approaching ");
                    System.out.println("\t\t\t 0. For train gone ");
                    System.out.print(" Enter: ");
                    isApproaching = scanner.nextInt() == 1;
                    setTrainApproaching(isApproaching);
                    break;
                case 5:
                    if (login) {
                        if (TrainApproaching()) {
                            System.out.println(" Train is approaching ");
                        } else if (Open) {
                            System.out.println(" Train is gone");
                        } else {
                            System.out.println(" Train is not approaching");
                        }
                    } else {
                        System.out.println(" \nYou are not logged in");
                        System.out.println(" Login First");
                    }
                    break;
                case 6:
                    if (login) {
                        cameraAlert();
                    } else {
                        System.out.println(" \nYou are not logged in");
                        System.out.println(" Login First");
                    }
                    break;
                case 7:
                    if (login) {
                        trackReport();
                    } else {
                        System.out.println(" \nYou are not logged in");
                        System.out.println(" Login First");
                    }
                    break;
                case 8:
                    setTrackIssue();
                    break;
                case 9:
                    login();
                    break;
                case 10:
                    login = false;
                    System.out.println(" \nYou are logged out !");
                    break;
                case 0:
                    System.out.println(" Exit !");
                    break;
                default:
                    System.out.println(" Invalid Input !");
                    break;
            }

            System.out.println();
        } while (choice != 0);

        scanner.close();
    }

    private static void login() {
        Scanner scanner = new Scanner(System.in);
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

    private static void openBarrier() {
        Open = true;
        setSignal();
        System.out.println(" \nBarriers opened!");
    }

    private static void closeBarrier() {
        Open = false;
        setSignal();
        System.out.println(" \nBarriers closed!");
    }

    private static boolean checkBarrier() {
        return Open;
    }

    private static void setSignal() {
        if (Open) {
            try {
                Thread.sleep(1000);
                System.out.println(3);
                Thread.sleep(1000);
                System.out.println(2);
                Thread.sleep(1000);
                System.out.println(1);
                Thread.sleep(1000);
                System.out.println(" GO");
                Thread.sleep(1000);
                System.out.println(" GREEN LIGHT");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Thread.sleep(1000);
                System.out.println(3);
                Thread.sleep(1000);
                System.out.println(2);
                Thread.sleep(1000);
                System.out.println(1);
                Thread.sleep(1000);
                System.out.println(" STOP");
                Thread.sleep(1000);
                System.out.println(" RED LIGHT");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void setTrainApproaching(boolean Approaching) {
        train = Approaching;
        if (train) {
            Open = false;
            System.out.println(" Train is approaching ");
            display();
        } else {
            Open = true;
            System.out.println(" Train is gone ");
            display();
        }
    }

    private static boolean TrainApproaching() {
        return train;
    }

    private static void cameraAlert() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the alert message for train engineer: ");
        scanner.nextLine(); // Consume newline
        String i = scanner.nextLine();

        System.out.println("\n\t\t\t1. to send \tOR  Any key to cancel");
        System.out.print(" Enter: ");
        int s = scanner.nextInt();

        if (s == 1) {
            try {
                Thread.sleep(1000);
                System.out.print(".");
                Thread.sleep(1000);
                System.out.print(".");
                Thread.sleep(1000);
                System.out.print(".");
                Thread.sleep(1000);
                System.out.println(" Delivered !");
                System.out.println(" Delivered ALERT :");
                System.out.println(" Alert sent to all nearest Train Engineers");
                System.out.println(" ALERT: Issue detected on the railway crossing.");
                System.out.println(" Message: " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(" Alert Message Canceled");
        }
    }

    private static void trackMsg() {
        Scanner scanner = new Scanner(System.in);
        String[] a = {
            "\t\t\t1. Heavy object reported on track",
            "\t\t\t2. Track has technical issue",
            "\t\t\t3. Tracks are disconnected",
            "\t\t\t4. Other train is changing the track"
        };

        for (String s : a) {
            System.out.println(s);
        }
        System.out.print(" Select the Alert issue: ");
        int i = scanner.nextInt();

        try {
            Thread.sleep(2000);
            System.out.print(".");
            Thread.sleep(2000);
            System.out.print(".");
            Thread.sleep(2000);
            System.out.print(".");
            Thread.sleep(2000);
            System.out.println(" Delivered !");
            System.out.println(" Delivered Track Alert:");
            System.out.println(" Stop the train because,");
            System.out.println(a[i - 1]);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void setTrackIssue() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\t\t\t1. For track issue detection");
        System.out.println("\t\t\t0. For track issue resolve");
        System.out.print("Enter: ");
        int i = scanner.nextInt();
        if (i == 1) {
            issue = true;
            System.out.println(" Issue Detected on track");
            trackMsg();
        } else if (i == 0) {
            issue = false;
            System.out.println(" Track is clear for train");
        } else {
            System.out.println(" Invalid Option !");
        }
    }

    private static void trackReport() {
        if (issue) {
            System.out.println(" Track is not clear to go");
        } else {
            System.out.println(" Track is clear to go");
        }
    }

    private static void display() {
        System.out.println(" Railway crossing :");
        if (TrainApproaching()) {
            System.out.println(" Barriers are closing ");
            setSignal();
        } else {
            System.out.println(" Barriers are opening ");
            setSignal();
        }
    }
}
