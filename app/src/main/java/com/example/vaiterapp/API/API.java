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


}
