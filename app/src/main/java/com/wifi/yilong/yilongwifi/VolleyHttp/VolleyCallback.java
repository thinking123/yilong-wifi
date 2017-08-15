package com.wifi.yilong.yilongwifi.VolleyHttp;

import com.android.volley.VolleyError;

/**
 * Created by Administrator on 2017/2/8.
 */

public interface VolleyCallback<T> {
    public void Success(T res);
    public void Error(VolleyError error);
}
