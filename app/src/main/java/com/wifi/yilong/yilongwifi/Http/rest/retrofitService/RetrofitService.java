package com.wifi.yilong.yilongwifi.Http.rest.retrofitService;

import android.content.Context;

import com.wifi.yilong.yilongwifi.Http.rest.model.Location;
import com.wifi.yilong.yilongwifi.Http.rest.model.Review;
import com.wifi.yilong.yilongwifi.Http.rest.model.User;
import com.wifi.yilong.yilongwifi.Infrastructure.MyLog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/2/12.
 */

public class RetrofitService {

    public static final String baseUrl = "http://www.yibaoruanzipao.online";
    private static  RetrofitService mRetrofitService;
    @Inject
    Retrofit mRetrofit;
    private Map<String , Call<?>> callList = new HashMap<>();
    private Context mContext;

    public static RetrofitService get(Context context , Retrofit retrofit){
        if(mRetrofitService == null){
            mRetrofitService = new RetrofitService(context , retrofit);

//            ((AppController)context.getApplicationContext()).getCommonComponent().inject(mRetrofitService);

        }

        return mRetrofitService;
    }


    private RetrofitService(Context context , Retrofit retrofit){
        mContext = context;
        mRetrofit = retrofit;
    }


    public void cancelRequest(String tag){
        if(callList.containsKey(tag)){
            callList.get(tag).cancel();
        }
    }
    public Observable<User> signup( String email , String pw ,String name ){
        RetrofitServiceAPI api = mRetrofit.create(RetrofitServiceAPI.class);
        Observable<User> observable = api.signup(  email, pw ,name);
        return observable;
    }
    public Observable<User> sigin(String tag , String email , String pw ) {


//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(User.class, new UserDeserializer())
//                .create();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(baseUrl)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build();
try{
    RetrofitServiceAPI api = mRetrofit.create(RetrofitServiceAPI.class);
    Observable<User> observable = api.signin(email, pw);

    return observable;
}catch (Exception ex){
    MyLog.Debug(ex.getMessage());
}

//        return api.signin(email, pw);
//            call.enqueue(callback);
//            callList.put(tag , call);

return null;
    }


    public void siginWithRt(String tag , String email , String pw ,Callback<User> callback) {


//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(User.class, new UserDeserializer())
//                .create();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(baseUrl)
//                .addConverterFactory(GsonConverterFactory.create(gson))
////                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
////                    .client(client)
//                .build();
//
//        RetrofitServiceAPI api = retrofit.create(RetrofitServiceAPI.class);
//       Call<User> call =   api.signinWithRt(email , pw);
////        return api.signin(email, pw);
//            call.enqueue(callback);
//            callList.put(tag , call);


    }


    public void sigup(String tag ,String email , String pw , String name, Callback<User> callback){

//        Gson gson = new GsonBuilder()
//                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
//                .create();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(baseUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        RetrofitServiceAPI api = retrofit.create(RetrofitServiceAPI.class);
//        Call<User> call = api.signup(email , pw , name);
//        call.enqueue(callback);
//        callList.put(tag , call);
    }


    public Observable<Location> getLocationById(String id ) {

        RetrofitServiceAPI api = mRetrofit.create(RetrofitServiceAPI.class);
        return api.getLocationById(id);
    }

    public Observable<Review> addReview(String locationId ,
                                        float rating ,
                                        String reviewText) {

        RetrofitServiceAPI api = mRetrofit.create(RetrofitServiceAPI.class);
        return api.addReview(locationId ,rating , reviewText);
    }

    public Observable<List<Location>> getLocations(String lng , String lat , String maxDistance){

//        Gson gson = new GsonBuilder()
//                .create();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(baseUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
        Map<String , String> querys = new HashMap<>();
            /*
    *     .appendQueryParameter("lng" , "-0.9690884")
                    .appendQueryParameter("lat" , "51.455041")
                    .appendQueryParameter("maxDistance" , "20")
    * */
        querys.put("lng" , lng);
        querys.put("lat" , lat);
        querys.put("maxDistance" , maxDistance);

        RetrofitServiceAPI api = mRetrofit.create(RetrofitServiceAPI.class);
//        RetrofitServiceAPI api = retrofit.create(RetrofitServiceAPI.class);
        return api.getLocations(querys);
//        call.enqueue(callback);

//        callList.put(tag , call);
    }

}
