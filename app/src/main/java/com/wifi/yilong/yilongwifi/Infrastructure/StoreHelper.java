package com.wifi.yilong.yilongwifi.Infrastructure;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by Administrator on 2017/1/23.
 */

public class StoreHelper {
    public static void putToSharedPreferences(Context c , String key , String value){
        PreferenceManager.getDefaultSharedPreferences(c).edit().putString(key , value).commit();
    }
    public static void putToSharedPreferences(Context c , String key , boolean value){
        PreferenceManager.getDefaultSharedPreferences(c).edit().putBoolean(key , value).commit();
    }
    public static void putToSharedPreferences(Context c , String key , Integer value){
        PreferenceManager.getDefaultSharedPreferences(c).edit().putInt(key , value).commit();
    }
    public static String getStringFromSharedPreferences(Context c , String key){
        return PreferenceManager.getDefaultSharedPreferences(c).getString(key , null);
    }
    public static Integer getIntFromSharedPreferences(Context c , String key){
        return PreferenceManager.getDefaultSharedPreferences(c).getInt(key , -1);
    }
    public static Boolean getIBooleanFromSharedPreferences(Context c , String key){
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(key , false);
    }
}
