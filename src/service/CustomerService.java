package service;

import model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {

    private static CustomerService instance;
    private List<Customer> customers;

    private CustomerService() {
        customers = new ArrayList<>();
    }

    public static CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
        }
        return instance;
    }

    public void addCustomer(String email, String firstName, String lastName) {
        if (customerEmailExists(email)) {
            throw new IllegalArgumentException("Customer with email " + email + " already exists!");
        }

        Customer customerToSave = new Customer(firstName, lastName, email);
        customers.add(customerToSave);
    }

    private boolean customerEmailExists(String email) {
        for (Customer customer : customers) {
            if (customer.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public Customer getCustomer(String email) {
        for (Customer customer : customers) {
            if (customer.getEmail().equals(email)) {
                return customer;
            }
        }
        return null;
    }

    public List<Customer> getAllCustomers() {
        return customers;
    }
}
