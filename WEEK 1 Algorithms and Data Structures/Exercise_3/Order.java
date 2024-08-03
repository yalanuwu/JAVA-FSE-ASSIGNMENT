package Exercise_3;

public class Order {
    private int orderId;
    private  String customerName;
    private double totalPrice;

    public Order(){}

    public Order(int orderId, String customerName, double totalPrice){
        this.orderId = orderId;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
    }

    int getOrderId(){return this.orderId;}
    void setOrderId(int id){this.orderId = id;}

    String getCustomerName(){return this.customerName;}
    void setCustomerName(String name){this.customerName = name;}

    double getTotalPrice(){return this.totalPrice;}
    void setTotalPrice(double price){this.totalPrice = price;}

    @Override
    public String toString(){
        return "Order ID: " + orderId + "\nCustomer Name: " + customerName + "\nTotal Price: $" + totalPrice + "\n";
    }

    //Bubble sort
    public static void bubbleSort(Order[] orders){
        for (int i = 0;  i < orders.length - 1; i++){
            for (int j = 0; j < orders.length - i - 1; j++){
                if (orders[j].getTotalPrice() > orders[j + 1].getTotalPrice()){
                    Order temp = orders[j];
                    orders[j] = orders[j + 1];
                    orders[j + 1] = temp;
                }
            }
        }
    }

    //Quick sort
    public static void quickSort(Order[] orders, int start, int end){
        if (end <= start) return;
        int pivot = partition(orders, start, end);
        quickSort(orders, start, pivot -1);
        quickSort(orders, pivot + 1, end);
    }

    private static int partition(Order[] orders, int start, int end) {
        double pivot = orders[end].getTotalPrice();
        int i = start -1;
        for (int j = start; j <= end -1; j++){
            if (orders[j].getTotalPrice() < pivot){
                i++;
                Order temp = orders[i];
                orders[i] = orders[j];
                orders[j] = temp;
            }
        }
        i++;
        Order temp = orders[i];
        orders[i] = orders[end];
        orders[end] = temp;

        return i;
    }

    public static void main(String[] args){
        Order[] orders = {
            new Order(1, "Name1", 300),
            new Order(2, "Name2", 100),
            new Order(3, "Name3", 600),
            new Order(4, "Name4", 200),
            new Order(5, "Name5", 500)
        };

        //Applying Bubble Sort
        //Time complexity: O(n^2)
        Order.bubbleSort(orders);
        for (Order i : orders){
            System.out.println(i);
        }
        

        //Applying Quick Sort
        //Time complexity: O(nlogn)
        Order.quickSort(orders, 0, orders.length -1);
        for (Order i : orders){
            System.out.println(i);
        }
    }
}
