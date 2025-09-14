package com.example.CouponsManagement.Controller;

import com.example.CouponsManagement.DTOs.Request.CartRequest;
import com.example.CouponsManagement.DTOs.Response.ApplicableCouponsResponse;
import com.example.CouponsManagement.DTOs.Response.ApplyCouponResponse;
import com.example.CouponsManagement.DTOs.Response.CouponsResponse;
import com.example.CouponsManagement.Exception.CouponException;
import com.example.CouponsManagement.Service.CouponApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CouponApplicationController {

    @Autowired
    CouponApplicationService couponApplicationService;

    @PostMapping("/applicable-coupons")
    public ResponseEntity<ApplicableCouponsResponse> getCoupons(@RequestBody CartRequest cartRequest) {
        try {
            return ResponseEntity.ok(couponApplicationService.applicableCouponsResponse(cartRequest));
        } catch (Exception exception) {
            throw new CouponException("Exception occurred while creating new Coupon");
        }
    }

    @PostMapping("/apply-coupon/{id}")
    public ResponseEntity<ApplyCouponResponse> applyCoupon(@PathVariable Integer id, @RequestBody CartRequest cartRequest) {
        try {
            return null;
            //return ResponseEntity.ok(couponApplicationService.applicableCouponsResponse(cartRequest));
        } catch (Exception exception) {
            throw new CouponException("Exception occurred while creating new Coupon");
        }
    }

}
