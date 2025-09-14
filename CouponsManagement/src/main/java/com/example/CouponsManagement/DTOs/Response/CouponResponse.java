package com.example.CouponsManagement.DTOs.Response;

import com.example.CouponsManagement.Enums.CouponType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponResponse {
    private Long id;
    private CouponType type;
    private String detailsJson;
    private boolean active;
    private LocalDateTime expiresAt;
}
