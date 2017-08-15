package com.wifi.yilong.yilongwifi.DaggerDI.Module;

import android.app.Application;
import android.content.SharedPreferences;

import com.activeandroid.query.Select;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.wifi.yilong.yilongwifi.Http.rest.gsonParse.LocationDeserializer;
import com.wifi.yilong.yilongwifi.Http.rest.gsonParse.ReviewDeserializer;
import com.wifi.yilong.yilongwifi.Http.rest.gsonParse.UserDeserializer;
import com.wifi.yilong.yilongwifi.Http.rest.model.Location;
import com.wifi.yilong.yilongwifi.Http.rest.model.Review;
import com.wifi.yilong.yilongwifi.Http.rest.model.User;
import com.wifi.yilong.yilongwifi.Http.rest.retrofitService.RetrofitService;

import java.io.IOException;
import java.text.DateFormat;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/2/16.
 */
@Module
public class NetModule {
    String mBaseUrl;

    //    UserDeserializer mUserDeserializer;
    public NetModule(String baseUrl) {
        this.mBaseUrl = baseUrl;
//        this.mUserDeserializer = userDeserializer;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        UserDeserializer userDeserializer = new UserDeserializer();
        LocationDeserializer locationDeserializer = new LocationDeserializer();
        ReviewDeserializer reviewDeserializer = new ReviewDeserializer();

        return new GsonBuilder()
                .setDateFormat(DateFormat.FULL, DateFormat.FULL)
                .registerTypeAdapter(User.class, userDeserializer)
                .registerTypeAdapter(Location.class, locationDeserializer)
                .registerTypeAdapter(Review.class, reviewDeserializer)
                .create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(SharedPreferences preferences){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                //add head token
                if (preferences.contains(User.USERID)) {
                    String id = preferences.getString(User.USERID, null);
                    User user = new Select().from(User.class).where("userId = ?", id).executeSingle();

                    Request request = original.newBuilder()
                            .header("Authorization", "Bearer " + user.authToken)
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);

                } else {
                    return chain.proceed(original);
                }

            }
        });

        return httpClient.build();
    }
    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, SharedPreferences preferences , OkHttpClient client) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit;
    }


    @Provides
    @Singleton
    RetrofitService provideRetrofitService(Application application, Retrofit retrofit) {
        return RetrofitService.get(application, retrofit);
    }


}
