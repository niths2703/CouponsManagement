package com.example.CouponsManagement.Service.impl;

import com.example.CouponsManagement.DTOs.Response.CouponsResponse;
import com.example.CouponsManagement.Entity.Coupon;
import com.example.CouponsManagement.Repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CouponCronService {

    @Autowired
    private CouponRepository couponRepository;

    @Scheduled(cron="0 0 6 * * *")
    public void deactivateExpiredCoupons(){
        List<Coupon> coupons = couponRepository.findAll();
        coupons.forEach(coupon -> {
            if(LocalDateTime.now().isAfter(coupon.getExpiresAt())){
                coupon.setActive(false);
            }
        });
        couponRepository.saveAll(coupons);
    }
}
