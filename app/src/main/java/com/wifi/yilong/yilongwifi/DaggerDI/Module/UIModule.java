package com.wifi.yilong.yilongwifi.DaggerDI.Module;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/3/7.
 */
@Module
public class UIModule {
    Activity mActivity;
    public UIModule(Activity activity){
        mActivity = activity;
    }
    @Provides
    @Singleton
    ProgressDialog provideProgressDialog(Application application){
        ProgressDialog progressDialog = new ProgressDialog(mActivity);
//        progressDialog.setMessage("Its loading....");
        progressDialog.setTitle("正在加载...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        return progressDialog;
    }
}
