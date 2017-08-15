package com.wifi.yilong.yilongwifi.Http.rest.gsonParse;

import com.activeandroid.serializer.TypeSerializer;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/18.
 */

public class StringArraySerializer extends TypeSerializer {
    private final Gson gson = new Gson();
    @Override
    public Class<?> getDeserializedType() {
        return List.class;
    }

    @Override
    public Class<?> getSerializedType() {
        return String.class;
    }

    @Override
    public String serialize(Object data) {
        if(data == null){
            return null;
        }

        return gson.toJson(data);
    }

    @Override
    public List<String> deserialize(Object data) {
        if(data == null){
            return null;
        }

        List<String> list = new ArrayList<>();
        list = gson.fromJson(data.toString() , List.class);

        return list;
    }
}
