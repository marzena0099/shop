package com.example.demo.Department;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping
    public DepartmentEntity addDepartment(@RequestBody DepartmentEntity department) {
        return departmentService.add(department);
    }

    @PostMapping("/{id}")
    public ResponseEntity<DepartmentEntity> edit(@PathVariable Long id, @RequestBody DepartmentEntity department) {
        return departmentService.edit(id, department)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<DepartmentEntity> getAll() {
        return departmentService.getAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DepartmentEntity> remove(@PathVariable Long id) {
        return departmentService.remove(id).
                map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
