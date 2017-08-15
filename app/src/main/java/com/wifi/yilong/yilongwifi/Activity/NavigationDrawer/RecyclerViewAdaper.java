package com.wifi.yilong.yilongwifi.Activity.NavigationDrawer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wifi.yilong.yilongwifi.R;

/**
 * Created by Administrator on 2017/2/6.
 */

public class RecyclerViewAdaper extends RecyclerView.Adapter<RecyclerViewAdaper.ViewHolder> {
    String[] mDataset ;
    public RecyclerViewAdaper(String[] myDataset){
        mDataset  = myDataset;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mDataset[position]);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mTextView;
        public ViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }
}
