package com.example.vaiterapp.API;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface API {

    @FormUrlEncoded
    @POST("customerRegistration.php")
    Call<ResponseBody> createCustomer(
            @Field("FirstName") String fname,
            @Field("LastName")  String lname,
            @Field("Email") String email,
            @Field("Password")  String pass
    );

    @FormUrlEncoded
    @POST("customerLogin.php")
    Call<LoginResponse> customerLogin(
            @Field("Email") String email,
            @Field("Password")  String pass
    );

    @FormUrlEncoded
    @POST("staffLogin.php")
    Call<LoginResponse> staffLogin(
            @Field("Email") String email,
            @Field("Password")  String pass
    );

    @FormUrlEncoded
    @POST("staffRegistration.php")
    Call<ResponseBody> createStaff(
            @Field("FirstName") String fname,
            @Field("LastName")  String lname,
            @Field("Email") String email,
            @Field("RestaurantName") String rname,
            @Field("Password")  String pass
    );

    @FormUrlEncoded
    @POST("Meals.php")
    Call<ResponseBody> getMeals(
            @Field("RestaurantName") String fname
    );

    @FormUrlEncoded
    @POST("createOrder.php")
    Call<ResponseBody> createOrder(
            @Field("MealName") String mname,
            @Field("DateTime") String dtime,
            @Field("CusID") int id
    );

    @FormUrlEncoded
    @POST("getCustomerCurrentOrders.php")
    Call<ResponseBody> showCustomerCurrentOrders(
            @Field("CusID") int id
    );

    @FormUrlEncoded
    @POST("getCustomerOrderHistory.php")
    Call<ResponseBody> showCustomerPastOrders(
            @Field("CusID") int id
    );

    @FormUrlEncoded
    @POST("getStaffCurrentOrders.php")
    Call<ResponseBody> showStaffCurrentOrders(
            @Field("staffID") int id
    );

    @FormUrlEncoded
    @POST("getStaffOrderHistory.php")
    Call<ResponseBody> showStaffPastOrders(
            @Field("staffID") int id
    );

}
