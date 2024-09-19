package com.example.demo.Employee;

import com.example.demo.Address.AddressEntity;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<EmployeeEntity> remove(@PathVariable Long id) {
        return employeeService.remove(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
//
    @PostMapping("/{employeeId}/address")
    public ResponseEntity<EmployeeEntity> addAddressToEmployee(@PathVariable Long employeeId, @RequestBody AddressEntity address) {
        EmployeeEntity updatedEmployee = employeeService.addAddressToEmployee(employeeId, address);
        return ResponseEntity.ok(updatedEmployee);
    }

}

