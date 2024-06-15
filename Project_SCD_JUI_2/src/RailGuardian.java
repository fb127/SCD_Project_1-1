import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RailGuardian {
    // Global variables to track system states
    private static boolean login = false;
    private static boolean issue = false;
    private static boolean open = true;
    private static boolean train = false;

    private static JFrame frame;
    private static JTextArea statusArea;
    private static JTextField usernameField;
    private static JPasswordField passwordField;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Rail Guardian");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 500);

            // Create main panel
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            frame.add(panel);

            // Create and add components to the panel
            addComponents(panel);

            frame.setVisible(true);
        });
    }

    // Function to add components to the main panel
    private static void addComponents(JPanel panel) {
        // Status Area
        statusArea = new JTextArea(10, 30);
        statusArea.setEditable(false);
        panel.add(new JScrollPane(statusArea));

        // Login Panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 2));
        panel.add(loginPanel);

        loginPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        loginPanel.add(usernameField);

        loginPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        loginPanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> login());
        loginPanel.add(loginButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());
        loginPanel.add(logoutButton);

        // Action Buttons Panel
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new GridLayout(6, 2));
        panel.add(actionPanel);

        addButton(actionPanel, "Open Barrier", e -> openBarrier());
        addButton(actionPanel, "Close Barrier", e -> closeBarrier());
        addButton(actionPanel, "Check Barrier Status", e -> checkBarrierStatus());
        addButton(actionPanel, "Set Train Approaching", e -> setTrainApproaching());
        addButton(actionPanel, "Check Train Approaching", e -> checkTrainApproaching());
        addButton(actionPanel, "Railway Crossing Alert", e -> railwayCrossingAlert());
        addButton(actionPanel, "Check Track Report", e -> checkTrackReport());
        addButton(actionPanel, "Set Track Issue", e -> setTrackIssue());
    }

    // Function to add buttons to the action panel
    private static void addButton(JPanel panel, String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        panel.add(button);
    }

    // Function to handle user login
    private static void login() {
        String name = usernameField.getText();
        String pass = new String(passwordField.getPassword());

        if (name.equals("admin") && pass.equals("admin123")) {
            statusArea.append("Authentication successful!\n");
            login = true;
        } else {
            statusArea.append("Authentication failed. Access denied!\n");
        }
    }

    // Function to handle user logout
    private static void logout() {
        login = false;
        statusArea.append("You are logged out!\n");
    }

    // Function to open the barrier
    private static void openBarrier() {
        if (login) {
            if (!train) {
                if (open) {
                    statusArea.append("Barriers are already opened!\n");
                } else {
                    open = true;
                    displaySignal(true);
                    statusArea.append("Barriers opened!\n");
                }
            } else {
                statusArea.append("Cannot open barrier. Train is approaching.\n");
            }
        } else {
            requestLogin();
        }
    }

    // Function to close the barrier
    private static void closeBarrier() {
        if (login) {
            if (!open) {
                statusArea.append("Barriers are already closed!\n");
            } else {
                open = false;
                displaySignal(false);
                statusArea.append("Barriers closed!\n");
            }
        } else {
            requestLogin();
        }
    }

    // Function to check the status of the barrier
    private static void checkBarrierStatus() {
        statusArea.append(open ? "Barriers are open.\n" : "Barriers are closed.\n");
    }

    // Function to set the train approaching status
    private static void setTrainApproaching() {
        int choice = JOptionPane.showConfirmDialog(frame, "Is the train approaching?", "Train Status", JOptionPane.YES_NO_OPTION);
        train = (choice == JOptionPane.YES_OPTION);

        if (train) {
            open = false;
            statusArea.append("Train is approaching.\n");
        } else {
            open = true;
            statusArea.append("Train is gone.\n");
        }

        displaySignal(open);
    }

    // Function to check if a train is approaching
    private static void checkTrainApproaching() {
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
    }

    // Function to handle railway crossing alert
    private static void railwayCrossingAlert() {
        if (login) {
            String message = JOptionPane.showInputDialog(frame, "Enter the alert message for train engineer:");
            if (message != null && !message.isEmpty()) {
                int choice = JOptionPane.showConfirmDialog(frame, "Send alert message?", "Confirm Alert", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    deliverAlert(message);
                } else {
                    statusArea.append("Alert message canceled.\n");
                }
            }
        } else {
            requestLogin();
        }
    }

    // Function to check the track report
    private static void checkTrackReport() {
        if (login) {
            statusArea.append(issue ? "Track is not clear to go.\n" : "Track is clear to go.\n");
        } else {
            requestLogin();
        }
    }

    // Function to set the track issue status
    private static void setTrackIssue() {
        if (login) {
            int choice = JOptionPane.showConfirmDialog(frame, "Is there a track issue?", "Track Issue", JOptionPane.YES_NO_OPTION);
            issue = (choice == JOptionPane.YES_OPTION);

            if (issue) {
                statusArea.append("Issue detected on track.\n");
                trackMsg();
            } else {
                statusArea.append("Track is clear for train.\n");
            }
        } else {
            requestLogin();
        }
    }

    // Helper function to request login if the user is not logged in
    private static void requestLogin() {
        statusArea.append("You are not logged in. Login first.\n");
    }

    // Function to display the signal for opening/closing the barrier
    private static void displaySignal(boolean isOpen) {
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

    // Function to handle the camera alert
    private static void cameraAlert() {
        String message = JOptionPane.showInputDialog(frame, "Enter the alert message for train engineer:");
        if (message != null && !message.isEmpty()) {
            int choice = JOptionPane.showConfirmDialog(frame, "Send alert message?", "Confirm Alert", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                deliverAlert(message);
            } else {
                statusArea.append("Alert message canceled.\n");
            }
        }
    }

    // Function to display track messages
    private static void trackMsg() {
        String[] alerts = {
                "Heavy object reported on track",
                "Track has technical issue",
                "Tracks are disconnected",
                "Other train is changing the track"
        };

        String selectedAlert = (String) JOptionPane.showInputDialog(frame, "Select the alert issue:",
                "Track Alert", JOptionPane.QUESTION_MESSAGE, null, alerts, alerts[0]);

        if (selectedAlert != null) {
            Timer timer = new Timer(2000, new ActionListener() {
                int count = 3;

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (count > 0) {
                        statusArea.append(".\n");
                        count--;
                    } else {
                        ((Timer) e.getSource()).stop();
                        statusArea.append("Delivered!\n");
                        statusArea.append("Delivered Track Alert:\n");
                        statusArea.append("Stop the train because,\n");
                        statusArea.append(selectedAlert + "\n");
                    }
                }
            });
            timer.start();
        }
    }

    // Function to deliver the alert message
    private static void deliverAlert(String message) {
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
}
