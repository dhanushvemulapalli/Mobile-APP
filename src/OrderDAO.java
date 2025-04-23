import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp; // Import for converting LocalDateTime to SQL Timestamp
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

class OrderDAO {

    public void createOrder(Order order) {
        String sql = "INSERT INTO orders (user_id, order_date, total_amount) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection(); // Use your DatabaseHelper
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, order.getUserId());
            pstmt.setTimestamp(2, Timestamp.valueOf(order.getOrderDate())); // Convert LocalDateTime to Timestamp
            pstmt.setDouble(3, order.getTotalAmount());
            pstmt.executeUpdate();

            // Get the generated order ID
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setOrderId(generatedKeys.getInt(1)); // Set the order ID in the Order object
                } else {
                    throw new SQLException("Failed to retrieve order ID.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Consider logging the error instead of just printing to console
            // You might want to throw a custom exception here to indicate order creation failure
        }
    }

    public List<Order> getOrdersForUser(int userId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                LocalDateTime orderDate = rs.getTimestamp("order_date").toLocalDateTime(); // Convert Timestamp to LocalDateTime
                double totalAmount = rs.getDouble("total_amount");
                Order order = new Order(orderId, userId, orderDate, totalAmount);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider logging the error
        }
        return orders;
    }
    public static void main(String[] args) {
        // Example usage (for testing)
        // You'll need a DatabaseHelper and the Order class defined.
        //  Also, ensure your database is set up.

        try {
            // 1. Create a test user (you might have this in your database already)
            // For testing,  I'm assuming you have a User with ID 1.  Don't hardcode in real app
            int testUserId = 1;

            // 2. Create a sample order
            LocalDateTime now = LocalDateTime.now();
            Order newOrder = new Order(0, testUserId, now, 100.00); // orderId will be set by createOrder()

            // 3.  Instantiate OrderDAO
            OrderDAO orderDAO = new OrderDAO();

            // 4. Create the order in the database
            orderDAO.createOrder(newOrder);
            System.out.println("Order created with ID: " + newOrder.getOrderId());

            // 5. Get orders for the user
            List<Order> orders = orderDAO.getOrdersForUser(testUserId);
            System.out.println("\nOrders for user " + testUserId + ":");
            for (Order order : orders) {
                System.out.println("Order ID: " + order.getOrderId() + ", Date: " + order.getOrderDate() + ", Total: " + order.getTotalAmount());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
