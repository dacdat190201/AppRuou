package com.example.final_shopthucung.retrofit;

import com.example.final_shopthucung.adapter.RuouNgoaiAdapter;
import com.example.final_shopthucung.model.MessageModel;
import com.example.final_shopthucung.model.ProductModel;
import com.example.final_shopthucung.model.Ruou;
import com.example.final_shopthucung.model.RuouModel;


import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {
    //lấy dữ liệu loại
    @GET("getloaisp.php")
    Observable<ProductModel> getProduct();
    //lấy dữ liệu rượu
    @GET("getspmoi.php")
    Observable<RuouModel> getRuou();


    @POST("chitiet.php")
    @FormUrlEncoded
    Observable<RuouModel> getRuouNgoai(
        @Field("page") int page,
        @Field("idLoai") int loai
    );

    @POST("xoa.php")
    @FormUrlEncoded
    Observable<MessageModel> xoaSanPham(
            @Field("idRuou") int idRuou
    );

    @POST("insertsp.php")
    @FormUrlEncoded
    Observable<MessageModel> insertsp(
            @Field("tenRuou") String tenRuou,
            @Field("hinhanh") String hinhanh,
            @Field("xuatXu") String xuatXu,
            @Field("moTa") String moTa,
            @Field("giaBan") int giaBan,
            @Field("idLoai") int idLoai
    );

    @POST("updatesp.php")
    @FormUrlEncoded
    Observable<MessageModel> updatesp(
            @Field("tenRuou") String tenRuou,
            @Field("hinhanh") String hinhanh,
            @Field("xuatXu") String xuatXu,
            @Field("moTa") String moTa,
            @Field("giaBan") int giaBan,
            @Field("idLoai") int idLoai,
            @Field("idRuou") int idRuou
    );
}
