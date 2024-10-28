import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() {
        Connection connection = null;
        try {
     String URL = "jdbc:mysql://localhost:3306/AirlineReservation";
     String USER = "root"; 
     String PASSWORD = ""; 
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return connection;
}
}


