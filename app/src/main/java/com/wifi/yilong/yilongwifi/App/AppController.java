package com.wifi.yilong.yilongwifi.App;

import android.app.Application;
import android.text.TextUtils;

import com.activeandroid.ActiveAndroid;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.wifi.yilong.yilongwifi.DaggerDI.Component.CommonComponent;
import com.wifi.yilong.yilongwifi.DaggerDI.Component.DaggerCommonComponent;
import com.wifi.yilong.yilongwifi.DaggerDI.Module.AppModule;
import com.wifi.yilong.yilongwifi.DaggerDI.Module.NetModule;
import com.wifi.yilong.yilongwifi.DaggerDI.Module.StorageModule;
import com.wifi.yilong.yilongwifi.Http.ServerURL;

/**
 * Created by Administrator on 2017/2/8.
 */

public class AppController extends Application {
    private static AppController mAppController;

    private RequestQueue mRequestQueue;
    private CommonComponent mCommonComponent;
    public static final String TAG = AppController.class.getSimpleName();
    @Override
    public void onCreate() {
        super.onCreate();
        mAppController = this;

        //init active android database lib
        ActiveAndroid.initialize(this);

        //init dagger 2 , dependency Inject object instance
        mCommonComponent = DaggerCommonComponent.builder()
                .appModule(new AppModule(this))
                .storageModule(new StorageModule())
                .netModule(new NetModule(ServerURL.BaseUrl))
                .build();




    }



    public CommonComponent getCommonComponent(){
        return mCommonComponent;
    }

    public static synchronized AppController getInstance(){
        return mAppController;
    }
    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req , String tag){
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag){
        if(mRequestQueue != null){
            mRequestQueue.cancelAll(tag);
        }
    }

    private boolean isNotificationOpen = false;

    public boolean isNotificationOpen() {
        return isNotificationOpen;
    }

    public void setNotificationOpen(boolean notificationOpen) {
        isNotificationOpen = notificationOpen;
    }
}
