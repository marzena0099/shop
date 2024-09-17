package com.example.demo.Employee;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeEntity add(EmployeeEntity employee) {

        employeeRepository.save(employee);
        return employee;
    }
@Transactional
    public Optional<EmployeeEntity> edit(Long id, EmployeeEntity employee) {
        Optional<EmployeeEntity> oldWorker = employeeRepository.findById(id);
        if(oldWorker.isEmpty()){
           return Optional.empty();
        }
        EmployeeEntity changedWorker = oldWorker.get();
//        changedWorker.setDepartment(employee.getDepartment());
        changedWorker.setName(employee.getName());
        changedWorker.setCity(employee.getCity());
        changedWorker.setPESEL(employee.getPESEL());
        changedWorker.setSurname(employee.getSurname());
        employeeRepository.save(changedWorker);
        return Optional.of(changedWorker);
    }

    public List<EmployeeEntity> getEmployee() {
        return employeeRepository.findAll();
    }

    public Optional<EmployeeEntity> remove(Long id) {
       return employeeRepository.findById(id)
               .map(employee -> {
               employeeRepository.delete(employee);
       return employee;
       });
    }
}
