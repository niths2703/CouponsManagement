package com.example.CouponsManagement.Service;

import com.example.CouponsManagement.DTOs.Request.CouponRequest;
import com.example.CouponsManagement.DTOs.Request.CouponUpdateRequest;
import com.example.CouponsManagement.DTOs.Response.CouponResponse;
import com.example.CouponsManagement.DTOs.Response.CouponsResponse;

public interface CouponService {
    CouponResponse createCoupon(CouponRequest couponRequest);

    CouponsResponse getAllCoupons();

    CouponResponse findCouponById(Long id);

    CouponResponse updateCouponById(Long id, CouponUpdateRequest couponUpdateRequest);

    CouponResponse deleteCouponById(Long id);
}
