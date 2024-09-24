package com.example.demo.Unit;

import com.example.demo.DTO.UnitNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UnitService {
    private final UnitRepository unitRepository;

    @Transactional
    public UnitEntity add(UnitEntity entity) {
        return unitRepository.save(entity);
    }

    @Transactional
    public UnitEntity edit(UnitEntity unitEntity) throws UnitNotFoundException {
        Long unitId = unitEntity.getId();
        if (!unitRepository.existsById(unitId)) {
            throw new UnitNotFoundException("not found unit");
        }
        return unitRepository.save(unitEntity);
    }

    @Transactional
    public Optional<UnitEntity> remove(Long id) {
        return unitRepository.findById(id)
                .map(unit -> {
                    unitRepository.delete(unit);
                    return unit;
                });

    }

    @Transactional
    public List<UnitEntity> get() {
        return unitRepository.findAll();
    }
}
