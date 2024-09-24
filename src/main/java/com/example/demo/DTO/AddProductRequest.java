package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter

    public class AddProductRequest {
        private Long productId;
        private Long ilosc;
        private Long uzytkownikId;

    }
