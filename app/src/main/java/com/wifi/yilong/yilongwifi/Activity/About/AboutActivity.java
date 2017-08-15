package com.wifi.yilong.yilongwifi.Activity.About;

import android.support.v4.app.Fragment;

import com.wifi.yilong.yilongwifi.Activity.FragmentManagement;

/**
 * Created by Administrator on 2017/1/25.
 */

public class AboutActivity extends FragmentManagement {

    @Override
    protected Fragment createFragment() {
        return new AboutFragment();
    }
}
