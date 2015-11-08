package com.example.alejandro.navigationdrawersample.controller;

/**
 * Created by Alejandro on 5/11/2015.
 */
public class Login {
    private String username;
    private String password;

    public Login(String username, String password){
        this.username = username;
        this.password = password;
    }


    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public boolean isAValidUser(){
        if(username.equals("usuario") && password.equals("usuario"))
            return true;
        else
            return false;

    }

}
