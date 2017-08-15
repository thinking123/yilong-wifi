package com.wifi.yilong.yilongwifi.DB;

import android.content.Context;
import android.database.Cursor;

/**
 * Created by Administrator on 2017/1/23.
 */

public class UserLoader extends SQLiteLoader<Cursor> {

    public UserLoader(Context c){
        super(c);
    }

    @Override
    protected Cursor loadCursor() {
        return DatabaseHelper.get(getContext()).getAllUsersWithCursor();
    }

    @Override
    public Cursor loadInBackground() {
        Cursor cursor  = loadCursor();
        if(cursor != null){
            cursor.getCount();
        }
        return cursor;
    }

}
