package com.example.CouponsManagement.Service;

import com.example.CouponsManagement.DTOs.Request.CartRequest;
import com.example.CouponsManagement.DTOs.Response.ApplicableCouponsResponse;

public interface CouponApplicationService {
    ApplicableCouponsResponse applicableCouponsResponse(CartRequest cartRequest);
}
