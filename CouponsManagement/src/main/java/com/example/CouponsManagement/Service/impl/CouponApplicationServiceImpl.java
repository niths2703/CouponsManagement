package com.example.CouponsManagement.Service.impl;

import com.example.CouponsManagement.DTOs.Request.CartItems;
import com.example.CouponsManagement.DTOs.Request.CartRequest;
import com.example.CouponsManagement.DTOs.Response.*;
import com.example.CouponsManagement.Entity.Coupon;
import com.example.CouponsManagement.Exception.CouponException;
import com.example.CouponsManagement.Repository.CouponRepository;
import com.example.CouponsManagement.Service.CouponApplicationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CouponApplicationServiceImpl implements CouponApplicationService {
    @Autowired
    private CouponRepository couponRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${buy.array.start}")
    private Integer buyArrayStart;
    @Value("${buy.array.end}")
    private Integer buyArrayEnd;
    @Value("${get.array.start}")
    private Integer getArrayStart;
    @Value("${get.array.end}")
    private Integer getArrayEnd;

    public ApplicableCouponsResponse applicableCouponsResponse(CartRequest cartRequest){
        Long cartTotal = cartRequest.getCart().getItems().stream()
                .mapToLong(i -> i.getPrice())
                .sum();

        //CART - WISE COUPON
        Optional<Coupon> cartWiseCouponOptional= couponRepository.findByTypeAndCartTotalLessThanThreshold("CART", cartTotal );
        if(cartWiseCouponOptional.isEmpty()){
            throw new CouponException("Cart Coupon not found for this request");
        }
        Coupon cartWiseCoupon = cartWiseCouponOptional.get();
        int cartDiscountPercent = extractDiscountPercent(cartWiseCoupon);
        long cartDiscount = (cartTotal * cartDiscountPercent) / 100;



        ///PRODUCT WISE COUPON
        String maxBrand = findMaximumFrequencyBrand(cartRequest.getCart().getItems());
        Optional<Coupon> productCouponOptional = couponRepository.findByCouponTypeAndBrand("PRODUCT", maxBrand);
        if(productCouponOptional.isEmpty()){
            throw new CouponException("Coupon not found with product-wise type for this request");
        }
        Coupon productWiseCoupon = productCouponOptional.get();
        int productDiscountPercent = extractDiscountPercent(productWiseCoupon);
        Long productWiseTotal= calculateProductWiseDiscountTotal(cartRequest.getCart().getItems(), maxBrand,productDiscountPercent);
        Long productDiscount = cartTotal -productWiseTotal;


        //BxGy Coupon
        List<CartItems> getArrayItems = cartRequest.getCart().getItems().stream().filter(cartItem -> cartItem.getProductId()>= buyArrayStart && cartItem.getProductId()<=buyArrayEnd).collect(Collectors.toList());
       String maxBuyArrayBrand = findMaximumFrequencyBrand(getArrayItems);
        Optional<Coupon> bXgYCouponOptional = couponRepository.findByType("BXGY");

        Coupon bXgYCoupon = bXgYCouponOptional.get();
               Long bXgYTotal =calculateB2G1DiscountTotal(cartRequest.getCart().getItems(), maxBuyArrayBrand);
        long bXgYDiscount = cartTotal - bXgYTotal;



        ///// Building Final Response

        ApplicableCoupon appCartCoupon = ApplicableCoupon.builder().couponId(cartWiseCoupon.getId())
                .couponType(cartWiseCoupon.getType().getValue()).discount(cartDiscount).shippingCharges(0).build();

        ApplicableCoupon appProductCoupon = ApplicableCoupon.builder().couponId(productWiseCoupon.getId())
                .couponType(productWiseCoupon.getType().getValue()).discount(productDiscount).shippingCharges(100).build();


        ApplicableCoupon appBxGyCoupon = ApplicableCoupon.builder().couponId(bXgYCoupon.getId())
                .couponType(bXgYCoupon.getType().getValue()).discount(bXgYDiscount).shippingCharges(0).build();


        List<ApplicableCoupon> applicableCoupons = new ArrayList<>();
        applicableCoupons.add(appCartCoupon);
        applicableCoupons.add(appProductCoupon);
        applicableCoupons.add(appBxGyCoupon);
        return ApplicableCouponsResponse.builder().coupons(applicableCoupons).build();
    }

    private String findMaximumFrequencyBrand(List<CartItems> cartItems){
        LinkedHashMap<String, Integer> brandFrequencyMap = new LinkedHashMap<>();
        cartItems.stream().forEach(cartItem -> {
            if(brandFrequencyMap.get(cartItem.getBrand())==null){
                brandFrequencyMap.put(cartItem.getBrand(),1);
            }
            else{
                int frequency= brandFrequencyMap.get(cartItem.getBrand());
                brandFrequencyMap.put(cartItem.getBrand(),++frequency);
            }
        });
        Set<String> brands = brandFrequencyMap.keySet();
        String maxBrand= "";
        int maxFrequency=0;
        for(String b: brands){
            int v = brandFrequencyMap.get(b);
            if(maxFrequency<v){
                maxFrequency = v;
                maxBrand = ""+b;
            }
        }
        return maxBrand;
    }

    private int extractDiscountPercent(Coupon coupon) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(coupon.getDetails());

            // details is nested inside root
            return root.path("details").path("discount").asInt();
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse discount from JSON", e);
        }
    }

    private long calculateProductWiseDiscountTotal(List<CartItems> cartItems, String brand, Integer percent) {
        long discountedTotal=0;
        for(CartItems cartItem : cartItems){
            if(cartItem.getBrand().equals(brand)){
                discountedTotal+= cartItem.getPrice()*(100-percent)/100;
            }
            else{
                discountedTotal+= cartItem.getPrice();
            }
        }
        return discountedTotal;
    }

    private long calculateB2G1DiscountTotal(List<CartItems> cartItems, String brand){
        long discountedTotal=0;
        int buy=0;
        for(CartItems cartItem : cartItems){
            if(cartItem.getBrand().equals(brand) && cartItem.getProductId()>= buyArrayStart && cartItem.getProductId()<=buyArrayEnd){
                buy++;
            }
        }
        int get=buy/2;
        for(CartItems cartItem : cartItems){
            if(get>0&&cartItem.getBrand().equals(brand) && cartItem.getProductId()>= getArrayStart && cartItem.getProductId()<=getArrayEnd){
                get--;
            }
            else{
                discountedTotal+=cartItem.getPrice();
            }
        }
        return discountedTotal;
    }

    public ApplyCouponResponse applyCoupon(Long id, CartRequest cartRequest){
        Long cartTotal = cartRequest.getCart().getItems().stream()
                .mapToLong(i -> i.getPrice())
                .sum();

        Optional<Coupon> couponOptional = couponRepository.findByIdAndActive(id,true);
        if(couponOptional.isEmpty()){
            throw new CouponException("No Coupon found with id : "+ id + " Or Coupon has expired");
        }

        Coupon appliedCoupon = couponOptional.get();
        UpdatedCart updatedCart = new UpdatedCart();
        if(appliedCoupon.getType().getValue().equals("CART")){
            int cartDiscountPercent = extractDiscountPercent(appliedCoupon);
            long cartDiscount = (cartTotal * cartDiscountPercent) / 100;
            updatedCart = updateCartForCartWise(cartRequest.getCart().getItems(), cartTotal,cartTotal-cartDiscount);
            return ApplyCouponResponse.builder().updatedCart(updatedCart).build();
        } else if (appliedCoupon.getType().equals("PRODUCT")) {
            String maxBrand = findMaximumFrequencyBrand(cartRequest.getCart().getItems());
            int productDiscountPercent = extractDiscountPercent(appliedCoupon);
            long productWiseTotal= calculateProductWiseDiscountTotal(cartRequest.getCart().getItems(), maxBrand,productDiscountPercent);
            updatedCart = updateCartForProductWise(cartRequest.getCart().getItems(), cartTotal, productWiseTotal);
            return ApplyCouponResponse.builder().updatedCart(updatedCart).build();
        }
        else if (appliedCoupon.getType().equals("BXGY")) {
            List<CartItems> getArrayItems = cartRequest.getCart().getItems().stream().filter(cartItem -> cartItem.getProductId()>= buyArrayStart && cartItem.getProductId()<=buyArrayEnd).collect(Collectors.toList());
            String maxBuyArrayBrand = findMaximumFrequencyBrand(getArrayItems);
            Long bXgYTotal =calculateB2G1DiscountTotal(cartRequest.getCart().getItems(), maxBuyArrayBrand);
            updatedCart = updateCartForBxGy(cartRequest.getCart().getItems(), cartTotal, bXgYTotal);
            return ApplyCouponResponse.builder().updatedCart(updatedCart).build();
        }
        else{
            throw new CouponException("No Such Coupon Type");
        }
    }

    private UpdatedCart updateCartForCartWise(List<CartItems> cartItems, long totalPrice, long finalPrice){
        List<CartItemsResponse> cartItemsResponses = objectMapper.convertValue(cartItems,List.class);
      return UpdatedCart.builder().items(cartItemsResponses).totalPrice(totalPrice).finalPrice(finalPrice)
              .totalDiscount(totalPrice - finalPrice).shippingCharges(0).build();
    }

    private UpdatedCart updateCartForProductWise(List<CartItems> cartItems, long totalPrice, long finalPrice){
        List<CartItemsResponse> cartItemsResponses = objectMapper.convertValue(cartItems,List.class);
        return UpdatedCart.builder().items(cartItemsResponses).totalPrice(totalPrice).finalPrice(finalPrice)
                .totalDiscount(totalPrice - finalPrice).shippingCharges(100).build();
    }

    private UpdatedCart updateCartForBxGy(List<CartItems> cartItems, long totalPrice, long finalPrice){
        List<CartItemsResponse> cartItemsResponses = objectMapper.convertValue(cartItems,List.class);
        return UpdatedCart.builder().items(cartItemsResponses).totalPrice(totalPrice).finalPrice(finalPrice)
                .totalDiscount(totalPrice - finalPrice).shippingCharges(100).build();
    }

}
