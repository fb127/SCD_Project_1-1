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
        panel.setLayout(new GridLayout(4, 2));
        panel.setBackground(new Color(176, 224, 230));

        JLabel titleLabel = new JLabel("RAIL GUARDIAN", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLACK);
        panel.add(titleLabel);
        panel.add(new JLabel("")); // Empty cell for grid alignment

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
        menuFrame.setSize(400, 600);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(240, 230, 140));

        JLabel titleLabel = new JLabel("RAIL GUARDIAN", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(7, 1, 10, 10));
        buttonPanel.setBackground(new Color(240, 230, 140));

        addButton(buttonPanel, "Control Barrier", e -> controlBarrierFrame());
        addButton(buttonPanel, "Check Barrier Status", e -> checkBarrierStatusFrame());
        addButton(buttonPanel, "Set Train Approaching", e -> setTrainApproachingFrame());
        addButton(buttonPanel, "Check Train Approaching", e -> checkTrainApproachingFrame());
        addButton(buttonPanel, "Railway Crossing Alert", e -> railwayCrossingAlertFrame());
        addButton(buttonPanel, "Check Track Report", e -> checkTrackReportFrame());
        addButton(buttonPanel, "Set Track Issue", e -> setTrackIssueFrame());

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(255, 69, 0));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.addActionListener(e -> logout());
        buttonPanel.add(logoutButton);

        panel.add(buttonPanel, BorderLayout.CENTER);
        menuFrame.add(panel);
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
    private static void controlBarrierFrame() {
        JFrame frame = createFrame("Control Barrier");
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        JLabel signalLabel = new JLabel("STOP", JLabel.CENTER);
        signalLabel.setFont(new Font("Serif", Font.BOLD, 24));
        signalLabel.setForeground(Color.RED);
        
        JButton openButton = new JButton("Open Barrier");
        openButton.setBackground(new Color(50, 205, 50));
        openButton.setForeground(Color.WHITE);
        openButton.addActionListener(e -> {
            Timer timer = new Timer(1000, new ActionListener() {
                int count = 3;
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (count > 0) {
                        signalLabel.setText("Opening in " + count);
                        count--;
                    } else {
                        ((Timer) e.getSource()).stop();
                        open = true;
                        signalLabel.setText("GO");
                        signalLabel.setForeground(Color.GREEN);
                    }
                }
            });
            timer.start();
        });
        
        JButton closeButton = new JButton("Close Barrier");
        closeButton.setBackground(new Color(255, 0, 0));
        closeButton.setForeground(Color.WHITE);
        closeButton.addActionListener(e -> {
            Timer timer = new Timer(1000, new ActionListener() {
                int count = 3;
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (count > 0) {
                        signalLabel.setText("Closing in " + count);
                        count--;
                    } else {
                        ((Timer) e.getSource()).stop();
                        open = false;
                        signalLabel.setText("STOP");
                        signalLabel.setForeground(Color.RED);
                    }
                }
            });
            timer.start();
        });

        panel.add(signalLabel, BorderLayout.NORTH);
        panel.add(openButton, BorderLayout.WEST);
        panel.add(closeButton, BorderLayout.EAST);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void checkBarrierStatusFrame() {
        JFrame frame = createFrame("Check Barrier Status");
        JTextArea statusArea = new JTextArea(5, 30);
        statusArea.setEditable(false);
        frame.add(new JScrollPane(statusArea));
        statusArea.setText(open ? "Barriers are open.\n" : "Barriers are closed.\n");
        frame.setVisible(true);
    }

    private static void setTrainApproachingFrame() {
        JFrame frame = createFrame("Set Train Approaching");
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        frame.add(panel);

        JButton approachingButton = new JButton("Train Approaching");
        approachingButton.setBackground(new Color(255, 165, 0));
        approachingButton.setForeground(Color.WHITE);
        approachingButton.addActionListener(e -> {
            train = true;
            open = false;
            displaySignal(false, new JTextArea());
            JOptionPane.showMessageDialog(frame, "Train is approaching. Barriers closed.");
            frame.dispose();
        });

        JButton goneButton = new JButton("Train Gone");
        goneButton.setBackground(new Color(50, 205, 50));
        goneButton.setForeground(Color.WHITE);
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
                statusArea.setText("Train is approaching.\n");
            } else if (open) {
                statusArea.setText("Train is gone.\n");
            } else {
                statusArea.setText("Train is not approaching.\n");
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
            if (message != null && !message.trim().isEmpty()) {
                statusArea.append("Sending alert...\n");
                deliverAlert(message, statusArea);
            } else {
                statusArea.append("Alert message canceled.\n");
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

        if (login) {
            statusArea.setText(issue ? "Track is not clear to go.\n" : "Track is clear to go.\n");
        } else {
            requestLogin();
        }

        frame.setVisible(true);
    }

    private static void setTrackIssueFrame() {
        JFrame frame = createFrame("Set Track Issue");
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        frame.add(panel);

        JButton issueButton = new JButton("Report Track Issue");
        issueButton.setBackground(new Color(255, 0, 0));
        issueButton.setForeground(Color.WHITE);
        issueButton.addActionListener(e -> {
            issue = true;
            trackMsg();
            JOptionPane.showMessageDialog(frame, "Issue detected on track.");
            frame.dispose();
        });

        JButton resolveButton = new JButton("Resolve Track Issue");
        resolveButton.setBackground(new Color(50, 205, 50));
        resolveButton.setForeground(Color.WHITE);
        resolveButton.addActionListener(e -> {
            issue = false;
            JOptionPane.showMessageDialog(frame, "Track is clear for train.");
            frame.dispose();
        });

        panel.add(issueButton);
        panel.add(resolveButton);
        frame.setVisible(true);
    }

    // Helper function to create a new frame with a given title
    private static JFrame createFrame(String title) {
        JFrame frame = new JFrame("Rail Guardian - " + title);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        return frame;
    }

    // Function to handle user logout
    private static void logout() {
        login = false;
        JOptionPane.showMessageDialog(menuFrame, "You are logged out!");
        menuFrame.dispose();
        createLoginFrame();
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
