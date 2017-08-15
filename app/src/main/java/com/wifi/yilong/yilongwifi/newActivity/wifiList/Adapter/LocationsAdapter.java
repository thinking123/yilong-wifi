package com.wifi.yilong.yilongwifi.newActivity.wifiList.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.wifi.yilong.yilongwifi.Http.rest.model.Location;
import com.wifi.yilong.yilongwifi.Http.rest.model.User;
import com.wifi.yilong.yilongwifi.R;
import com.wifi.yilong.yilongwifi.newActivity.wifiList.fragment.WiFiListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/20.
 */

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.LocationViewHolder> {

    private List<Location> locations;
    private Context mContext;
    private SparseBooleanArray mSelectedItemsIds;

    WiFiListFragment.ItemClickListener mItemClickListener;

    public Location getClickItem(int pos){
      return   locations != null ? locations.get(pos) : null;
    }
    public LocationsAdapter(Context context , User user){// ,WiFiListFragment.ItemClickListener itemClickListener){

        List<Location> list = new Select().all().from(Location.class).execute();
        locations = new ArrayList<Location>();
        for(int i = 0 ; i < 20 ; i ++){
            locations.add(list.get(0));
        }
//        locations = new Select().all().from(Location.class).execute();
        mContext = context;
//        mItemClickListener = itemClickListener;
        mSelectedItemsIds = new SparseBooleanArray();
    }
    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wifi_list_row, parent, false);

        return new LocationViewHolder(view , mItemClickListener , locations);
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {
        Location location = locations.get(position);

        holder.rowLocationName.setText(location.name);
        holder.rowRating.setRating(location.rating);
        if(location.distance < 0.01){
            holder.rowDistance.setVisibility(View.INVISIBLE);
        }else{
            holder.rowDistance.setText(String.format("%.2f" , location.distance));
        }

        if(mSelectedItemsIds.size() == 0){
            //reset selected row
            holder.container.setSelected(false);
        }
        holder.rowAddress.setText(location.address);
        holder.rowLinearFacility.removeAllViews();
        for(String fac : location.facilities){
            TextView textView = new TextView(mContext);
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            llp.setMargins(6 , 0 , 0 , 6);
            textView.setLayoutParams(llp);

            textView.setBackgroundResource(R.drawable.rounded_corner);
            textView.setText(fac);
            textView.setTextColor(Color.WHITE);

            holder.rowLinearFacility.addView(textView);
        }

    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    //for action mode function

    public void toggleSelection(int position , View view) {
        selectView(position, !mSelectedItemsIds.get(position) ,view);
    }


    //Remove selected selections
    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }


    //Put or delete selected position into SparseBooleanArray
    public void selectView(int position, boolean value , View view) {
        view.setSelected(value);
        if (value){
            mSelectedItemsIds.put(position, value);
        }
        else{
            mSelectedItemsIds.delete(position);
        }

        notifyDataSetChanged();
    }

    //Get total selected count
    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    //Return all selected ids
    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

    public static class LocationViewHolder extends RecyclerView.ViewHolder{
//            implements View.OnClickListener ,View.OnLongClickListener{
        @BindView(R.id.row_location_name)
        TextView rowLocationName;
        @BindView(R.id.row_rating)
        RatingBar rowRating;
        @BindView(R.id.row_distance)
        TextView rowDistance;
        @BindView(R.id.row_address)
        TextView rowAddress;
        @BindView(R.id.row_linear_facility)
        LinearLayout rowLinearFacility;
        @BindView(R.id.wifi_list_row_relative_layout)
        RelativeLayout container;

        private WiFiListFragment.ItemClickListener mItemClickListener;
        private List<Location> locations;

        public LocationViewHolder(View itemView ,
                                  WiFiListFragment.ItemClickListener itemClickListener
        ,List<Location> locations) {
            super(itemView);
            ButterKnife.bind(this, itemView);
//            itemView.setOnClickListener(this);
//            itemView.setOnLongClickListener(this);
            mItemClickListener = itemClickListener;
            this.locations = locations;
        }

//        @Override
//        public void onClick(View v) {
//            if (mItemClickListener != null) {
//
//                mItemClickListener.onClick(v, locations.get(getAdapterPosition()));
//            }
//        }
//
//        @Override
//        public boolean onLongClick(View v) {
//            if (mItemClickListener != null) {
//
//                mItemClickListener.onLongClick(v, locations.get(getAdapterPosition()));
//            }
//            return true;
//        }
    }
}
