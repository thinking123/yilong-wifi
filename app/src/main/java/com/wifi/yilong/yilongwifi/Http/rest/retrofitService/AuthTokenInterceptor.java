package com.wifi.yilong.yilongwifi.Http.rest.retrofitService;

import android.text.TextUtils;

import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/2/12.
 */

public class AuthTokenInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();
            Response response = chain.proceed(request);
            String body = response.body().string();
            if(!TextUtils.isEmpty(body)) {
                JSONObject token = new GsonBuilder().create().fromJson(body, JSONObject.class);
//                String strToken = token.getString("token");
//            Jwt jwt = Jwts.parser().parse(strToken).getBody();
//            User user = new GsonBuilder().create().fromJson(jwt.getBody() , User.class);
            }

            return response;

    }
}
