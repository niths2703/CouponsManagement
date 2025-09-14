package com.example.CouponsManagement.DTOs.Response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class CouponsResponse {
    List<CouponResponse> coupons;
}
