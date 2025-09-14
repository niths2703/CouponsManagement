package com.example.CouponsManagement.Controller;

import com.example.CouponsManagement.DTOs.Request.CouponRequest;
import com.example.CouponsManagement.DTOs.Request.CouponUpdateRequest;
import com.example.CouponsManagement.DTOs.Response.CouponResponse;
import com.example.CouponsManagement.DTOs.Response.CouponsResponse;
import com.example.CouponsManagement.Exception.CouponException;
import com.example.CouponsManagement.Service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    @Autowired
    private CouponService couponService;


    @PostMapping("")
    public ResponseEntity<CouponResponse> createCoupon(CouponRequest couponRequest) {
        try {
            return ResponseEntity.ok(couponService.createCoupon(couponRequest));
        } catch (Exception exception) {
            throw new CouponException("Exception occurred while creating new Coupon");
        }
    }

    @GetMapping("")
    public ResponseEntity<CouponsResponse> getCoupons() {
        try {
            return ResponseEntity.ok(couponService.getAllCoupons());
        } catch (Exception exception) {
            throw new CouponException("Exception occurred while creating new Coupon");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CouponResponse> getCouponById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(couponService.findCouponById(id));
        } catch (Exception exception) {
            throw new CouponException("Exception occurred while creating new Coupon");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CouponResponse> updateCouponById(@PathVariable Long id, @RequestBody CouponUpdateRequest couponUpdateRequest) {
        try {
            return ResponseEntity.ok(couponService.updateCouponById(id, couponUpdateRequest));
        } catch (Exception exception) {
            throw new CouponException("Exception occurred while creating new Coupon");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CouponResponse> deleteCouponById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(couponService.deleteCouponById(id));
        } catch (Exception exception) {
            throw new CouponException("Exception occurred while creating new Coupon");
        }
    }
}
