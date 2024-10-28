import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class CancelTicketPage extends JFrame {
    private JTextField passengerIdField;

    public CancelTicketPage() {
        setTitle("Cancel Ticket Page");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel passengerIdLabel = new JLabel("Passenger ID:");
        passengerIdLabel.setBounds(10, 20, 100, 25);
        panel.add(passengerIdLabel);

        passengerIdField = new JTextField(20);
        passengerIdField.setBounds(120, 20, 165, 25);
        panel.add(passengerIdField);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(10, 60, 80, 25);
        panel.add(cancelButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(120, 60, 80, 25);
        panel.add(backButton);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelTicket();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
                new HomePage(); 
            }
        });
    }

    private void cancelTicket() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM passengers WHERE passenger_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(passengerIdField.getText()));
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Ticket cancelled successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Passenger ID not found.");
            }
            dispose(); 
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error during cancellation. Please try again.");
        }
    }

    public static void main(String[] args) {
        new CancelTicketPage();
    }
}
