package com.wifi.yilong.yilongwifi.Activity.WiFiList;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.wifi.yilong.yilongwifi.Activity.FragmentManagement;
import com.wifi.yilong.yilongwifi.Infrastructure.AppConstant;
import com.wifi.yilong.yilongwifi.Infrastructure.StoreHelper;
import com.wifi.yilong.yilongwifi.R;

/**
 * Created by Administrator on 2017/1/24.
 */

public class WiFiListActivity extends FragmentManagement {
    @Override
    protected Fragment createFragment() {
        return new WiFiListFragment();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            WiFiListFragment fg = (WiFiListFragment)getSupportFragmentManager()
                    .findFragmentById(R.id.fragmentContainer);
            String query = intent.getStringExtra(SearchManager.QUERY);
            StoreHelper.putToSharedPreferences(this ,
                    AppConstant.SharedPreferencesKey.SEARCHVIEWQUERY ,
                    query);

            fg.updateData();

        }
    }

    @Override
    protected int getLayoutResId() {
        return super.getLayoutResId();
    }
}
