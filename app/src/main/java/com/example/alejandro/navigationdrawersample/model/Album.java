package com.example.alejandro.navigationdrawersample.model;

/**
 * Created by Alejandro on 4/11/2015.
 */
public class Album {
    private int userId;
    private int id;
    private String title;


    public Album(){

    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
