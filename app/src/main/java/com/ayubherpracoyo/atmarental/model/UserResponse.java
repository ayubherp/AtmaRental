package com.ayubherpracoyo.atmarental.model;

import com.ayubherpracoyo.atmarental.model.Customer;
import com.ayubherpracoyo.atmarental.model.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse {
    private String message;

    @SerializedName("user")
    private User user;

    @SerializedName("customer")
    private Customer customer;

    @SerializedName("data")
    private List<User> userList;

    @SerializedName("data2")
    private List<Customer> customerList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }
}
