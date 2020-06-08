package com.example.vaiterapp;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface API {

    @FormUrlEncoded
    @POST("registration.php")
    Call<ResponseBody> createCustomer(
            @Field("FirstName") String fname,
            @Field("LastName")  String lname,
            @Field("Email") String email,
            @Field("Password")  String pass
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> userLogin(
            @Field("Email") String email,
            @Field("Password")  String pass
    );
}
