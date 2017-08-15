package com.wifi.yilong.yilongwifi.Http.rest.retrofitService;

import com.wifi.yilong.yilongwifi.Http.rest.model.Location;
import com.wifi.yilong.yilongwifi.Http.rest.model.Review;
import com.wifi.yilong.yilongwifi.Http.rest.model.User;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/2/12.
 */

public interface RetrofitServiceAPI {
//    private static final String BaseUrl = "http://www.yibaoruanzipao.online";
//    public static final String SignUp = BaseUrl + "/api/register/";
//    public static final String Signin = BaseUrl + "/api/login/";
//    public static final String GetLocations = BaseUrl + "/api/locations";
//    public static final String GetLocationDetail = BaseUrl + "/api/locations/";
//    public static final String AddReview = BaseUrl + "/api/locations/";
//
//    public static final String BEARER = "Bearer ";
//    public static final String AUTHORIZATION = "Authorization";

    @FormUrlEncoded
    @POST("/api/login/")
    Observable<User> signin(@Field("email") String email , @Field("password") String pw);

    @FormUrlEncoded
    @POST("/api/login/")
    Call<User> signinWithRt(@Field("email") String email , @Field("password") String pw);
    @FormUrlEncoded
    @POST("/api/register/")
    Observable<User> signup(@Field("email") String email , @Field("password") String pw , @Field("name") String name);

    @GET("/api/locations/")
    Observable<List<Location>> getLocations(@QueryMap Map<String , String> querys);
    @GET("/api/locations/{id}")
    Observable<Location> getLocationById(@Path("id") String id);

    //add review need auth token
    @FormUrlEncoded
    @POST("/api/locations/{locationid}/reviews")
    Observable<Review> addReview(@Path("locationid") String locationid ,
                                 @Field("rating") float rating ,
                                 @Field("reviewText") String reviewText );
}
