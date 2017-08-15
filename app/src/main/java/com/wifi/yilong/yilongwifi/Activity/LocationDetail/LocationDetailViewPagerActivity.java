package com.wifi.yilong.yilongwifi.Activity.LocationDetail;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.wifi.yilong.yilongwifi.Infrastructure.MyLog;
import com.wifi.yilong.yilongwifi.Model.Location;
import com.wifi.yilong.yilongwifi.Model.LocationLab;
import com.wifi.yilong.yilongwifi.R;

import java.util.List;

/**
 * Created by Administrator on 2017/1/27.
 */

public class LocationDetailViewPagerActivity extends FragmentActivity {

    ViewPager mViewPager;
    private List<Location> mLocations;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if(NavUtils.getParentActivityName(this) != null){
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_detail_viewpager_activity);

        mLocations = LocationLab.get(this).getLocations();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            if(NavUtils.getParentActivityName(this) != null){
                this.getActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        mViewPager = (ViewPager)findViewById(R.id.location_detail_viewpager);

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Location location = mLocations.get(position);
                LocationDetailFragment ldfg = LocationDetailFragment.newInstance(location.getId());

                return  ldfg;
            }

            @Override
            public int getCount() {
                return mLocations.size();
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                LocationDetailFragment fg =   (LocationDetailFragment)((FragmentStatePagerAdapter)mViewPager.getAdapter()).getItem(position);
//                if(fg != null){
//fg.testTip();
//                }

//                LocationDetailFragment ldfg = (LocationDetailFragment)mViewPager.getAdapter().instantiateItem()
                MyLog.Debug("pos : " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if(savedInstanceState != null){
            String id = savedInstanceState.getString(Location.ID);
            for(int i = 0 ; i<mLocations.size() ; i++){
                if(mLocations.get(i).getId().equals(id)){
                    mViewPager.setCurrentItem(i);
                    break;
                }
            }

        }

    }
}
