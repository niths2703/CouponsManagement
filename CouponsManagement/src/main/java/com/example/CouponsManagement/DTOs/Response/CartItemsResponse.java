package com.example.CouponsManagement.DTOs.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemsResponse {
    private Long productId;
    private Integer quantity;
    private Long price;
    private String brand;
    private Long totalDiscount;
}
