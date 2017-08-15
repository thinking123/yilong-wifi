package com.wifi.yilong.yilongwifi.Http.rest.gsonParse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.wifi.yilong.yilongwifi.Http.rest.model.Location;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017/2/19.
 */

public class LocationDeserializer  implements JsonDeserializer<Location> {
    @Override
    public Location deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {

        float[] coords = new Gson().fromJson(json.getAsJsonObject().getAsJsonArray("coords") , float[].class);

        Location location = new Location();
        if(coords != null && coords.length == 2){
            //get location details
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();
            location =  gson.fromJson(json.getAsJsonObject() , Location.class);


            location.latitude = coords[0];
            location.longitude =  coords[1];
        }else{
            //get location list
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();
            location =  gson.fromJson(json.getAsJsonObject() , Location.class);
        }
        return  location;
    }
}
