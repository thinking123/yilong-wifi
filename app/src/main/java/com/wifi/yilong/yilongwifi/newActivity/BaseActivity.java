package com.wifi.yilong.yilongwifi.newActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wifi.yilong.yilongwifi.R;

/**
 * Created by Administrator on 2017/2/24.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected int getContainerId(){
        return R.id.fragmentContainer;
    }
    protected int getLayoutId(){
        return R.layout.activity_fragment;
    }
    protected abstract Fragment getFragment(Bundle args);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        Intent intent = getIntent();
        Bundle args = intent.getExtras();
        FragmentManager fm = getFragmentManager();
        if(fm.findFragmentById(getContainerId()) == null){
            Fragment fragment = getFragment(args);

            fm.beginTransaction()
                    .add(getContainerId() , fragment)
                    .commit();
        }
    }
}
