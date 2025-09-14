package com.example.CouponsManagement.DTOs.Request;

import com.example.CouponsManagement.Enums.CouponType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponUpdateRequest {
    private CouponType type;
    private String detailsJson;
    private LocalDateTime expiresAt;
}
