import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RailGuardianGUI {
    // Global variables to track system states
    private static boolean login = false;
    private static boolean issue = false;
    private static boolean open = true;
    private static boolean train = false;

    private static JFrame loginFrame;
    private static JFrame menuFrame;
    private static JTextField usernameField;
    private static JPasswordField passwordField;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RailGuardianGUI::createLoginFrame);
    }

    // Function to create the login frame
    private static void createLoginFrame() {
        loginFrame = new JFrame("Rail Guardian - Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(400, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.setBackground(new Color(176, 224, 230));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(50, 205, 50));
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(e -> login());
        panel.add(loginButton);

        loginFrame.add(panel);
        loginFrame.setVisible(true);
    }

    // Function to handle user login
    private static void login() {
        String name = usernameField.getText();
        String pass = new String(passwordField.getPassword());

        if (name.equals("admin") && pass.equals("admin123")) {
            login = true;
            loginFrame.dispose();
            createMenuFrame();
        } else {
            JOptionPane.showMessageDialog(loginFrame, "Authentication failed. Access denied!");
        }
    }

    // Function to create the main menu frame
    private static void createMenuFrame() {
        menuFrame = new JFrame("Rail Guardian - Menu");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(400, 500);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));
        panel.setBackground(new Color(240, 230, 140));
        menuFrame.add(panel);

        addButton(panel, "Open Barrier", e -> openBarrierFrame());
        addButton(panel, "Close Barrier", e -> closeBarrierFrame());
        addButton(panel, "Check Barrier Status", e -> checkBarrierStatusFrame());
        addButton(panel, "Set Train Approaching", e -> setTrainApproachingFrame());
        addButton(panel, "Check Train Approaching", e -> checkTrainApproachingFrame());
        addButton(panel, "Railway Crossing Alert", e -> railwayCrossingAlertFrame());
        addButton(panel, "Check Track Report", e -> checkTrackReportFrame());
        addButton(panel, "Set Track Issue", e -> setTrackIssueFrame());

        menuFrame.setVisible(true);
    }

    // Function to add buttons to the menu panel
    private static void addButton(JPanel panel, String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setBackground(new Color(100, 149, 237));
        button.setForeground(Color.WHITE);
        button.addActionListener(action);
        panel.add(button);
    }

    // Helper functions to create separate frames for each action
    private static void openBarrierFrame() {
        if (login) {
            JFrame frame = createFrame("Open Barrier");
            JTextArea statusArea = new JTextArea(5, 30);
            statusArea.setEditable(false);
            frame.add(new JScrollPane(statusArea));

            if (!train) {
                if (open) {
                    statusArea.append("Barriers are already opened!\n");
                } else {
                    open = true;
                    displaySignal(true, statusArea);
                    statusArea.append("Barriers opened!\n");
                }
            } else {
                statusArea.append("Cannot open barrier. Train is approaching.\n");
            }

            frame.setVisible(true);
        } else {
            requestLogin();
        }
    }

    private static void closeBarrierFrame() {
        if (login) {
            JFrame frame = createFrame("Close Barrier");
            JTextArea statusArea = new JTextArea(5, 30);
            statusArea.setEditable(false);
            frame.add(new JScrollPane(statusArea));

            if (!open) {
                statusArea.append("Barriers are already closed!\n");
            } else {
                open = false;
                displaySignal(false, statusArea);
                statusArea.append("Barriers closed!\n");
            }

            frame.setVisible(true);
        } else {
            requestLogin();
        }
    }

    private static void checkBarrierStatusFrame() {
        JFrame frame = createFrame("Check Barrier Status");
        JTextArea statusArea = new JTextArea(5, 30);
        statusArea.setEditable(false);
        frame.add(new JScrollPane(statusArea));
        statusArea.append(open ? "Barriers are open.\n" : "Barriers are closed.\n");
        frame.setVisible(true);
    }

    private static void setTrainApproachingFrame() {
        JFrame frame = createFrame("Set Train Approaching");
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        frame.add(panel);

        JButton approachingButton = new JButton("Train Approaching");
        approachingButton.addActionListener(e -> {
            train = true;
            open = false;
            displaySignal(false, new JTextArea());
            JOptionPane.showMessageDialog(frame, "Train is approaching. Barriers closed.");
            frame.dispose();
        });

        JButton goneButton = new JButton("Train Gone");
        goneButton.addActionListener(e -> {
            train = false;
            open = true;
            displaySignal(true, new JTextArea());
            JOptionPane.showMessageDialog(frame, "Train is gone. Barriers opened.");
            frame.dispose();
        });

        panel.add(approachingButton);
        panel.add(goneButton);
        frame.setVisible(true);
    }

    private static void checkTrainApproachingFrame() {
        JFrame frame = createFrame("Check Train Approaching");
        JTextArea statusArea = new JTextArea(5, 30);
        statusArea.setEditable(false);
        frame.add(new JScrollPane(statusArea));

        if (login) {
            if (train) {
                statusArea.append("Train is approaching.\n");
            } else if (open) {
                statusArea.append("Train is gone.\n");
            } else {
                statusArea.append("Train is not approaching.\n");
            }
        } else {
            requestLogin();
        }

        frame.setVisible(true);
    }

    private static void railwayCrossingAlertFrame() {
        if (login) {
            JFrame frame = createFrame("Railway Crossing Alert");
            JTextArea statusArea = new JTextArea(5, 30);
            statusArea.setEditable(false);
            frame.add(new JScrollPane(statusArea));

            String message = JOptionPane.showInputDialog(frame, "Enter the alert message for train engineer:");
            if (message != null && !message.isEmpty()) {
                int choice = JOptionPane.showConfirmDialog(frame, "Send alert message?", "Confirm Alert", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    deliverAlert(message, statusArea);
                } else {
                    statusArea.append("Alert message canceled.\n");
                }
            }

            frame.setVisible(true);
        } else {
            requestLogin();
        }
    }

    private static void checkTrackReportFrame() {
        JFrame frame = createFrame("Check Track Report");
        JTextArea statusArea = new JTextArea(5, 30);
        statusArea.setEditable(false);
        frame.add(new JScrollPane(statusArea));
        statusArea.append(issue ? "Track is not clear to go.\n" : "Track is clear to go.\n");
        frame.setVisible(true);
    }

    private static void setTrackIssueFrame() {
        if (login) {
            JFrame frame = createFrame("Set Track Issue");
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(2, 1));
            frame.add(panel);

            JButton issueButton = new JButton("Track Issue Detected");
            issueButton.addActionListener(e -> {
                issue = true;
                trackMsg();
                JOptionPane.showMessageDialog(frame, "Issue detected on track. Alert sent.");
                frame.dispose();
            });

            JButton resolveButton = new JButton("Track Issue Resolved");
            resolveButton.addActionListener(e -> {
                issue = false;
                JOptionPane.showMessageDialog(frame, "Track is clear for train.");
                frame.dispose();
            });

            panel.add(issueButton);
            panel.add(resolveButton);
            frame.setVisible(true);
        } else {
            requestLogin();
        }
    }

    // Function to create a new frame
    private static JFrame createFrame(String title) {
        JFrame frame = new JFrame("Rail Guardian - " + title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        return frame;
    }

    // Helper function to request login if the user is not logged in
    private static void requestLogin() {
        JOptionPane.showMessageDialog(null, "You are not logged in. Login first.");
    }

    // Function to display the signal for opening/closing the barrier
    private static void displaySignal(boolean isOpen, JTextArea statusArea) {
        String signal = isOpen ? "GO" : "STOP";
        String light = isOpen ? "GREEN LIGHT" : "RED LIGHT";

        Timer timer = new Timer(1000, new ActionListener() {
            int count = 3;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (count > 0) {
                    statusArea.append(count + "\n");
                    count--;
                } else {
                    ((Timer) e.getSource()).stop();
                    statusArea.append(signal + "\n");
                    statusArea.append(light + "\n");
                }
            }
        });
        timer.start();
    }

    // Function to deliver the alert message
    private static void deliverAlert(String message, JTextArea statusArea) {
        Timer timer = new Timer(1000, new ActionListener() {
            int count = 3;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (count > 0) {
                    statusArea.append(".\n");
                    count--;
                } else {
                    ((Timer) e.getSource()).stop();
                    statusArea.append("Delivered!\n");
                    statusArea.append("Delivered ALERT:\n");
                    statusArea.append("Alert sent to all nearest Train Engineers.\n");
                    statusArea.append("ALERT: Issue detected on the railway crossing.\n");
                    statusArea.append("Message: " + message + "\n");
                }
            }
        });
        timer.start();
    }

    // Function to display track messages
    private static void trackMsg() {
        String[] alerts = {
            "Heavy object reported on track",
            "Track has technical issue",
            "Tracks are disconnected",
            "Other train is changing the track"
        };

        String selectedAlert = (String) JOptionPane.showInputDialog(
                null, 
                "Select the alert issue:", 
                "Track Alert", 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                alerts, 
                alerts[0]);

        if (selectedAlert != null) {
            Timer timer = new Timer(1000, new ActionListener() {
                int count = 3;

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (count > 0) {
                        System.out.print(".");
                        count--;
                    } else {
                        ((Timer) e.getSource()).stop();
                        System.out.println("Delivered!");
                        System.out.println("Delivered Track Alert:");
                        System.out.println("Stop the train because,");
                        System.out.println(selectedAlert);
                    }
                }
            });
            timer.start();
        }
    }
}
