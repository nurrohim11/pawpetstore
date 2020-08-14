package com.asus.ecommerceapp.Networking;

import com.asus.ecommerceapp.BuildConfig;
import com.google.gson.JsonObject;
import com.asus.ecommerceapp.model.Login.LoginRequest;
import com.asus.ecommerceapp.model.Login.RequestResponse;
import com.asus.ecommerceapp.model.Pelanggan.Pelanggan;
import com.asus.ecommerceapp.model.about.ResponseAbout;
import com.asus.ecommerceapp.model.banner.BannerResponse;
import com.asus.ecommerceapp.model.grooming.HistoryGroomingResponse;
import com.asus.ecommerceapp.model.grooming.OrderGrooming;
import com.asus.ecommerceapp.model.keranjang.KeranjangResponse;
import com.asus.ecommerceapp.model.keranjang.ResponseHapusKeranjang;
import com.asus.ecommerceapp.model.konfirmasi.Konfirmasi;
import com.asus.ecommerceapp.model.localnotification.ResponseNotif;
import com.asus.ecommerceapp.model.penitipan.HistoryPenitipanResponse;
import com.asus.ecommerceapp.model.penitipan.OrderPenitipan;
import com.asus.ecommerceapp.model.penitipan.ResponseOrderPenitipan;
import com.asus.ecommerceapp.model.keranjang.Cart;
import com.asus.ecommerceapp.model.produk.Checkout;
import com.asus.ecommerceapp.model.keranjang.EditCart;
import com.asus.ecommerceapp.model.produk.HistoryProdukResponse;
import com.asus.ecommerceapp.model.produk.ItemOrderResponse;
import com.asus.ecommerceapp.model.produk.NoOrderResponse;
import com.asus.ecommerceapp.model.produk.ProdukDetailById;
import com.asus.ecommerceapp.model.produk.ResponseProduk;
import com.asus.ecommerceapp.model.keranjang.TotalKeranjang;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIInterface {
    @POST("login")
    Call<RequestResponse> getLoginStatus(@Body LoginRequest request);
//
    @POST("registerUser")
    Call<RequestResponse> registerUser(@Body Pelanggan pelanggan);

    @POST("edituser")
    Call<RequestResponse> editUser(@Body Pelanggan pelanggan);

    @GET("getDataUser/{id}")
    Call<Pelanggan> getUserData(@Path("id") String id);

    @POST("updateFcm")
    Call<Pelanggan> updateFCM(@Body JsonObject body);

    @GET("getProdukById/{id}")
    Call<ProdukDetailById> getProdukById(@Path("id") String id);

    @GET("getKeranjang/{id}")
    Call<KeranjangResponse> getKeranjang(@Path("id") String id);

    @GET("getTotalKeranjang/{id}")
    Call<TotalKeranjang> getTotal(@Path("id") String id);

    @POST("deleteKeranjang/{id}")
//    Call<ResponseHapusKeranjang> deleteKeranjang(KeranjangItem item);
    Call<ResponseHapusKeranjang> deleteKeranjang(@Path("id") String id);

    @POST("KeranjangBelanja")
    Call<RequestResponse> addToCart(@Body Cart cart);

    @GET("about")
    Call<ResponseAbout> about();

    @POST("editKeranjang")
    Call<RequestResponse> updateQty(@Body EditCart editCart);

    @POST("updateQtyBro")
    Call<RequestResponse> updateJumlah(@Body EditCart editCart);

    @POST("KonfirmasiPenitipan")
    Call<RequestResponse> konfirmasiPenitipan (@Body Konfirmasi konfirmasi);

    @POST("AyoCheckout")
    Call<RequestResponse> checkout(@Body Checkout checkout);

    @GET("getHistoryGrooming/{id}")
    Call<HistoryGroomingResponse> getHistory(@Path("id") String id);

    @GET("getHistoryProduk/{id}")
    Call<HistoryProdukResponse> getHistoryProduk(@Path("id") String id);

    @GET("getHistoryPenitipan/{id}")
    Call<HistoryPenitipanResponse> getHistoryPenitipan(@Path("id") String id);

    @GET("getPenitipanPending/{id}/{status}")
    Call<HistoryPenitipanResponse> getHistoryPenitipanPending(@Path("id") String id,@Path("status") String status);

    @GET("getGroomingPending/{id}/{status}")
    Call<HistoryGroomingResponse> getHistoryGroomingPending(@Path("id") String id, @Path("status") String status);

    @GET("getOrderProdukPending/{id}/{status}")
    Call<HistoryProdukResponse> getHistoryProdukPending(@Path("id") String id, @Path("status") String status);

    @POST("orderPenitipan")
    Call<ResponseOrderPenitipan> orderPenitipan(@Body OrderPenitipan orderPenitipan);

    @POST("orderGrooming")
    Call<ResponseOrderPenitipan> orderGrooming(@Body OrderGrooming orderGrooming);

    @GET("getBarang")
    Call<ResponseProduk> getBarang();

    @GET("getBanner")
    Call<BannerResponse> getBanner();

    @GET("NoOrderProduk")
    Call<NoOrderResponse> getNoOrder();

    @GET("itemGetOrderProduk/{no_order}")
    Call<ItemOrderResponse> getItemOrder(@Path("no_order") String no_order);
//    Call<RequestResponse> checkout(String id, String a);

    @GET("lastPesan/{id}")
    Call<ResponseNotif> getLstPesan (@Path("id") String id);

    public static String getProvinsi = BuildConfig.BASE_URL+"getProvinsi";

    public static String registerUser = BuildConfig.BASE_URL+"registerUser";

    public static String getKota = BuildConfig.BASE_URL+"getKota";

    public static String getKecamatan = BuildConfig.BASE_URL+"getKecamatan";

}
