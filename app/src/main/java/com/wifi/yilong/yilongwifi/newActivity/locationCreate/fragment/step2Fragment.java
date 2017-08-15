package com.wifi.yilong.yilongwifi.newActivity.locationCreate.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.wifi.yilong.yilongwifi.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/5.
 */

public class step2Fragment extends Fragment {
    @BindView(R.id.location_create_step1_fragment_add_facilities_button)
    Button locationCreateStep1FragmentAddFacilitiesButton;
    @BindView(R.id.location_create_step2_fragment_linear_layout)
    LinearLayout locationCreateStep2FragmentLinearLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.location_create_step2_fragment, container, false);

        ButterKnife.bind(this, v);
        return v;
    }

    @OnClick(R.id.location_create_step1_fragment_add_facilities_button)
    public void onClick() {
    }
}
