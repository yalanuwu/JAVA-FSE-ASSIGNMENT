package Exercise_2;


public class Product {
    private int productId;
    private String productName;
    private String category;
    // private static ArrayList<Product> productList = new ArrayList<>();

    public Product(){}

    public Product(int productId, String productName, String category){
        this.productId = productId;
        this.productName = productName;
        this.category = category;
    }

    public int getProductId(){ return this.productId;}
    public void setProductId(int productId){ this.productId = productId;}
    public String getProductName() {return this.productName;}
    public void setProductName(String productName) {this.productName = productName;}
    public String getCategory(){ return this.category;}
    public void setCategory(String category){ this.category = category;}

    //Linear Search
    public static Product LinearSearch(Product[] products, int productId){
        for (Product p : products){
            if (p.getProductId() == productId){
                return p;
            }
        }  
        return null;
    }

    //Binary Search
    public static Product BinarySearch(Product[] products, int productId){
        int min = 0;
        int max = products.length -1;
        while (min <= max){
            int mid = min + (max - min) / 2;
            if (products[mid].getProductId() == productId) return products[mid];
            else if (products[mid].getProductId() > productId) max = mid -1;
            else if (products[mid].getProductId() < productId) min = mid + 1;
              
        }
        return null;
    }

    @Override
    public String toString(){
        return "Product{" + "productId=" + productId + ", productName='" + productName + '\'' + ", category='" + category + '\'' + '}';
    }

    public static void main(String[] args){
        //Array for linear search
        Product[] products = {
            new Product(1, "Laptop", "Electronics"),
            new Product(3, "SmartPhone", "Electronics"),
            new Product(2, "Pen", "Stationary"),
            new Product(4, "Knife", "Kitchenware")
        };

        //Array for binary search
        Product[] products_2 = {
            new Product(1, "Laptop", "Electronics"),
            new Product(2, "Pen", "Stationary"),
            new Product(3, "SmartPhone", "Electronics"),
            new Product(4, "Knife", "Kitchenware")
        };

        //Linear Search
        //Time complexity: O(n)
        Product result_1 = Product.LinearSearch(products, 2);
        System.out.println(result_1);

        //Binary Search
        //Time complexity: O(log n)
        Product result_2 = Product.BinarySearch(products_2, 3);
        System.out.println(result_2);

    }
}
