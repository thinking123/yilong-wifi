package com.wifi.yilong.yilongwifi.newActivity.wifiList.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wifi.yilong.yilongwifi.R;

/**
 * Created by Administrator on 2017/2/20.
 */

public class AboutFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wifi_list_about , container ,false);

        return view;
    }
}
