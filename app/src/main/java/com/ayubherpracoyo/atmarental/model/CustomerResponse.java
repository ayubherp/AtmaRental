package com.ayubherpracoyo.atmarental.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerResponse {
    private String message;
    @SerializedName("data")
    private List<Customer> customerList;
    @SerializedName("customer")
    private Customer customer;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
