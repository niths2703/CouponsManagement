package com.example.CouponsManagement.Enums;


public enum CouponType {
        CART_WISE("CART"),
        PRODUCT_WISE("PRODUCT"),
        BXGY("BXGY");

        private final String value;

        CouponType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
}


