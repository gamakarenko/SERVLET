package com.example.task3.Model;

public class Address {
    private int user_id;
    private String street;
    private int house;

    public Address() {
    }

    public Address(int user_id, String street, int house) {
        this.user_id = user_id;
        this.street = street;
        this.house = house;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouse() {
        return house;
    }

    public void setHouse(int house) {
        this.house = house;
    }
}
