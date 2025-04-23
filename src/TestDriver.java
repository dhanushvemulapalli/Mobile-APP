public class TestDriver {
    public static void main(String[] args) {
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("Success: SQLite JDBC driver loaded.");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: Driver not found!");
            e.printStackTrace();
        }
    }
}
