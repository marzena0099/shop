package com.example.demo.Employee;

import com.example.demo.Address.AddressEntity;
import com.example.demo.Address.AddressRepository;
import com.example.demo.DTO.AddressNotFoundException;
import com.example.demo.DTO.EmployeeNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final AddressRepository addressRepository;

    @Transactional
    public EmployeeEntity add(EmployeeEntity employee) {
        employeeRepository.save(employee);
        return employee;
    }

    @Transactional
    public EmployeeEntity addAddressToEmployee(Long employeeId, Long addressID) {
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("not found employee"));
        AddressEntity address = addressRepository.findById(addressID)
                .orElseThrow(() -> new AddressNotFoundException("not found address"));
        employee.setAddress(address);
        return employeeRepository.save(employee);
    }


    @Transactional
    public EmployeeEntity edit(EmployeeEntity employee) {
        if (!employeeRepository.existsById(employee.getId())) {
            throw new EmployeeNotFoundException("not found employee");
        }
        return employeeRepository.save(employee);
    }

    @Transactional
    public List<EmployeeEntity> getEmployee() {
        return employeeRepository.findAll();
    }

    @Transactional
    public void remove(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException("Employee not found for ID: " + id);
        }
        employeeRepository.deleteById(id);
    }
}
