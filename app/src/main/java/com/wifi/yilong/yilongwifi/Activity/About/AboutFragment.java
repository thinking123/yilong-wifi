package com.wifi.yilong.yilongwifi.Activity.About;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wifi.yilong.yilongwifi.R;

/**
 * Created by Administrator on 2017/1/25.
 */

public class AboutFragment extends Fragment {

    TextView mAboutTextView ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            if(NavUtils.getParentActivityName(getActivity()) != null){
                getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

            }
        }
        View v = inflater.inflate(R.layout.about_fragment , container , false);
        mAboutTextView = (TextView)v.findViewById(R.id.about_info);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if(NavUtils.getParentActivityName(getActivity()) != null){
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
