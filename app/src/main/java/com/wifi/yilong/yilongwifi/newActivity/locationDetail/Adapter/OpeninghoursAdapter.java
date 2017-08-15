package com.wifi.yilong.yilongwifi.newActivity.locationDetail.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wifi.yilong.yilongwifi.Http.rest.model.OpeningTime;
import com.wifi.yilong.yilongwifi.Infrastructure.Utils;
import com.wifi.yilong.yilongwifi.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/26.
 */

public class OpeninghoursAdapter extends RecyclerView.Adapter<OpeninghoursAdapter.ViewHolder> {



    List<OpeningTime> openingTimes;
    Context mContext;

    public OpeninghoursAdapter(List<OpeningTime> _openingTimes,
                               Context context) {
        openingTimes = _openingTimes;
        mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.opening_hours_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ViewHolder viewHolder = null;
        OpeningTime openingTime = openingTimes.get(position);

        viewHolder = holder;
        if(Utils.IsStringEmpty(openingTime.days)){
            viewHolder.openingHoursRowDays.setText(openingTime.days);
        }else{
            viewHolder.openingHoursRowDays.setVisibility(View.INVISIBLE);
        }
        if(Utils.IsStringEmpty(openingTime.days)){
            viewHolder.openingHoursRowOpening.setText(openingTime.opening);
        }else{
            viewHolder.openingHoursRowOpening.setVisibility(View.INVISIBLE);
        }
        if(Utils.IsStringEmpty(openingTime.days)){
            viewHolder.openingHoursRowClosing.setText(openingTime.closing);
        }else{
            viewHolder.openingHoursRowClosing.setVisibility(View.INVISIBLE);
        }
        if(Utils.IsStringEmpty(openingTime.days)){
            viewHolder.openingHoursRowClosed.setText(openingTime.closed ? "closed" : "open");
        }else{
            viewHolder.openingHoursRowClosed.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return openingTimes.size();
    }



    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.opening_hours_row_days)
        TextView openingHoursRowDays;
        @BindView(R.id.opening_hours_row_opening)
        TextView openingHoursRowOpening;
        @BindView(R.id.opening_hours_row_closing)
        TextView openingHoursRowClosing;
        @BindView(R.id.opening_hours_row_closed)
        TextView openingHoursRowClosed;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
