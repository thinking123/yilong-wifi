package com.wifi.yilong.yilongwifi.Service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.wifi.yilong.yilongwifi.Activity.WiFiList.WiFiListActivity;
import com.wifi.yilong.yilongwifi.Http.HttpHelper;
import com.wifi.yilong.yilongwifi.Http.ServerURL;
import com.wifi.yilong.yilongwifi.Infrastructure.AppConstant;
import com.wifi.yilong.yilongwifi.Infrastructure.StoreHelper;
import com.wifi.yilong.yilongwifi.Infrastructure.Utils;
import com.wifi.yilong.yilongwifi.Model.Location;
import com.wifi.yilong.yilongwifi.R;

import java.util.List;

import static com.wifi.yilong.yilongwifi.Infrastructure.AppConstant.Interval.LOCATION_SERVICE_INTERVAL;
import static com.wifi.yilong.yilongwifi.Infrastructure.AppConstant.REQUEST.REQUEST_LOCATION_NEW_LOCATION_NOTIFICATION;
import static com.wifi.yilong.yilongwifi.Infrastructure.AppConstant.SharedPreferencesKey.IS_LOCATION_SERVICE_ON;

/**
 * Created by Administrator on 2017/2/1.
 */

public class LocationService extends IntentService {

    public static final String TAG = "LocationService";

    public LocationService() {
        super(TAG);
    }
    private String formatUrl(){
        String url = ServerURL.GetLocations;

        url = Uri.parse(url).buildUpon()
                .appendQueryParameter("lng" , "-0.9690884")
                .appendQueryParameter("lat" , "51.455041")
                .appendQueryParameter("maxDistance" , "20")
                .build().toString();

        return  url;
        //lng=-0.9690884&lat=51.455041&maxDistance=20
//        return String.format("%s?lng=-0.9690884&lat=51.455041&maxDistance=20" , ServerURL.GetLocations);
//        return String.format("%s?lng=%f&lat=%.8f&maxDistance=%f" , ServerURL.GetLocations,-0.9690884,51.455041,20.0);

    }
    @Override
    protected void onHandleIntent(Intent intent) {
        if(!Utils.IsNetworkAvailable(this)){
            return;
        }

        List<Location> locations = HttpHelper.GetLocations(formatUrl());
        if(locations.size() == 0){
            return;
        }

        String id = locations.get(0).getId();
        String lastId = StoreHelper.getStringFromSharedPreferences(this ,
                AppConstant.SharedPreferencesKey.LAST_LOCATION_ID);
        if(id.equals(lastId)){
            return;
        }

        Intent i = new Intent(this , WiFiListActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this ,
                AppConstant.REQUEST.REQUEST_LOCATION_SERVICE,
                i , 0);
        Resources r = getResources();
        Notification notification = new NotificationCompat
                .Builder(this)
                .setTicker(r.getString(R.string.notifiation_new_location_ticker))
                .setContentTitle(r.getString(R.string.notifiation_new_location_title))
                .setContentText(r.getString(R.string.notifiation_new_location_text))
                .setSmallIcon(R.drawable.new_location)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        sendBackgroundNotification(REQUEST_LOCATION_NEW_LOCATION_NOTIFICATION , notification);

    }

    private void sendBackgroundNotification(int resCode , Notification notification){
        Intent i = new Intent(AppConstant.Action.SHOW_NOTIFICATION);
        i.putExtra(AppConstant.Extra.INT_SHOW_NEW_LOCATION_NOTIFICATION_REQUEST , resCode);
        i.putExtra(AppConstant.Extra.INT_SHOW_NEW_LOCATION_NOTIFICATION_NOTIFICATION , notification);

        sendOrderedBroadcast(i , AppConstant.Signature.ACTION_SHOW_NEW_LOCATION_SIGNATURE);
    }

    public static boolean isServiceStarted(Context c){
        Intent i = new Intent(c , LocationService.class);
        PendingIntent pi = PendingIntent.getService(c ,
                AppConstant.REQUEST.REQUEST_LOCATION_SERVICE ,
                i , PendingIntent.FLAG_NO_CREATE);

        return  pi != null;

    }
    public static void startLocationService(Context c , boolean isOn){
        Intent i = new Intent(c , LocationService.class);
        PendingIntent pi = PendingIntent.getService(c , AppConstant.REQUEST.REQUEST_LOCATION_SERVICE , i , 0);
        AlarmManager alarmManager = (AlarmManager)c.getSystemService(Context.ALARM_SERVICE);

        if(isOn){
            alarmManager.setRepeating(AlarmManager.RTC ,
                    System.currentTimeMillis() ,
                    LOCATION_SERVICE_INTERVAL ,
                    pi );
        }else{
            alarmManager.cancel(pi);
            pi.cancel();
        }

        StoreHelper.putToSharedPreferences(c ,
                IS_LOCATION_SERVICE_ON ,
                isOn);

    }
}
