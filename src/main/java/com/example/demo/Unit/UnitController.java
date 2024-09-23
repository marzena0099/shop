package com.example.demo.Unit;

import com.example.demo.DTO.UnitNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/unit")
public class UnitController {
    private final UnitService unitService;
    private String abc;


    @PostMapping
    public ResponseEntity<UnitEntity> add(@RequestBody UnitEntity entity) {
        UnitEntity unit = unitService.add(entity);
        return new ResponseEntity<>(unit, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UnitEntity> edit(@RequestBody UnitEntity entity) throws UnitNotFoundException {
        UnitEntity editedUnit = unitService.edit(entity);
        return new ResponseEntity<>(editedUnit, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UnitEntity> remove(Long id) {
      unitService.remove(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping
    public List<UnitEntity> getUnit() {
        return unitService.get();
    }
}
