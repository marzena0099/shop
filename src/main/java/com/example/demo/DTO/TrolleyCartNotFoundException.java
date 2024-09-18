package com.example.demo.DTO;



public class TrolleyCartNotFoundException extends RuntimeException{
    public TrolleyCartNotFoundException( String message){
        super(message);
    }
}
