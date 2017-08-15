package com.wifi.yilong.yilongwifi.newActivity.wifiList.callback;

import android.view.View;

/**
 * Created by Administrator on 2017/3/6.
 */

public interface RecyclerClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
