package com.example.vaiterapp;

public class User {

    private String FirstName, LastName, Email, RestaurantName;

    public User(String firstName, String lastName, String email) {
        FirstName = firstName;
        LastName = lastName;
        Email = email;
    }

    public User(String firstName, String lastName, String email, String restaurantName) {
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        RestaurantName = restaurantName;
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
