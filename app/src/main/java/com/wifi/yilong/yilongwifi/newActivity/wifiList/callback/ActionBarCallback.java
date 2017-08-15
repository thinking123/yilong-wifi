package com.wifi.yilong.yilongwifi.newActivity.wifiList.callback;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import com.wifi.yilong.yilongwifi.R;
import com.wifi.yilong.yilongwifi.newActivity.wifiList.Adapter.LocationsAdapter;
import com.wifi.yilong.yilongwifi.newActivity.wifiList.fragment.WiFiListFragment;

/**
 * Created by Administrator on 2017/3/5.
 */

public class ActionBarCallback implements ActionMode.Callback {

    private WiFiListFragment mWiFiListFragment;
    private LocationsAdapter mLocationsAdapter;
//    private List<Location> mLocations;
    public ActionBarCallback(WiFiListFragment wiFiListFragment ,
                             LocationsAdapter locationsAdapter){
        mWiFiListFragment = wiFiListFragment;
        mLocationsAdapter = locationsAdapter;
//        mLocations = locations;
    }
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
         mode.getMenuInflater().inflate(R.menu.delete_menu , menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//        mode.setTitle("you select items");
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_menu_delete:
                mWiFiListFragment.deleteRows();
//                mode.finish();
                break;
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mLocationsAdapter.removeSelection();
        mWiFiListFragment.setNullToActionMode();
    }
}
