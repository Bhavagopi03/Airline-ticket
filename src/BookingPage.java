import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BookingPage extends JFrame {
    private JTextField nameField;
    private JTextField ageField;
    private JTextField contactField;
    private JTextField addressField;
    private JTextField paymentAmountField;
    private JTextField cardNumberField;
    private JRadioButton maleButton;
    private JRadioButton femaleButton;
    private JComboBox<String> flightComboBox;
    private JCheckBox vegMeal;
    private JCheckBox nonVegMeal;

    public BookingPage() {
        setTitle("Booking Page");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(10, 20, 80, 25);
        panel.add(nameLabel);

        nameField = new JTextField(20);
        nameField.setBounds(150, 20, 165, 25);
        panel.add(nameField);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(10, 50, 80, 25);
        panel.add(ageLabel);

        ageField = new JTextField(20);
        ageField.setBounds(150, 50, 165, 25);
        panel.add(ageField);

        JLabel contactLabel = new JLabel("Contact:");
        contactLabel.setBounds(10, 80, 80, 25);
        panel.add(contactLabel);

        contactField = new JTextField(20);
        contactField.setBounds(150, 80, 165, 25);
        panel.add(contactField);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(10, 110, 80, 25);
        panel.add(addressLabel);

        addressField = new JTextField(20);
        addressField.setBounds(150, 110, 165, 25);
        panel.add(addressField);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(10, 140, 80, 25);
        panel.add(genderLabel);

        maleButton = new JRadioButton("Male");
        maleButton.setBounds(150, 140, 80, 25);
        panel.add(maleButton);

        femaleButton = new JRadioButton("Female");
        femaleButton.setBounds(240, 140, 80, 25);
        panel.add(femaleButton);

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);

        JLabel flightLabel = new JLabel("Flight No:");
        flightLabel.setBounds(10, 170, 80, 25);
        panel.add(flightLabel);

        flightComboBox = new JComboBox<>(new String[]{"FlightA1", "FlightB2", "FlightB3", "FlightB4", "FlightB5"});
        flightComboBox.setBounds(150, 170, 165, 25);
        panel.add(flightComboBox);

        JLabel mealLabel = new JLabel("Meal Preference:");
        mealLabel.setBounds(10, 200, 120, 25);
        panel.add(mealLabel);

        vegMeal = new JCheckBox("Veg");
        vegMeal.setBounds(150, 200, 60, 25);
        panel.add(vegMeal);

        nonVegMeal = new JCheckBox("Non-Veg");
        nonVegMeal.setBounds(220, 200, 100, 25);
        panel.add(nonVegMeal);

        JLabel paymentLabel = new JLabel("Payment Amount:");
        paymentLabel.setBounds(10, 230, 120, 25);
        panel.add(paymentLabel);

        paymentAmountField = new JTextField(20);
        paymentAmountField.setBounds(150, 230, 165, 25);
        panel.add(paymentAmountField);

        JLabel cardLabel = new JLabel("Card Number:");
        cardLabel.setBounds(10, 260, 120, 25);
        panel.add(cardLabel);

        cardNumberField = new JTextField(20);
        cardNumberField.setBounds(150, 260, 165, 25);
        panel.add(cardNumberField);

        JButton bookButton = new JButton("Book");
        bookButton.setBounds(10, 290, 80, 25);
        panel.add(bookButton);

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookTicket();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(150, 290, 80, 25);
        panel.add(cancelButton);

        cancelButton.addActionListener(e -> {
            dispose(); 
            new HomePage(); 
        });
    }

    private void bookTicket() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO passengers (name, age, contact, address, gender, flight_no, meal, payment_amount, card_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, nameField.getText());
            statement.setInt(2, Integer.parseInt(ageField.getText()));
            statement.setString(3, contactField.getText());
            statement.setString(4, addressField.getText());
            statement.setString(5, maleButton.isSelected() ? "Male" : "Female");
            statement.setString(6, (String) flightComboBox.getSelectedItem());
            statement.setString(7, vegMeal.isSelected() ? "Veg" : (nonVegMeal.isSelected() ? "Non-Veg" : "None"));
            statement.setDouble(8, Double.parseDouble(paymentAmountField.getText()));
            statement.setString(9, cardNumberField.getText());
            statement.executeUpdate();

            
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int passengerId = generatedKeys.getInt(1);
                JOptionPane.showMessageDialog(this, "Booking successful! Your Passenger ID is: " + passengerId);
            }

            dispose(); 
            new HomePage(); 
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error during booking. Please try again.");
        }
    }

    public static void main(String[] args) {
        new BookingPage();
    }
}
