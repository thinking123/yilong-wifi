package com.wifi.yilong.yilongwifi.Receiver;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wifi.yilong.yilongwifi.Infrastructure.AppConstant;

/**
 * Created by Administrator on 2017/2/1.
 */

public  class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(getResultCode() != Activity.RESULT_OK){
            return;
        }

        int resCode = intent.getIntExtra(
                AppConstant.Extra.INT_SHOW_NEW_LOCATION_NOTIFICATION_REQUEST ,
                0);

        Notification notification = (Notification)intent.getSerializableExtra(
                AppConstant.Extra.INT_SHOW_NEW_LOCATION_NOTIFICATION_NOTIFICATION);
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(resCode , notification);
    }
}
