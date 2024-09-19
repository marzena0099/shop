package com.example.demo.DTO;

public class EmployeeNotFoundException extends RuntimeException{
public EmployeeNotFoundException(String message){
    super(message);
}
}
