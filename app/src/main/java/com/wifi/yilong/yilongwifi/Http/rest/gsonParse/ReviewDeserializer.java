package com.wifi.yilong.yilongwifi.Http.rest.gsonParse;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.wifi.yilong.yilongwifi.Http.rest.model.Review;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017/3/2.
 */

public class ReviewDeserializer implements JsonDeserializer<Review> {

    @Override
    public Review deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement jsonToken = jsonObject.get("token");
        String token = jsonToken.getAsString();
        try {



            Review user = new Review();

            return  user;

        } catch (Exception e) {
            e.printStackTrace();
        }
        //new String(BaseEncoding.base64().decode(token.split("\\.")[1]) , "UTF-8");

        return null;
    }
}