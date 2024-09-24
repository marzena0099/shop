package com.example.demo.Department;

import com.example.demo.DTO.DepartmentNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Transactional
    public DepartmentEntity edit(DepartmentEntity dp) {
        if (!departmentRepository.existsById(dp.getId())) {
            throw new DepartmentNotFoundException("not found department");
        }
        return departmentRepository.save(dp);

    }

    @Transactional
    public DepartmentEntity add(DepartmentEntity department) {
        return departmentRepository.save(department);
    }

    public List<DepartmentEntity> getAll() {
        return departmentRepository.findAll();
    }

    @Transactional
    public void remove(Long id) {
        if(!departmentRepository.existsById(id)){
            throw new DepartmentNotFoundException("not found department");
        }
        departmentRepository.deleteById(id);
    }
}
