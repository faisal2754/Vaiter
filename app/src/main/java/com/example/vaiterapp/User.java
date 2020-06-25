package com.example.vaiterapp;

public class User {

    private String FirstName, LastName, Email, RestaurantName;
    private int CustomerID, StaffID;

    public User(int ID, String firstName, String lastName, String email) {
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        CustomerID = ID;
    }

    public User(int ID, String firstName, String lastName, String email, String restaurantName) {
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        RestaurantName = restaurantName;
        StaffID = ID;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public int getStaffID() {
        return StaffID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getEmail() {
        return Email;
    }

    public String getRestaurantName(){
        return RestaurantName;
    }
}
