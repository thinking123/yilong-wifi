package com.wifi.yilong.yilongwifi.newActivity.signin;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.wifi.yilong.yilongwifi.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/14.
 */

public class SigninActivity extends AppCompatActivity {


    @BindView(R.id.fragmentContainer)
    FrameLayout fragmentContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        ButterKnife.bind(this);

        FragmentManager fm = getFragmentManager();
        if(fm.findFragmentById(R.id.fragmentContainer) == null) {
            fm
            .beginTransaction()
            .add(R.id.fragmentContainer, new SigninFragment())
            .commit();
        }

    }
}
