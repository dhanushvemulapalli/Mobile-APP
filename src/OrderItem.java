

public class OrderItem {
    private int orderItemId;
    private int orderId;
    private int productId;
    private int quantity;
    private double itemPrice; // Price at the time of order

    public OrderItem(int orderItemId, int orderId, int productId, int quantity, double itemPrice) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
    }

    // Getters and Setters
    public int getOrderItemId() { return orderItemId; }
    public int getOrderId() { return orderId; }
    public int getProductId() { return productId; }
    public int getQuantity() { return quantity; }
    public double getItemPrice() { return itemPrice; }

    public void setOrderItemId(int orderItemId) { this.orderItemId = orderItemId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public void setProductId(int productId) { this.productId = productId; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setItemPrice(double itemPrice) { this.itemPrice = itemPrice; }
}