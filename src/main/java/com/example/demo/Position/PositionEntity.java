package com.example.demo.Position;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "position")
public class PositionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}