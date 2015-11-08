package com.example.alejandro.navigationdrawersample.util;

import java.util.Random;

/**
 * Created by Alejandro on 5/11/2015.
 */
public class RandomHelper {
    private static final int MAX = 10;
    private static final int MIN = 1;
    public static int getRandomIdNumber(){
        Random random = new Random();
        int id= random.nextInt(MAX - MIN + 1) + MIN;
        return id;
    }
}
