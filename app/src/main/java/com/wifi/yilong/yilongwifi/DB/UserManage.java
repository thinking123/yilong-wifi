package com.wifi.yilong.yilongwifi.DB;

import android.content.Context;

/**
 * Created by Administrator on 2017/1/23.
 */

public class UserManage {
    public static UserManage mUserManage;

    DatabaseHelper dbHelper;
    public UserManage(Context c){
        dbHelper = new DatabaseHelper(c);
    }
    public static UserManage get(Context c){
        if(mUserManage == null){
            mUserManage = new UserManage(c);
        }

        return mUserManage;
    }
}
