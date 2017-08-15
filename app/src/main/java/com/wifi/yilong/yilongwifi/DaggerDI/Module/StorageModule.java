package com.wifi.yilong.yilongwifi.DaggerDI.Module;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/2/16.
 */
@Module
public class StorageModule {

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Application application){
        return PreferenceManager.getDefaultSharedPreferences(application);
    }
}
