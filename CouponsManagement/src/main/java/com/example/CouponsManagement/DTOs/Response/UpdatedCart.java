package com.example.CouponsManagement.DTOs.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatedCart {
   private List<CartItemsResponse> items;
   private Long totalPrice;
   private Long totalDiscount;
   private Long finalPrice;
   private Integer shippingCharges;
}
