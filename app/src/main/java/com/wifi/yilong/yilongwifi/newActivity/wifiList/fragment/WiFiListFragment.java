package com.wifi.yilong.yilongwifi.newActivity.wifiList.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.wifi.yilong.yilongwifi.App.AppController;
import com.wifi.yilong.yilongwifi.Http.rest.model.Location;
import com.wifi.yilong.yilongwifi.Http.rest.model.User;
import com.wifi.yilong.yilongwifi.Http.rest.retrofitService.RetrofitService;
import com.wifi.yilong.yilongwifi.Infrastructure.MyLog;
import com.wifi.yilong.yilongwifi.Infrastructure.Utils;
import com.wifi.yilong.yilongwifi.R;
import com.wifi.yilong.yilongwifi.newActivity.locationDetail.LocationDetailActivity;
import com.wifi.yilong.yilongwifi.newActivity.wifiList.Adapter.LocationsAdapter;
import com.wifi.yilong.yilongwifi.newActivity.wifiList.callback.ActionBarCallback;
import com.wifi.yilong.yilongwifi.newActivity.wifiList.callback.RecyclerClickListener;
import com.wifi.yilong.yilongwifi.newActivity.wifiList.callback.RecyclerTouchListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/2/20.
 */

public class WiFiListFragment extends Fragment {
    @Inject
    RetrofitService mRetrofitService;
    @Inject
    SharedPreferences mSharedPreferences;
    @BindView(R.id.wifi_list_fragment_home_recyclerview)
    RecyclerView wifiListFragmentHomeRecyclerview;
    @BindView(R.id.wifi_list_fragment_home_swiperefreshlayout)
    SwipeRefreshLayout wifiListFragmentHomeSwiperefreshlayout;


    private User mCurrentUser;
    private LocationsAdapter mLocationsAdapter;
    private android.view.ActionMode mActionMode;
    private Dialog dialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Inject dependency
        ((AppController) getActivity().getApplication()).getCommonComponent().inject(this);
        dialog = Utils.getCustomProgressDialog(getActivity() , "" , null);

    }


    private void loadUserInfo() {
        String id = mSharedPreferences.getString(User.USERID, null);
        if (id != null) {
            mCurrentUser = new Select().from(User.class).where("userId = ?", id).executeSingle();

        } else {
            new NullPointerException("can not find current user");
        }
    }
    public interface ItemClickListener {
        void onClick(View view, Location location);
        boolean onLongClick(View v, Location location);
    }

    private void setLocationList() {
        mLocationsAdapter = new LocationsAdapter(getActivity(), mCurrentUser);
        wifiListFragmentHomeRecyclerview.setAdapter(mLocationsAdapter);
        wifiListFragmentHomeRecyclerview.setLayoutManager(
                new LinearLayoutManager(getActivity().getApplicationContext()));
        wifiListFragmentHomeRecyclerview.setHasFixedSize(true);
        wifiListFragmentHomeRecyclerview.setItemAnimator(new DefaultItemAnimator());
        wifiListFragmentHomeRecyclerview.addOnItemTouchListener(new RecyclerTouchListener(
                getActivity(),
                wifiListFragmentHomeRecyclerview,
                new RecyclerClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        if (mActionMode != null){
                            onListItemSelect(position , view);
                        }else {
                                Intent intent = new Intent(getActivity() , LocationDetailActivity.class);

                                intent.putExtra(Location.LOCATION , mLocationsAdapter.getClickItem(position));
                                startActivity(intent);
                        }

                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        onListItemSelect(position , view);
                    }
                }));


        wifiListFragmentHomeSwiperefreshlayout.setOnRefreshListener(()->{
            getLocations();
        });
    }
    @Override
    public void onPause() {
        super.onPause();
        if(mActionMode != null){
            mLocationsAdapter.removeSelection();;
            mActionMode.finish();
        }
    }

    //List item select method
    private void onListItemSelect(int position , View view) {
        mLocationsAdapter.toggleSelection(position ,view);//Toggle the selection

        boolean hasCheckedItems = mLocationsAdapter.getSelectedCount() > 0;//Check if any items are already selected or not


        if (hasCheckedItems && mActionMode == null){
            // there are some selected items, start the actionMode
            mActionMode = getActivity().startActionMode(new ActionBarCallback(this , mLocationsAdapter));
        }
        else if (!hasCheckedItems && mActionMode != null){
            // there no selected items, finish the actionMode
            mActionMode.finish();
        }


        if (mActionMode != null){
            //set action mode title on item selection
            mActionMode.setTitle(String.valueOf(mLocationsAdapter
                    .getSelectedCount()) + " selected");
        }



    }
    //Set action mode null after use
    public void setNullToActionMode() {
        if (mActionMode != null)
            mActionMode = null;
    }

    public void deleteRows(){
        SparseBooleanArray selected = mLocationsAdapter.getSelectedIds();//Get selected ids

        //Loop all selected ids
        for (int i = (selected.size() - 1); i >= 0; i--) {
            if (selected.valueAt(i)) {
                //If current id is selected remove the item via key
//                item_models.remove(selected.keyAt(i));
                mLocationsAdapter.notifyDataSetChanged();//notify adapter

            }
        }
//        Toast.makeText(getActivity(), selected.size() + " item deleted.", Toast.LENGTH_SHORT).show();//Show Toast
        mActionMode.finish();//Finish action mode after use
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wifi_list_home, container, false);
        ButterKnife.bind(this, view);
        loadUserInfo();
//        setLocationList();
        getLocations();

        return view;
    }


    private void getLocations() {
        dialog.show();
        mRetrofitService
                .getLocations("-0.9690884", "51.455041", "20")
                .doOnNext(locations -> {
                    if (locations != null && locations.size() > 0) {
                        ActiveAndroid.beginTransaction();
                        try {
                            for (Location location : locations) {
                                long id = location.save();
                                location.user = mCurrentUser;
                                MyLog.Debug("location id : " + id);
                            }
                            ActiveAndroid.setTransactionSuccessful();
                        }catch (Exception ex){
                            MyLog.Debug(ex.getMessage());
                        }
                        finally {
                            ActiveAndroid.endTransaction();
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(locations -> {
                }, throwable -> {
                    dialog.dismiss();
                }, () -> {
                    dialog.dismiss();
                    //update location data
                    setLocationList();
                    if(wifiListFragmentHomeSwiperefreshlayout.isRefreshing()){
                        wifiListFragmentHomeSwiperefreshlayout.setRefreshing(false);
                    }
                    mLocationsAdapter.notifyDataSetChanged();

                });
    }
}
