package com.wifi.yilong.yilongwifi.DaggerDI.Module;

import android.app.Application;

import com.wifi.yilong.yilongwifi.App.AppController;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/2/16.
 */
@Module
public class AppModule {
    Application application;

    public AppModule(Application app){
        this.application = app;
    }

    @Provides
    @Singleton
    Application provideApplication(){
        return this.application;
    }

    @Provides
    @Singleton
    AppController provideAppController(){
        return AppController.getInstance();
    }
}
