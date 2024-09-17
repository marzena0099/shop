package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Getter
@Setter

    public class AddProductRequest {
        private Long productId;
        private int ilosc;
        private Long uzytkownikId;

    }
