package com.wifi.yilong.yilongwifi.newActivity.locationCreate.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wifi.yilong.yilongwifi.R;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/5.
 */

public class step3Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.location_create_step3_fragment, container, false);

        ButterKnife.bind(this, v);
        return v;
    }
}
