package com.wifi.yilong.yilongwifi.newActivity.locationDetail;

import android.app.Fragment;
import android.os.Bundle;

import com.wifi.yilong.yilongwifi.newActivity.BaseActivity;

/**
 * Created by Administrator on 2017/2/22.
 */

public class LocationDetailActivity extends BaseActivity {
    @Override
    protected Fragment getFragment(Bundle args) {
        LocationDetailFragment fragment = new LocationDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
