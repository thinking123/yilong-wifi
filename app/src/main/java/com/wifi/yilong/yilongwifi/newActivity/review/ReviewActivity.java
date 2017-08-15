package com.wifi.yilong.yilongwifi.newActivity.review;

import android.app.Fragment;
import android.os.Bundle;

import com.wifi.yilong.yilongwifi.newActivity.BaseActivity;

/**
 * Created by Administrator on 2017/3/1.
 */

public class ReviewActivity extends BaseActivity {
    @Override
    protected Fragment getFragment(Bundle args) {
        ReviewFragment fragment = new ReviewFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
