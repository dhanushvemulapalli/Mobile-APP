import java.util.List;
import java.awt.*;
import java.awt.event.*;
// import java.util.List;
// import java.awt.List;

public class ECommerceApp extends Frame implements ActionListener {

    private Label titleLabel;
    private java.awt.List productListDisplay;
    private Button viewDetailsButton;
    private ProductDAO productDAO;

    public ECommerceApp() {
        setTitle("Basic E-commerce App");
        setSize(400, 300);
        setLayout(new FlowLayout()); // Basic layout manager

        productDAO = new ProductDAO();
        titleLabel = new Label("Available Products");
        productListDisplay = new java.awt.List(10, false);
        viewDetailsButton = new Button("View Details");
        viewDetailsButton.addActionListener(this);

        populateProductList();

        add(titleLabel);
        add(productListDisplay);
        add(viewDetailsButton);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });

        setVisible(true);
    }

    private void populateProductList() {
        List<Product> products = productDAO.getAllProducts();
        for (Product product : products) {
            productListDisplay.add(product.toString()); // Using toString for basic display
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewDetailsButton) {
            int selectedIndex = productListDisplay.getSelectedIndex();
            if (selectedIndex != -1) {
                List<Product> products = productDAO.getAllProducts();
                if (selectedIndex < products.size()) {
                    Product selectedProduct = products.get(selectedIndex);
                    // Create a dialog or update the frame to show details
                    showProductDetails(selectedProduct);
                }
            } else {
                System.out.println("No product selected.");
            }
        }
    }

    private void showProductDetails(Product product) {
        Dialog detailsDialog = new Dialog(this, "Product Details", true); // Modal dialog
        detailsDialog.setLayout(new FlowLayout());
        detailsDialog.add(new Label("Name: " + product.getName()));
        detailsDialog.add(new Label("Description: " + product.getDescription()));
        detailsDialog.add(new Label("Price: $" + product.getPrice()));
        detailsDialog.add(new Label("Stock: " + product.getStock()));
        Button closeButton = new Button("Close");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                detailsDialog.dispose();
            }
        });
        detailsDialog.add(closeButton);
        detailsDialog.pack();
        detailsDialog.setLocationRelativeTo(this); // Center relative to the main frame
        detailsDialog.setVisible(true);
    }

    public static void main(String[] args) {
        new ECommerceApp();
    }
}