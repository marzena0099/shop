package com.example.demo.Employee;

import com.example.demo.Address.AddressEntity;
import com.example.demo.DTO.EmployeeNotFoundException;
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
    public EmployeeEntity addEmployee(@RequestBody EmployeeEntity employee) {
        return employeeService.add(employee);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<EmployeeEntity> edit(@PathVariable Long id, @RequestBody EmployeeEntity employee) {
        return employeeService.edit(id, employee)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<EmployeeEntity> getEmployee() {
        return employeeService.getEmployee();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        employeeService.remove(id);
        return ResponseEntity.ok().build();
    }



    @PostMapping("/address")
    public ResponseEntity<EmployeeEntity> addAddressToEmployee(@RequestParam Long employeeId, @RequestParam Long addressId) {
        EmployeeEntity updatedEmployee = employeeService.addAddressToEmployee(employeeId, addressId);
        return ResponseEntity.ok(updatedEmployee);
    }

}

