package com.wifi.yilong.yilongwifi.newActivity.introducePage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.wifi.yilong.yilongwifi.App.AppController;
import com.wifi.yilong.yilongwifi.R;
import com.wifi.yilong.yilongwifi.newActivity.signin.SigninActivity;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/3/4.
 */

public class IntroducePageActivity extends AppIntro{

    public static final String HADSHOWINTROPAGE = "HADSHOWINTROPAGE";
    @Inject
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Inject dependency
        ((AppController) this.getApplication()).getCommonComponent().inject(this);

        // Note here that we DO NOT use setContentView();

        // Add your slide fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.
//        addSlide(firstFragment);
//        addSlide(secondFragment);
//        addSlide(thirdFragment);
//        addSlide(fourthFragment);

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
//        addSlide(AppIntroFragment.newInstance(title, description, image, backgroundColor));
        addSlide(AppIntroFragment.newInstance("一笼", "一笼日子跑", R.drawable.yilong, getResources().getColor(R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("一壶", "一壶日子跑", R.drawable.yi, getResources().getColor(R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("一勹", "一勹日子跑", R.drawable.yilong1, getResources().getColor(R.color.colorPrimary)));

        // OPTIONAL METHODS
        // Override bar/separator color.
//        setBarColor(Color.parseColor("#3F51B5"));
//        setSeparatorColor(Color.parseColor("#2196F3"));
        setBarColor(Color.BLUE);
        setSeparatorColor(Color.YELLOW);
        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
//        setVibrate(true);
//        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        SetHadShowIntroPage();
        Intent intent = new Intent(this , SigninActivity.class);
        startActivity(intent);
        finish();
        // Do something when users tap on Done button.
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

    private void SetHadShowIntroPage(){
        mSharedPreferences.edit().putBoolean(HADSHOWINTROPAGE , true).commit();
    }
}
