package com.wifi.yilong.yilongwifi.Http.rest.gsonParse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.wifi.yilong.yilongwifi.Http.rest.model.User;

import org.parceler.guava.io.BaseEncoding;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by Administrator on 2017/2/13.
 */

public class UserDeserializer implements JsonDeserializer<User> {

    @Override
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement jsonToken = jsonObject.get("token");
        String token = jsonToken.getAsString();
        try {
            String payLoader = new String(BaseEncoding.base64().decode(token.split("\\.")[1]), "UTF-8");
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .registerTypeAdapter(Date.class , new DateFromLongDeserializer())
//                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssz")
//                    .serializeNulls()
                    .create();


            User user = gson.fromJson(payLoader , User.class);
            user.authToken = token;

            return  user;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //new String(BaseEncoding.base64().decode(token.split("\\.")[1]) , "UTF-8");

        return null;
    }
}
