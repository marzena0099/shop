package com.example.demo;

import com.example.demo.Employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Demo7Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo7Application.class, args);
    }

    @Autowired
    ApplicationContext app;

    @GetMapping("/dupa")
    String abc() {
        String a = app.getBean("abc", String.class);
        System.out.println(a);
        app.getBean(EmployeeService.class);
        new EmployeeService(null, null);
        return a;

    }

}
