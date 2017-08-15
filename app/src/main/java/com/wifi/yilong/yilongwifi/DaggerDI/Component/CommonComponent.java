package com.wifi.yilong.yilongwifi.DaggerDI.Component;

import com.wifi.yilong.yilongwifi.DaggerDI.Module.AppModule;
import com.wifi.yilong.yilongwifi.DaggerDI.Module.NetModule;
import com.wifi.yilong.yilongwifi.DaggerDI.Module.StorageModule;
import com.wifi.yilong.yilongwifi.Http.rest.retrofitService.RetrofitService;
import com.wifi.yilong.yilongwifi.newActivity.introducePage.IntroducePageActivity;
import com.wifi.yilong.yilongwifi.newActivity.locationDetail.LocationDetailFragment;
import com.wifi.yilong.yilongwifi.newActivity.review.ReviewFragment;
import com.wifi.yilong.yilongwifi.newActivity.signin.SigninFragment;
import com.wifi.yilong.yilongwifi.newActivity.signup.SignupFragment;
import com.wifi.yilong.yilongwifi.newActivity.splashScreen.SplashScreenActivity;
import com.wifi.yilong.yilongwifi.newActivity.wifiList.WiFiListActivity;
import com.wifi.yilong.yilongwifi.newActivity.wifiList.fragment.WiFiListFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Administrator on 2017/2/16.
 */
@Singleton
@Component(modules = {AppModule.class, NetModule.class, StorageModule.class })
public interface CommonComponent {
    void inject(SigninFragment fragment);
    void inject(SignupFragment fragment);
    void inject(WiFiListFragment fragment);
    void inject(LocationDetailFragment fragment);
    void inject(ReviewFragment fragment);
    void inject(WiFiListActivity activity);
    void inject(IntroducePageActivity activity);
    void inject(SplashScreenActivity activity);
    void inject(RetrofitService retrofitService);


}
