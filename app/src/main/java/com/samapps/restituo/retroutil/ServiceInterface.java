package com.samapps.restituo.retroutil;


import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ServiceInterface {

    // method,, return type ,, secondary url
    @Multipart
    @POST("restituo/init_Transaction.php")
    Call<Token_Res> generateTokenCall(
            @Part("code") RequestBody language,
            @Part("MID") RequestBody mid,
            @Part("ORDER_ID") RequestBody order_id,
            @Part("AMOUNT") RequestBody amount
    );
}