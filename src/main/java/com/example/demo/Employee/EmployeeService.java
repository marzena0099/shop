package com.example.demo.Employee;

import com.example.demo.Address.AddressEntity;
import com.example.demo.Address.AddressRepository;
import com.example.demo.DTO.AddressNotFoundException;
import com.example.demo.DTO.EmployeeNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final AddressRepository addressRepository;

    public EmployeeEntity add(EmployeeEntity employee) {
        employeeRepository.save(employee);
        return employee;
    }

    public EmployeeEntity addAddressToEmployee(Long employeeId, Long addressID) {
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("not found employee"));
        AddressEntity address = addressRepository.findById(addressID)
                .orElseThrow(() -> new AddressNotFoundException("not found address"));
        employee.setAddress(address);
        return employeeRepository.save(employee);
    }

    private boolean isValidAddressType(String addressType) {
        return Arrays.asList("RESIDENTIAL", "BILLING", "SHIPPING").contains(addressType);
    }

    @Transactional
    public Optional<EmployeeEntity> edit(Long id, EmployeeEntity employee) {
        Optional<EmployeeEntity> oldWorker = employeeRepository.findById(id);
        if (oldWorker.isEmpty()) {
            return Optional.empty();
        }
        EmployeeEntity changedWorker = oldWorker.get();
        changedWorker.setDepartment(employee.getDepartment());
        changedWorker.setName(employee.getName());
        changedWorker.setAddress(employee.getAddress());
        changedWorker.setPESEL(employee.getPESEL());
        changedWorker.setSurname(employee.getSurname());
        employeeRepository.save(changedWorker);
        return Optional.of(changedWorker);
    }

    public List<EmployeeEntity> getEmployee() {
        return employeeRepository.findAll();
    }

    public void remove(Long id) {
        employeeRepository.findById(id)
                .ifPresentOrElse(
                        employeeRepository::delete,
                        () -> {
                            throw new EmployeeNotFoundException("Employee not found for ID: " + id);
                        }
                );
    }
}
