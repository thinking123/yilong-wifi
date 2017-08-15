package com.wifi.yilong.yilongwifi.Model;

import android.content.Context;

import java.util.List;

/**
 * Created by Administrator on 2017/1/27.
 */

public class LocationLab {

    private Context mContext;
    private List<Location> mLocations;

    private static LocationLab sLocationLab;
    public static LocationLab get(Context c){
        if(sLocationLab == null){
            sLocationLab = new LocationLab(c.getApplicationContext());
        }

        return sLocationLab;
    }

    private LocationLab(Context c){
        mContext = c;
    }

    public void setLocations(List<Location> ls){
        mLocations = ls;
    }
    public List<Location> getLocations(){
        return mLocations;
//        ArrayList<Location> list = new ArrayList<Location>();
//        list.add(mLocations.get(0));
//        return list;
    }
    public Location getLocation(int index){
       return mLocations != null ? mLocations.get(index) : null;
    }
    public Location getLocation(String id){
        for(Location l : mLocations){
            if(l.getId().equals(id)){
                return l;
            }
        }

        return  null;
    }
}
