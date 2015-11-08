package com.example.alejandro.navigationdrawersample.model;
import com.google.gson.annotations.SerializedName;
/**
 * Created by Alejandro on 4/11/2015.
 */
public class Company {
    private String name;
    private String catchPhrase;
    @SerializedName(value = "bs")
    private String kindOfBussines;


    public Company(){

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatchPhrase() {
        return catchPhrase;
    }

    public void setCatchPhrase(String catchPhrase) {
        this.catchPhrase = catchPhrase;
    }

    public String getKindOfBussines() {
        return kindOfBussines;
    }

    public void setKindOfBussines(String kindOfBussines) {
        this.kindOfBussines = kindOfBussines;
    }
}
