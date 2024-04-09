package com.example.application.model.API;

import com.example.application.model.phpmodel.ResponsModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APISQL {
    @FormUrlEncoded
    @POST("select_login.php")
    Call<ResponsModel> sendLoginUser(@Field("username_log") String username_log,
                                     @Field("passwords_log") String passwords_log,
                                     @Field("radiogroup_log") String radiogroup_log);

    @Multipart
    @POST("insert_identitas2.php")
    Call<ResponsModel> sendBiodata1(@Part("namatempat") RequestBody namatempat,
                                    @Part("deskripsi") RequestBody deskripsi,
                                    @Part("waktu") RequestBody waktu,
                                    @Part("alamat") RequestBody alamat,
                                    @Part("harga") RequestBody harga,
                                    @Part("persyaratan") RequestBody persyaratan,
                                    @Part("jenistempat") RequestBody jenistempat,
                                    @Part("key") RequestBody key,
                                    @Part MultipartBody.Part image,
                                    @Part("latitude") RequestBody latitude,
                                    @Part("longitude") RequestBody longitude);

    @FormUrlEncoded
    @POST("insert_identitas1.php")
    Call<ResponsModel> sendBiodata(@Field("fullnames") String fullnames,
                                   @Field("ages") String ages,
                                   @Field("usernames") String usernames,
                                   @Field("passwords") String passwords,
                                   @Field("radiogroups") String radiogroups,
                                   @Field("gender") String gender,
                                   @Field("alam") String alam,
                                   @Field("budaya") String budaya,
                                   @Field("buatan") String buatan);

    @FormUrlEncoded
    @POST("insert_identitas_provider1.php")
    Call<ResponsModel> sendBiodataProvider(@Field("fullnames") String fullnames,
                                   @Field("ages") String ages,
                                   @Field("usernames") String usernames,
                                   @Field("passwords") String passwords,
                                   @Field("radiogroups") String radiogroups,
                                   @Field("gender") String gender,
                                   @Field("asal") String asal,
                                   @Field("hp") String hp);


    @FormUrlEncoded
    @POST("naivebayes.php")
    Call<ResponsModel> sendNaive(@Field("keyy") String keyy);

    @GET("select_tempatwisata.php")
    Call<ResponsModel> getBiodata();

    @FormUrlEncoded
    @POST("detail_marker_budaya.php")
    Call<ResponsModel> getDetailMarker(@Field("Nama_tempat") String Nama_tempat);

    @GET("select_tempatwisata1.php")
    Call<ResponsModel> getBiodata1();

    @GET("select_tempatwisata_all.php")
    Call<ResponsModel> getBiodata2();

    @FormUrlEncoded
    @POST("select_tempatwisata_admin.php")
    Call<ResponsModel> sendadmin(@Field("key") String key);

    @Multipart
    @POST("update_tempat.php")
    Call<ResponsModel> sendUpdateTempat(@Part("namatempat") RequestBody namatempat,
                                        @Part("namatempat1") RequestBody namatempat1,
                                        @Part("deskripsi") RequestBody deskripsi,
                                        @Part("waktu") RequestBody waktu,
                                        @Part("alamat") RequestBody alamat,
                                        @Part("harga") RequestBody harga,
                                        @Part("persyaratan") RequestBody persyaratan,
                                        @Part("jenis_tempat") RequestBody jenis_tempat,
                                        @Part("key") RequestBody key);
    @Multipart
    @POST("update_gambar_tempat.php")
    Call<ResponsModel> sendUpdateTempat1(@Part("key") RequestBody key,
                                         @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("delete_tempat.php")
    Call<ResponsModel> sendDeleteTempat(@Field("key") String key);

    @FormUrlEncoded
    @POST("boking_tempat.php")
    Call<ResponsModel> bokingTempat(@Field("key1") String key1,
                                    @Field("key2") String key2);

    @FormUrlEncoded
    @POST("cih.php")
    Call<ResponsModel> cih(@Field("key1") String key1,
                           @Field("Lat") String Lat,
                           @Field("Lang") String Lang);

    @FormUrlEncoded
    @POST("bokingan_akhir.php")
    Call<ResponsModel> akhir(@Field("key1") String key1,
                             @Field("key2") String key2);

    @FormUrlEncoded
    @POST("bokingan_tempat.php")
    Call<ResponsModel> bokingan(@Field("key1") String key1);


    @FormUrlEncoded
    @POST("cancel_booking.php")
    Call<ResponsModel> cancelBooking(@Field("key1") String key1,
                                    @Field("key2") String key2);

    @FormUrlEncoded
    @POST("monitoring.php")
    Call<ResponsModel> monitoringAdmin(@Field("key1") String key1,
                                     @Field("key2") String key2);

    @FormUrlEncoded
    @POST("delete_bokingan.php")
    Call<ResponsModel> deleteBooking(@Field("key1") String key1);

    @FormUrlEncoded
    @POST("simpan_data_training.php")
    Call<ResponsModel> data_training(@Field("key1") String key1);
}
