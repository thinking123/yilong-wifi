package com.wifi.yilong.yilongwifi.newActivity.signup;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wifi.yilong.yilongwifi.R;

/**
 * Created by Administrator on 2017/2/18.
 */

public class SignupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        FragmentManager fm = getFragmentManager();
        if(fm.findFragmentById(R.id.fragmentContainer) == null){
            fm.beginTransaction()
                    .add(R.id.fragmentContainer , new SignupFragment())
                    .commit();
        }
    }
}
