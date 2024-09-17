package com.example.demo.Unit;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UnitService {
    private final UnitRepository unitRepository;

    public UnitEntity add(UnitEntity entity) {
        return unitRepository.save(entity);
    }
@Transactional
    public Optional<UnitEntity> edit(Long id, UnitEntity unitEntity) {
        Optional<UnitEntity> opt = unitRepository.findById(id);
        if(!opt.isPresent()){
            return Optional.empty();
        }
        else{
            UnitEntity u = opt.get();
            u.setCity(unitEntity.getCity());
            u.setEmployee(unitEntity.getEmployee());
            u.setDepartment(unitEntity.getDepartment());
            unitRepository.save(u);
            return Optional.of(u);
        }
    }
@Transactional
    public Optional<UnitEntity> remove(Long id) {
        return unitRepository.findById(id)
                .map(unit->{
                    unitRepository.delete(unit);
                    return unit;
                });

    }

    public List<UnitEntity> get() {
    return unitRepository.findAll();
    }
}
