package com.example.CouponsManagement.DTOs.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItems {
    private Long productId;
    private Integer quantity;
    private Long price;
    private String brand;
}
