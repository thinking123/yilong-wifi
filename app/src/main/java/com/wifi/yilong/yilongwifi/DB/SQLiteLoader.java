package com.wifi.yilong.yilongwifi.DB;


import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by Administrator on 2017/1/23.
 */

public abstract class SQLiteLoader<D> extends AsyncTaskLoader<D> {
    private D mData;

    protected abstract Cursor loadCursor();

    public SQLiteLoader(Context c){
        super(c);
    }

    @Override
    public void deliverResult(D data) {
        mData = data;
        if(isStarted()){
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {
        if(mData != null){
            deliverResult(mData);
        }else{
            forceLoad();
        }
    }

}
