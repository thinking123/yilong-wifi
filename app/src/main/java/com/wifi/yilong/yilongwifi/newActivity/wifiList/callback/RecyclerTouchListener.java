package com.wifi.yilong.yilongwifi.newActivity.wifiList.callback;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017/3/6.
 */

public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

    private GestureDetector gestureDetector;
    private RecyclerClickListener mRecyclerClickListener;

    public RecyclerTouchListener(Context context, final RecyclerView recyclerView,
                                 final RecyclerClickListener recyclerClickListener) {
        mRecyclerClickListener = recyclerClickListener;
        gestureDetector = new GestureDetector(context ,  new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX() , e.getY());

                if(child != null && mRecyclerClickListener != null){
                    mRecyclerClickListener.onLongClick(child , recyclerView.getChildAdapterPosition(child));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && mRecyclerClickListener != null && gestureDetector.onTouchEvent(e)) {
            mRecyclerClickListener.onClick(child, rv.getChildAdapterPosition(child));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
