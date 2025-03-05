import model.Customer;

public class Tester {

    public static void main(String[] args) {
        Customer customer = new Customer("first", "second", "joe@example.net");
        System.out.println(customer);
    }
}
