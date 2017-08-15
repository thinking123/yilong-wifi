package com.wifi.yilong.yilongwifi.Activity.Review;

import android.support.v4.app.Fragment;

import com.wifi.yilong.yilongwifi.Activity.FragmentManagement;
import com.wifi.yilong.yilongwifi.Model.Review;

/**
 * Created by Administrator on 2017/1/28.
 */

public class ReviewDetailActivity extends FragmentManagement {
    @Override
    protected Fragment createFragment() {
        return ReviewDetailFragment.newInstance((Review)getIntent().getSerializableExtra(Review.REVIEW));
    }

}
