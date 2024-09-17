package com.example.demo.Employee;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/employee")
public class EmployeeController {
private final EmployeeService employeeService;
   @PostMapping
    public EmployeeEntity addEmployee(@RequestBody EmployeeEntity employee){
return employeeService.add(employee);
   }
@PostMapping("/edit/{id}")
public ResponseEntity<EmployeeEntity> edit(@PathVariable Long id, @RequestBody EmployeeEntity employee){
       return employeeService.edit(id,employee)
               .map(ResponseEntity::ok)
               .orElse(ResponseEntity.notFound().build());
}
@GetMapping
    public List<EmployeeEntity> getEmployee(){
       return employeeService.getEmployee();
}
@DeleteMapping("/{id}")
    public ResponseEntity<EmployeeEntity> remove(@PathVariable Long id){
       return employeeService.remove(id)
               .map(ResponseEntity::ok)
               .orElse(ResponseEntity.notFound().build());
}
}
