package com.wifi.yilong.yilongwifi.Activity.Review;

import android.support.v4.app.Fragment;

import com.wifi.yilong.yilongwifi.Activity.FragmentManagement;

/**
 * Created by Administrator on 2017/1/26.
 */

public class AddReviewActivity extends FragmentManagement {

    @Override
    protected Fragment createFragment() {
        return new AddReviewFragment();
    }
}
