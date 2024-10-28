import javax.swing.*;
import java.awt.*;

public class HomePage extends JFrame {
    public HomePage() {
        setTitle("Home Page");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setVisible(true); // Set HomePage visible
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(new GridLayout(5, 1));

        JLabel welcomeLabel = new JLabel("Welcome to ABC Tickets!", JLabel.CENTER);
        panel.add(welcomeLabel);

        JButton bookTicketButton = new JButton("Book Ticket");
        panel.add(bookTicketButton);
        bookTicketButton.addActionListener(e -> {
            new BookingPage(); // Ensure BookingPage class is defined and visible
        });

        JButton cancelTicketButton = new JButton("Cancel Ticket");
        panel.add(cancelTicketButton);
        cancelTicketButton.addActionListener(e -> {
            new CancelTicketPage(); // Ensure CancelTicketPage class is defined and visible
        });

        JButton availableFlightsButton = new JButton("Available Flights");
        panel.add(availableFlightsButton);
        availableFlightsButton.addActionListener(e -> {
            new AvailableFlightsPage(); // Ensure AvailableFlightsPage class is defined and visible
        });

        JButton logoutButton = new JButton("Logout");
        panel.add(logoutButton);
        logoutButton.addActionListener(e -> {
            dispose(); // Close HomePage
            new LoginPage(); // Open LoginPage again
        });
    }

    public static void main(String[] args) {
        new HomePage();
    }
}
