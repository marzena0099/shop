package com.example.demo.Department;

import aj.org.objectweb.asm.commons.Remapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;


    public Optional<DepartmentEntity> edit(Long id, DepartmentEntity dp){
        Optional<DepartmentEntity> optDep = departmentRepository.findById(id);
        if(!optDep.isPresent()){
            return Optional.empty();
        }
        DepartmentEntity changedDep= optDep.get();
        changedDep.setCity(dp.getCity());
//        changedDep.setEmployees(dp.getEmployees());
        changedDep.setPostalCode(dp.getPostalCode());
        changedDep.setName(dp.getName());
//        changedDep.setProducts(dp.getProducts());
        departmentRepository.save(changedDep);
        return  Optional.of(changedDep);
    }

    public DepartmentEntity add(DepartmentEntity department){
        return departmentRepository.save(department);
    }

    public List<DepartmentEntity> getAll() {
    return departmentRepository.findAll();
    }

    public Optional<DepartmentEntity> remove(Long id) {
    return departmentRepository.findById(id)
            .map(department -> {
                departmentRepository.delete(department);
                return department;
            });
    }
}
