package com.example.demo.Employee;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeEntity> addEmployee(@RequestBody EmployeeEntity employee) {
        EmployeeEntity added = employeeService.add(employee);
        return new ResponseEntity<>(added, HttpStatus.CREATED);
    }

    @PutMapping("/edit")
    public ResponseEntity<EmployeeEntity> edit(@RequestBody EmployeeEntity employee) {
        EmployeeEntity edited= employeeService.edit(employee);
                return new ResponseEntity<>(edited, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeEntity>> getEmployee() {
        List<EmployeeEntity> listEmployees = employeeService.getEmployee();
    return new ResponseEntity<>(listEmployees, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        employeeService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    @PostMapping("/address")
    public ResponseEntity<EmployeeEntity> addAddressToEmployee(@RequestParam Long employeeId, @RequestParam Long addressId) {
        EmployeeEntity updatedEmployee = employeeService.addAddressToEmployee(employeeId, addressId);
        return new ResponseEntity<>(updatedEmployee,HttpStatus.OK);
    }

}

