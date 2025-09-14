# CouponsManagement
# ===================
This is a backend application designed to manage and apply coupons for an ecommerce website or application. Admin can maintain creation/updating/deletion of the coupons through CRUD APIs. A user can access and avail the desired coupons by using user-application APIs and also select the type of coupon from Cart-Wise, Product- wise or BxGy Coupons.

Separate Json files are being maintained to list down the products. Buy and Get Array Product lists are maintained with Buy Array in higher price ranges and Get Array in lower price ranges as per business needs.

A seaprate cron job method is being used to deactivate the coupons in use after 2 months.

As mentioned, there are 3 types of Coupons being used here :

# 1. Cart-Wise Coupon :
For my application, I have considered three possible ranges of values for toatal value of cart:
# a) <= 5000 : Discount : 20%
# b) >5000 and <= 100000 : 30%
# c) > 100000 : 40 %

Only this type of coupon offers Free Shipping charges. 

# 2. Product-Wise Coupon :
Product-wise coupon will be applied brand-wise. The current APIs will get the maximum frequency of brand of products in the cart and apply the brand-discount on all those products and offer cart total for this use-case. A customized shipping charge will be added according cart total.

# 3. BxGy Coupons :
Buy and Get array products are maintained in Json files which are stored in static resources folder. Based on Buy number and Get number we can apply BxGy coupon and also offer a customized shiiping charge based on cart total. 

# Assumptions:
1. Range of Cart Total will not exceeed 5,00,000.
2. Only 40 products have been consiered in total in static JSON files with 4 brands. These values can be changed as per Admin needs.
3. Each brand of product has a fixed value of discount percentage they are offering on their products.
4. Only Admin can login for Coupon CRUD APIs which will be limited in number and maintained by the admin. Each coupon created can be valid for 2 months after which admin will be notified upon their deactivation.
5. Each customer in user table identified by a unique mobile number will have a fixed number of repetition limit (=2) for using BxGy coupons.

# Limitations :
1. Cart Total value can be considered maximum till maximum value of Java Long data type.

# Missed Scenarios :
1. For the product-wise coupon, the APIs are currently getting maximum frequency of brand of products in the cart and applying the brand-discount on all those products. There can be cases where lesser frequency brands can offfer lower cart value to the Customer and hence prove a better offer for him.
2. Currently, I have considered only 1 type of BxGy coupon which is B2G1. More such coupons can be considered and APIs can be customized according to Buy number and Get number of Products.
3. Shipping Charges are currently hardcoded for BxGy and Product-wise coupons. These charges can be customized according to range of final Cart Total.
4. Total Discount of Cart is visible in Response . Total discount per product has not been handled in the response for now.
5. User Login APIs need to be made for handling user roles. Admin will be able to manage CRUD APIs for Coupons. While a customer can see the applicable coupons for his cart and also apply the desired coupon by its id.

# Sample Table images :
<img width="722" height="198" alt="image" src="https://github.com/user-attachments/assets/69e8ed38-5c50-4d7a-9713-998fb71cd45a" />


