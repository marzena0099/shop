package com.example.demo.Department;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentEntity> addDepartment(@RequestBody DepartmentEntity department) {
        DepartmentEntity department1= departmentService.add(department);
        return new ResponseEntity<>(department1, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<DepartmentEntity> edit(@RequestBody DepartmentEntity department) {
        DepartmentEntity departmentEdited =  departmentService.edit(department);
        return new ResponseEntity<>(departmentEdited, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<DepartmentEntity>> getAll() {
        List<DepartmentEntity> listD= departmentService.getAll();
        return new ResponseEntity<>(listD, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
    departmentService.remove(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
