package com.wifi.yilong.yilongwifi.newActivity.reviewlist;

import android.app.Fragment;
import android.os.Bundle;

import com.wifi.yilong.yilongwifi.newActivity.BaseActivity;

/**
 * Created by Administrator on 2017/3/8.
 */

public class ReviewListActivity extends BaseActivity {
    @Override
    protected Fragment getFragment(Bundle args) {
        Fragment fragment = new ReviewListFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
