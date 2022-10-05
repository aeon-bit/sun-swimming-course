package com.nurul.swimmingcourse.utils;

import java.util.ArrayList;

public class APIError {
    private String message;
    private ArrayList errors;

    public APIError(){

    }

    public String message(){
        return message;
    }

    public ArrayList errors(){
        return errors;
    }

}
