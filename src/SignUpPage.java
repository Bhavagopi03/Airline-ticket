import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class SignUpPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public SignUpPage() {
        setTitle("Sign Up Page");
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

        JLabel userLabel = new JLabel("User");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(100, 20, 165, 25);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 50, 165, 25);
        panel.add(passwordField);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(10, 80, 80, 25);
        panel.add(signUpButton);

        JButton backButton = new JButton("Back to Login");
        backButton.setBounds(100, 120, 140, 25); 
        panel.add(backButton);

       
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signUp();
            }
        });

  
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
                new LoginPage(); 
            }
        });
    }

    private void signUp() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, usernameField.getText());
            statement.setString(2, new String(passwordField.getPassword()));
            statement.executeUpdate();
            
            JOptionPane.showMessageDialog(this, "User created successfully!");
            dispose(); 
            new LoginPage(); 
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error creating user. Please try again.");
        }
    }

    public static void main(String[] args) {
        new SignUpPage();
    }
}
