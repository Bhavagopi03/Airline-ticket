import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AvailableFlightsPage extends JFrame {
    private JTable table;

    public AvailableFlightsPage() {
        setTitle("Available Flights");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        add(panel);
        loadAvailableFlights(panel);

        setVisible(true);
    }

    private void loadAvailableFlights(JPanel panel) {
        panel.setLayout(new BorderLayout());

        table = new JTable();
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        fetchAvailableFlights();
    }

    private void fetchAvailableFlights() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM flights";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            DefaultTableModel model = new DefaultTableModel(new String[]{
                "Flight ID", "Flight Name", "Origin", "Destination", "Departure", "Arrival", "Price"}, 0);
            
            while (resultSet.next()) {
                model.addRow(new Object[]{
                    resultSet.getInt("flight_id"),
                    resultSet.getString("flight_name"),
                    resultSet.getString("origin"),
                    resultSet.getString("destination"),
                    resultSet.getTimestamp("departure_time"),
                    resultSet.getTimestamp("arrival_time"),
                    resultSet.getDouble("price") 
                });
            }
            table.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AvailableFlightsPage();
    }
}
