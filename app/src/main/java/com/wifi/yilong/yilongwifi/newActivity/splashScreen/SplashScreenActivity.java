package com.wifi.yilong.yilongwifi.newActivity.splashScreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wifi.yilong.yilongwifi.App.AppController;
import com.wifi.yilong.yilongwifi.newActivity.signin.SigninActivity;
import com.wifi.yilong.yilongwifi.newActivity.introducePage.IntroducePageActivity;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/3/4.
 */

public class SplashScreenActivity extends AppCompatActivity {
    @Inject
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Inject dependency
        ((AppController) this.getApplication()).getCommonComponent().inject(this);

        if(isNeedShowIntroPage()){
            Intent intent = new Intent(this , IntroducePageActivity.class);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(this , SigninActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private boolean isNeedShowIntroPage() {
        boolean isShow = false;
        isShow = !(boolean) mSharedPreferences.getBoolean(IntroducePageActivity.HADSHOWINTROPAGE, false);

        return isShow;
    }
}
