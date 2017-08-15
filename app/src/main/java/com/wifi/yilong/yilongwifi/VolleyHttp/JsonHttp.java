package com.wifi.yilong.yilongwifi.VolleyHttp;

import com.android.volley.Request;

/**
 * Created by Administrator on 2017/2/8.
 */

public class JsonHttp {
    private int mMethod = Request.Method.GET;

//    public interface httpDataType{
//        int JSONOBJECT = 0;
//        int JSONARRAYOBJECT = 1;
//    }
    public enum  HttpDataType {
         JSONOBJECT ,
         JSONARRAYOBJECT
    }
    public void SetMethod(int method){
        mMethod = method;
    }

    public static void startHttp(int method ,
                                 String url ,
                                 HttpDataType dataType ,
                                 VolleyCallback<?> callback){

    }
}
