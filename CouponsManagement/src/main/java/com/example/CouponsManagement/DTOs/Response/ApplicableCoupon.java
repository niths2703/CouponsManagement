package com.example.CouponsManagement.DTOs.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ApplicableCoupon {
    private Long couponId;
    private String couponType;
    private Long discount;
    private Integer shippingCharges;
}
