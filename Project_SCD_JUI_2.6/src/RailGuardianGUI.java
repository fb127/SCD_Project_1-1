import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



// Rail Guardian Class
public class RailGuardianGUI { 
    
	// Global variables to track system states
    private static boolean login = false;
    private static boolean issue = false;
    private static boolean open_barrier = true;
    private static boolean train = false;
    private static JFrame loginFrame;
    private static JFrame menuFrame;
    private static JTextField usernameField;
    private static JPasswordField passwordField;

    //main function invoking / calling  from the login frame 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(RailGuardianGUI::createLoginFrame);
    }

    // Function to create the login frame
    /**
     * @wbp.parser.entryPoint
     */
    private static void createLoginFrame() {
       
    	//exception for the login frame
    	try {
    	loginFrame = new JFrame("Rail Guardian - Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(573, 409);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(64, 64, 64));
        panel.setLayout(null);

        //exception for the logo
        try {
        // Logo space
        JLabel logoLabel = new JLabel(new ImageIcon("C:\\Users\\Abdullah\\Desktop\\SCD\\railguardian.png"), JLabel.CENTER); // Update path to your logo
        logoLabel.setBounds(0, 0, 314, 370);
        logoLabel.setPreferredSize(new Dimension(100, 100));
        panel.add(logoLabel);
        }
        catch(Exception e){
        	System.out.println("Error in loading logo image");
        }

        //Label for email
        JLabel labelEmail = new JLabel("  Email");
        labelEmail.setForeground(new Color(255, 255, 255));
        labelEmail.setBounds(324, 127, 192, 29);
        panel.add(labelEmail);
        //public 
        usernameField = new JTextField();
        usernameField.setBounds(324, 157, 212, 29);
        panel.add(usernameField);

        //label for password
        JLabel labelPassword = new JLabel("  Password");
        labelPassword.setForeground(new Color(255, 255, 255));
        labelPassword.setBounds(324, 193, 192, 29);
        panel.add(labelPassword);
        //public
        passwordField = new JPasswordField();
        passwordField.setBounds(324, 224, 212, 29);
        panel.add(passwordField);

        //Login button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(379, 264, 100, 36);
        loginButton.setBackground(new Color(255, 255, 255));
        loginButton.setForeground(new Color(85, 85, 85));
        loginButton.setPreferredSize(new Dimension(100, 30));
        loginButton.addActionListener(e -> login());
        panel.add(loginButton);

        loginFrame.getContentPane().add(panel);
        
        // Login text field
        JTextArea textFieldLogin = new JTextArea();
        textFieldLogin.setText("LOGIN");
        textFieldLogin.setFont(new Font("Arial", Font.PLAIN, 28));
        textFieldLogin.setForeground(new Color(255, 255, 255));
        textFieldLogin.setBackground(new Color(64, 64, 64));
        textFieldLogin.setBounds(379, 69, 93, 36);
        panel.add(textFieldLogin);
        loginFrame.setVisible(true);
    	}
    	catch(Exception e){
    		System.out.println("Error ! login frame cannot be create");
    	}
    }

    // Function to handle user login
    private static void login() {
    	
    	// exception handling for the login function
    	try {
        String name = usernameField.getText();
        String pass = new String(passwordField.getPassword());

        //Conditional statement to authenticate the user
        if (name.equals("admin") && pass.equals("admin123")) {
            login = true;
            loginFrame.dispose();
            createMenuFrame();
        } else {
            JOptionPane.showMessageDialog(loginFrame, "Authentication failed. Access denied!");
        }
    	}
    	catch(Exception e) {
    		System.out.println("Error occured during in login function");
    	}
    }

    // Function to create the main menu frame
    private static void createMenuFrame() {
    	
    	// Exception for menu frame
    	try {
        menuFrame = new JFrame("Rail Guardian - Menu");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(400, 600);
        menuFrame.setBackground(new Color(64, 64, 64));
        
        //Layout
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(64, 64, 64));

        // Label for title
        JLabel titleLabel = new JLabel("RAIL GUARDIAN", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, BorderLayout.NORTH);

        //button declaration
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(8, 1, 10, 10));
        buttonPanel.setBackground(new Color(64, 64, 64));

        //Exception for the buttons
        try {
        //buttons name and association to the frame
        addButton(buttonPanel, "Control Barrier", e -> controlBarrierFrame());
        addButton(buttonPanel, "Check Barrier Status", e -> checkBarrierStatusFrame());
        addButton(buttonPanel, "Set Train Approaching", e -> setTrainApproachingFrame());
        addButton(buttonPanel, "Check Train Approaching", e -> checkTrainApproachingFrame());
        addButton(buttonPanel, "Railway Crossing Alert", e -> railwayCrossingAlertFrame());
        addButton(buttonPanel, "Check Track Report", e -> checkTrackReportFrame());
        addButton(buttonPanel, "Set Track Issue", e -> setTrackIssueFrame());

        // Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(255, 69, 0));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setPreferredSize(new Dimension(100, 30));
        logoutButton.addActionListener(e -> logout());
        buttonPanel.add(logoutButton);
        }
        catch(Exception e) {
        	System.out.println("Error occured in associating button and names");
        }

        
        panel.add(buttonPanel, BorderLayout.CENTER);
        menuFrame.getContentPane().add(panel);
        menuFrame.setVisible(true);
    	}
    	catch(Exception e) {
    		System.out.println("Error ! menu frame cannot be create");
    	}
    }

    // Function to add buttons to the menu panel
    private static void addButton(JPanel panel, String text, ActionListener action) {
        
    	//Exception for button panel
    	try {
    	JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(64, 64, 64));
        button.setPreferredSize(new Dimension(200, 30));
        button.addActionListener(action);
        panel.add(button);
    	}
    	catch(Exception e) {
    		System.out.println("Error occured in creating panel for button");
    	}
    }

    // functions to control the barriers
    private static void controlBarrierFrame() {
        
    	//Exception for control barrier frame 
    	//1
    	try {
    	JFrame frame = createFrame("Control Barrier");
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // stop label 
        JLabel signalLabel = new JLabel("STOP", JLabel.CENTER);
        signalLabel.setFont(new Font("Serif", Font.BOLD, 24));
        signalLabel.setForeground(Color.RED);

        // button to open barrier
        JButton openButton = new JButton("Open Barrier");
        openButton.setBackground(new Color(50, 205, 50));
        openButton.setForeground(Color.WHITE);
        openButton.setPreferredSize(new Dimension(150, 30));
        
       //Exception for opening barrier 2
        try {
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
                        open_barrier = true;
                        signalLabel.setText("GO");
                        signalLabel.setForeground(Color.GREEN);
                    }
                }
            });
            timer.start();
        	
        });
       }
        //2
       catch(Exception e) {
    	   System.out.println("Error occured during the opening of barrier");
       }

        // button to close the barrier
        JButton closeButton = new JButton("Close Barrier");
        closeButton.setBackground(new Color(255, 0, 0));
        closeButton.setForeground(Color.WHITE);
        closeButton.setPreferredSize(new Dimension(150, 30));
       
        //Exception for closing barrier 3
        try {
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
                        open_barrier = false;
                        signalLabel.setText("STOP");
                        signalLabel.setForeground(Color.RED);
                    }
                }
            });
            timer.start();
        });
        }
        //3
        catch(Exception e) {
        	System.out.println("Error occured while closing the barrier");
        }

        panel.add(signalLabel, BorderLayout.NORTH);
        panel.add(openButton, BorderLayout.WEST);
        panel.add(closeButton, BorderLayout.EAST);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    	}
    	//1
    	catch(Exception e) {
    		System.out.println("Error occured creating control barrier frame");
    	}
    }

    // function for checking the barrier status
    private static void checkBarrierStatusFrame() {
    	//Exception for the  barrier status frame
    	try {
        JFrame frame = createFrame("Check Barrier Status");
        JTextArea statusArea = new JTextArea(5, 30);
        statusArea.setEditable(false);
        statusArea.setFont(new Font("Arial", Font.BOLD, 16));
        statusArea.setBackground(new Color(64, 64, 64));
        statusArea.setForeground(Color.WHITE);
        statusArea.setText(open_barrier ? "Barriers are open.\n" : "Barriers are closed.\n");
        frame.getContentPane().add(new JScrollPane(statusArea));
        frame.setVisible(true);
    	}
    	catch(Exception e) {
    		System.out.println("Error ! cannot creat a barrier status frame");
    	}
    }

    // function to see train is approaching or not
    private static void setTrainApproachingFrame() {
    	
    	//Exception for train detection frame 1
    	try {
        JFrame frame = createFrame("Set Train Approaching");
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        frame.getContentPane().add(panel);

       //Exception for train approaching 2
        try {
        //button for indicating train is coming
        JButton approachingButton = new JButton("Train Approaching");
        approachingButton.setBackground(new Color(255, 165, 0));
        approachingButton.setForeground(Color.WHITE);
        approachingButton.setPreferredSize(new Dimension(150, 30));
        approachingButton.addActionListener(e -> {
            train = true;
            open_barrier = false;
            displaySignal(false, new JTextArea());
            JOptionPane.showMessageDialog(frame, "Train is approaching. Barriers closed.");
            frame.dispose();
        });
        panel.add(approachingButton);
        }
        //2
        catch(Exception e) {
        	System.out.println("Error occurred creating train approaching button");
        }

        //Exception for train gone button 3
        try {
        //button for indicating train is gone
        JButton goneButton = new JButton("Train Gone");
        goneButton.setBackground(new Color(50, 205, 50));
        goneButton.setForeground(Color.WHITE);
        goneButton.setPreferredSize(new Dimension(150, 30));
        goneButton.addActionListener(e -> {
            train = false;
            open_barrier = true;
            displaySignal(true, new JTextArea());
            JOptionPane.showMessageDialog(frame, "Train is gone. Barriers opened.");
            frame.dispose();
        });
        
        panel.add(goneButton);
        }
        //3
        catch(Exception e) {
        	System.out.println("Error occured creating train gone button");
        }

       
        frame.setVisible(true);
    	}
    	//1
    	catch(Exception e) {
    		System.out.println("Error occured in creating train detection frame");
    	}
    }

    // function for checking train is coming or not
    private static void checkTrainApproachingFrame() {
    	//Exception for train status 1
    	try {
        JFrame frame = createFrame("Check Train Approaching");
        JTextArea statusArea = new JTextArea(5, 30);
        statusArea.setEditable(false);
        statusArea.setFont(new Font("Arial", Font.BOLD, 16));
        statusArea.setBackground(new Color(64, 64, 64));
        statusArea.setForeground(Color.WHITE);
        frame.getContentPane().add(new JScrollPane(statusArea));
        if (login) {
            if (train) {
                statusArea.setText("Train is approaching.\n");
            } else if (open_barrier) {
                statusArea.setText("Train is gone.\n");
            } else {
                statusArea.setText("Train is not approaching.\n");
            }
        } else {
            requestLogin();
        }
        
        frame.setVisible(true);
    	}
    	//1
    	catch(Exception e){
    		System.out.println("Error occured creating train status frame");
    	}
    }

    // function for alerting the train about any issue occurred at railway crossing 
    private static void railwayCrossingAlertFrame() {
    	
    	// Exception for railway crossing alert 1
    	try {
        JFrame frame = createFrame("Railway Crossing Alert");
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JTextArea alertArea = new JTextArea(10, 30);
        alertArea.setEditable(false);
        alertArea.setFont(new Font("Arial", Font.BOLD, 16));
        alertArea.setBackground(new Color(64, 64, 64));
        alertArea.setForeground(Color.WHITE);

        JButton alertButton = new JButton("Send Alert");
        alertButton.setBackground(new Color(255, 69, 0));
        alertButton.setForeground(Color.WHITE);
        alertButton.setPreferredSize(new Dimension(150, 30));
       
        //Exception for alert button action 2
        try {
        alertButton.addActionListener(e -> {
            String message = JOptionPane.showInputDialog(frame, "Enter alert message:", "Railway Crossing Alert", JOptionPane.INFORMATION_MESSAGE);
            if (message != null && !message.isEmpty()) {
                alertArea.append("Alert: " + message + "\n");
            }
        });
        }
        // 2
        catch(Exception e) {
        	System.out.println("Error occured  creating the alert button ");
        }

        panel.add(new JScrollPane(alertArea), BorderLayout.CENTER);
        panel.add(alertButton, BorderLayout.SOUTH);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    	}
    	//1
    	catch(Exception e) {
    		System.out.println("Error occurre creating railway crossing alert");
    	}
    }

  
    //function for checking the track 
    private static void checkTrackReportFrame() {
    	
    	//Exception for the track report
    	try {
    	
        JFrame frame = createFrame("Check Track Report");
        JTextArea reportArea = new JTextArea(10, 30);
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Arial", Font.BOLD, 16));
        reportArea.setBackground(new Color(64, 64, 64));
        reportArea.setForeground(Color.WHITE);
        if (login) {
            reportArea.setText(issue ? "Track is not clear to go.\n" : "Track is clear to go.\n");
        } else {
            requestLogin();
        }
        frame.getContentPane().add(new JScrollPane(reportArea));
        frame.setVisible(true);
    	}
    	catch(Exception e) {
    		System.out.println("Error occured creating the track report frame");
    	}
    }

    // function for indicating the issue at track
    private static void setTrackIssueFrame() {
    	
    	//Exception for track issue detection 1
    	try {
        JFrame frame = createFrame("Set Track Issue");
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        frame.getContentPane().add(panel);

        JButton issueButton = new JButton("Report Track Issue");
        issueButton.setBackground(new Color(255, 0, 0));
        issueButton.setForeground(Color.WHITE);
        issueButton.setPreferredSize(new Dimension(150, 30));
       
        //Exception for issue button
        try {
        issueButton.addActionListener(e -> {
            issue = true;
            trackMsg();
            JOptionPane.showMessageDialog(frame, "Issue detected on track.");
            frame.dispose();
        });
        }
        catch(Exception e) {
        	System.out.println("Error occurred in issue detection button action");
        }
        

        JButton resolveButton = new JButton("Resolve Track Issue");
        resolveButton.setBackground(new Color(50, 205, 50));
        resolveButton.setForeground(Color.WHITE);
        resolveButton.setPreferredSize(new Dimension(150, 30));
       
        // Exception for issue resolve button
        try {
        resolveButton.addActionListener(e -> {
            issue = false;
            JOptionPane.showMessageDialog(frame, "Track is clear for train.");
            frame.dispose();
        });
        }
        catch(Exception e) {
        	System.out.println("Error occured in issue resolve button action");
        }

        panel.add(issueButton);
        panel.add(resolveButton);
        frame.setVisible(true);
    	}
    	//1
    	catch(Exception e) {
    		System.out.println("Error occuured creating track error detection frame");
    	}
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
    	
    	// exception for logout function
    	try {
        login = false;
        JOptionPane.showMessageDialog(menuFrame, "You are logged out!");
        menuFrame.dispose();
        createLoginFrame();
    	}
    	catch(Exception e) {
    		System.out.println(" Error occcurred in logout function");
    	}
    }

    // function to request login if the user is not logged in
    private static void requestLogin() {
        
    	//exception for request login
    	try {
    	JOptionPane.showMessageDialog(null, "You are not logged in. Login first.");
        }
        catch(Exception e) {
        	System.out.println("error occurred in login request fucntion");
        }
    }

    // Function to display the signal for opening/closing the barrier
    private static void displaySignal(boolean isOpen, JTextArea statusArea) {
        
    	//Exception for display signal function
    	try {
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
    	catch(Exception e) {
    		System.out.println("Error occurred in display sinal function");
    	}
        
    }

    // Function to deliver the alert message
    @SuppressWarnings("unused")
	private static void deliverAlert(String message, JTextArea statusArea) {
    	//Exception for alert deliver function
    	try {
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
    	catch(Exception e) {
    		System.out.println("Error occurred in alert deliver function");
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
