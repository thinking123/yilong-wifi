package com.wifi.yilong.yilongwifi.VolleyHttp;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Administrator on 2017/2/8.
 */

public class JsonObjectHttp extends JsonObjectRequest {

    Priority mPriority;
    Map<String , String> mParams;
    Map<String , String> mHeaders;
    public JsonObjectHttp(
            int method ,
            String url,
            JSONObject postData,
            final VolleyCallback callback
    ){
        super(method, url, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(callback != null){
                            callback.Success(response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(callback != null){
                            callback.Error(error);
                        }
                    }
                });
    }

    public void setPriority(Priority _priority){
        mPriority = _priority;
    }

    @Override
    public Priority getPriority() {
        return mPriority != null ? mPriority : Priority.NORMAL;
    }

    public void setParams(Map<String , String> params){
        mParams = params;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams != null ? mParams : super.getParams();
    }

    public void setHeaders(Map<String , String> headers){
        mHeaders = headers;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders != null ? mHeaders : super.getHeaders();
    }
}
