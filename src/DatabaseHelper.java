import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DatabaseHelper {

    private static final String DB_URL = "jdbc:sqlite:ecommerce.db";

    public static Connection getConnection() throws SQLException {
        // Load the SQLite JDBC driver.
        try {
            Class.forName("org.sqlite.JDBC");  // Moved driver loading here
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQLite JDBC driver not found", e); // Wrap in SQLException
        }
        return DriverManager.getConnection(DB_URL);
    }

    public static void executeUpdate(String sql) throws SQLException { // Added throws declaration
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Re-throw the SQLException
        }
    }

    public static ResultSet executeQuery(String sql) throws SQLException{ // Added throws declaration
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Re-throw the SQLException
        }
    }

    public static void initializeDatabase() {
        // SQL statements to create tables (as defined earlier)
        String createProductsTable = "CREATE TABLE IF NOT EXISTS products (" +
                "product_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "description TEXT," +
                "price REAL NOT NULL," +
                "stock INTEGER NOT NULL)";

        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT UNIQUE NOT NULL," +
                "password TEXT NOT NULL," +
                "email TEXT UNIQUE NOT NULL)";

        String createOrdersTable = "CREATE TABLE IF NOT EXISTS orders (" +
                "order_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER NOT NULL," +
                "order_date DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "total_amount REAL NOT NULL," +
                "FOREIGN KEY (user_id) REFERENCES users(user_id))";

        String createOrderItemsTable = "CREATE TABLE IF NOT EXISTS order_items (" +
                "order_item_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "order_id INTEGER NOT NULL," +
                "product_id INTEGER NOT NULL," +
                "quantity INTEGER NOT NULL," +
                "item_price REAL NOT NULL," +
                "FOREIGN KEY (order_id) REFERENCES orders(order_id)," +
                "FOREIGN KEY (product_id) REFERENCES products(product_id))";
        try{
            executeUpdate(createProductsTable);
            executeUpdate(createUsersTable);
            executeUpdate(createOrdersTable);
            executeUpdate(createOrderItemsTable);
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        initializeDatabase();
        System.out.println("Database initialized or already exists.");
    }
}
