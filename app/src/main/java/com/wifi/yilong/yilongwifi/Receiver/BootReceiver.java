package com.wifi.yilong.yilongwifi.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wifi.yilong.yilongwifi.Infrastructure.AppConstant;
import com.wifi.yilong.yilongwifi.Infrastructure.StoreHelper;
import com.wifi.yilong.yilongwifi.Service.LocationService;

/**
 * Created by Administrator on 2017/2/1.
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isOn = StoreHelper.getIBooleanFromSharedPreferences(context ,
                AppConstant.SharedPreferencesKey.IS_LOCATION_SERVICE_ON);

        LocationService.startLocationService(context , isOn);
    }
}
