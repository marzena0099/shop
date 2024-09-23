package com.example.demo.Unit;

import lombok.AllArgsConstructor;
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
    public UnitEntity add(@RequestBody UnitEntity entity){
        return unitService.add(entity);
    }
    @PostMapping("/{id}")
    public ResponseEntity<UnitEntity> edit(@PathVariable Long id, @RequestBody UnitEntity entity){
        return unitService.edit(id, entity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
@DeleteMapping("/{id}")
    public ResponseEntity<UnitEntity> remove(Long id){
        return unitService.remove(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
}
@GetMapping
    public List<UnitEntity> getUnit(){
        return unitService.get();
}
}
