package com.example.CouponsManagement.Repository;

import com.example.CouponsManagement.Entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

       Optional<Coupon> findByType(String type);

       Optional<Coupon> findByIdAndActive(Long id, boolean active);

        @Query(value = "SELECT * FROM coupon c " +
                "WHERE c.coupon_type = :type " +
                "AND :cartTotal < (c.details->>'threshold')::int",
                nativeQuery = true)
        Optional<Coupon> findByTypeAndCartTotalLessThanThreshold(
                @Param("type") String type,
                @Param("cartTotal") long cartTotal
        );


        @Query(value = "SELECT * FROM coupon c " +
                "WHERE c.coupon_type = :type " +
                "AND c.details->>'brand' = :brand " +
                "LIMIT 1",
                nativeQuery = true)
        Optional<Coupon> findByCouponTypeAndBrand(
                @Param("type") String type,
                @Param("brand") String brand
        );


}

