package com.example.alejandro.navigationdrawersample.model;

/**
 * Created by Alejandro on 4/11/2015.
 */
import com.google.gson.annotations.SerializedName;
public class User {
    private int id;
    @SerializedName(value = "username")
    private String userName;
    private String name;
    private String email;
    private String phone;
    private Company company;

    public User(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Name : "+name+ " Username "+userName;
    }
}
