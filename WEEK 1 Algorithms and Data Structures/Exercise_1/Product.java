package Exercise_1;
import java.util.HashMap;
import java.util.Map;

public class Product {

    private int productId;
    private String productName;
    private int quantity;
    private double price;
    private static Map<Integer, Product> products = new HashMap<>();

    public Product(){}

    public Product(int productId, String productName, int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    //Method to add products
    public static void addProduct(Product product) {
        products.put(product.getProductId(), product);
    }

    //Method to update products
    public static void updateProduct(int productId, String productName, int quantity, double price){
        Product product = products.get(productId);
        if (product != null){
            product.setProductName(productName);
            product.setQuantity(quantity);
            product.setPrice(price);
        } else {
            System.out.println("Product not found.");
        }
    }

    //Method to delete product
    public static void deleteProduct(int productId){
        products.remove(productId);
    }


    public static void main(String[] args) {

        //Adding products
        //Average Time Complexity : O(1)
        Product.addProduct(new Product(1, "Laptop", 10, 999.99));
        Product.addProduct(new Product(2, "Smartphone", 20, 499.99));
        Product.addProduct(new Product(3, "Tablet", 15, 299.99));

        //Updating products
        //Average Time Complexity : O(1)
        Product.updateProduct(1, "New Laptop", 10, 999.99);

        //Deleting product
        //Average Time Complexity : O(1)
        Product.deleteProduct(2);

    }
}

