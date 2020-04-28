package com.huriati.project.tvkabel.api;


import com.huriati.project.tvkabel.model.HistoryResponse;
import com.huriati.project.tvkabel.model.PelangganResponse;
import com.huriati.project.tvkabel.model.Tagihan;
import com.huriati.project.tvkabel.model.TagihanResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface BaseApiService {
    @FormUrlEncoded
    @POST("auth/login")
    Call<ResponseBody> loginRequest(@Field("email") String email,
                                    @Field("password") String password);
    @GET("api-pelanggan/view")
    Call<TagihanResponse> tagihanRequest(@Query("id") String id);

    @GET("api-pelanggan/total")
    Call<ResponseBody> totalRequest(@Query("id") String id);

    @GET("api-pelanggan/history")
    Call<HistoryResponse> historyRequest(@Query("id") String id,
                                         @Query("pelanggan_id") String pelanggan_id);

    @FormUrlEncoded
    @POST("api-pelanggan/create")
    Call<ResponseBody> kolektorRequest(@Field("kolektor_id") String kolektor_id,
                                       @Field("tanggal") String tanggal);
    @FormUrlEncoded
    @POST("api-pelanggan/create-detail")
    Call<ResponseBody> tagihanDetailRequest(@Field("tagihan_kolektor_id") String tagihan_kolektor_id,
                                      @Field("tagihan_pelanggan_id") String tagihan_pelanggan_id,
                                      @Field("pelanggan_id") String pelanggan_id,
                                      @Field("tgl_tagihan") String tgl_tagihan,
                                      @Field("bulan_tagihan") String bulan_tagihan,
                                      @Field("jumlah_pembayaran") int jumlah_pembayaran);

    @GET("employee/salary ")
    Call<ResponseBody> salaryRequest(@Header("token") String token);

    @GET("api-pelanggan/index")
    Call<PelangganResponse> pelangganRequest(@Query("wilayah") String wilayah, @Query("page") int page);

    @GET("api-pelanggan/index")
    Call<PelangganResponse> pelangganRequest(@Query("id") String id, @Query("wilayah") String wilayah, @Query("page") int page);

    @GET("employee/print")
    Call<ResponseBody> printRequest();

    @Streaming
    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlAsync(@Url String fileUrl);
}
