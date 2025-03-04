package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer {

    private final String firstName;
    private final String lastName;
    private final String email;

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;

        String emailPattern = "^(.+)@(.+).(.+)$";
        Pattern pattern = Pattern.compile(emailPattern);
        if (!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Email format not accepted!");
        }
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "First Name: " + firstName + " " +
                "Last Name: " + lastName + " " +
                "Email address: " + email;
    }
}
