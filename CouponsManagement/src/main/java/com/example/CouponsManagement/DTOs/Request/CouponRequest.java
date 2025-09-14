package com.example.CouponsManagement.DTOs.Request;

import com.example.CouponsManagement.Enums.CouponType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponRequest {
    CouponType type;
    String details;
}
