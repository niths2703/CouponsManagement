package com.example.CouponsManagement.Service.impl;

import com.example.CouponsManagement.DTOs.Request.CouponRequest;
import com.example.CouponsManagement.DTOs.Request.CouponUpdateRequest;
import com.example.CouponsManagement.DTOs.Response.CouponResponse;
import com.example.CouponsManagement.DTOs.Response.CouponsResponse;
import com.example.CouponsManagement.Entity.Coupon;
import com.example.CouponsManagement.Exception.CouponException;
import com.example.CouponsManagement.Repository.CouponRepository;
import com.example.CouponsManagement.Service.CouponService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    public CouponResponse createCoupon(CouponRequest couponRequest){
        Coupon coupon = Coupon.builder()
                .type(couponRequest.getType()).details(couponRequest.getDetails())
                .active(true).createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(60)).build();
        coupon = couponRepository.save(coupon);
        return CouponResponse.builder().id(coupon.getId())
                .type(coupon.getType()).detailsJson(couponRequest.getDetails())
                .active(true).expiresAt(coupon.getExpiresAt())
                .build();
    }

    public CouponsResponse getAllCoupons(){
        List<Coupon> coupons= couponRepository.findAll();
        List<CouponResponse> couponResponses = objectMapper.convertValue(coupons,List.class);
        return CouponsResponse.builder()
                .coupons(couponResponses).build();
    }

    public CouponResponse findCouponById(Long id){
        Optional<Coupon> couponOptional = couponRepository.findById(id);
        if(couponOptional.isEmpty()){
            throw new CouponException("Coupon not present with id : " + id);
        }
        Coupon coupon = couponOptional.get();
        return CouponResponse.builder().id(id).type(coupon.getType()).active(coupon.isActive())
                .detailsJson(coupon.getDetails()).expiresAt(coupon.getExpiresAt())
                .build();
    }

    public CouponResponse updateCouponById(Long id, CouponUpdateRequest couponUpdateRequest){
        Optional<Coupon> couponOptional = couponRepository.findById(id);
        if(couponOptional.isEmpty()){
            throw new CouponException("Coupon not present with id : " + id);
        }
        Coupon oldCoupon = couponOptional.get();
        Coupon newCoupon = oldCoupon.toBuilder().type(couponUpdateRequest.getType())
                .details(couponUpdateRequest.getDetailsJson()).updatedAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(60)).build();
        couponRepository.save(newCoupon);
        return CouponResponse.builder().id(id).type(newCoupon.getType()).active(newCoupon.isActive())
                .detailsJson(newCoupon.getDetails()).expiresAt(newCoupon.getExpiresAt())
                .build();
    }

    public CouponResponse deleteCouponById(Long id){
        Optional<Coupon> couponOptional = couponRepository.findById(id);
        if(couponOptional.isEmpty()){
            throw new CouponException("Coupon not present with id : " + id);
        }
        Coupon coupon = couponOptional.get();
        couponRepository.delete(coupon);
        return CouponResponse.builder().id(id).type(coupon.getType()).active(coupon.isActive())
                .detailsJson(coupon.getDetails()).expiresAt(coupon.getExpiresAt())
                .build();
    }
}

