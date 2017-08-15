package com.wifi.yilong.yilongwifi.Activity.WiFiList;

import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.wifi.yilong.yilongwifi.Activity.About.AboutActivity;
import com.wifi.yilong.yilongwifi.Activity.Dialog.ProgressDialogFragment;
import com.wifi.yilong.yilongwifi.Activity.Dialog.TipDailogFragment;
import com.wifi.yilong.yilongwifi.Activity.LocationDetail.LocationDetailViewPagerActivity;
import com.wifi.yilong.yilongwifi.Activity.Signin.SigninActivity;
import com.wifi.yilong.yilongwifi.Http.HttpHelper;
import com.wifi.yilong.yilongwifi.Http.ServerURL;
import com.wifi.yilong.yilongwifi.Infrastructure.AppConstant;
import com.wifi.yilong.yilongwifi.Infrastructure.StoreHelper;
import com.wifi.yilong.yilongwifi.Model.Location;
import com.wifi.yilong.yilongwifi.Model.LocationLab;
import com.wifi.yilong.yilongwifi.R;
import com.wifi.yilong.yilongwifi.Service.LocationService;

import java.util.List;

/**
 * Created by Administrator on 2017/1/24.
 */

public class WiFiListFragment extends ListFragment {
    private final int OnceFetchCount = 10;

    private List<Location> mLocations;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        new WiFiListAsyncTask().execute(OnceFetchCount);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu , inflater);
        inflater.inflate(R.menu.wifi_list_menu , menu );

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            MenuItem searchItem = menu.findItem(R.id.wifi_list_search);
            SearchView searchView = (SearchView)searchItem.getActionView();

            SearchManager searchManager = (SearchManager)getActivity()
                    .getSystemService(Context.SEARCH_SERVICE);
            ComponentName name =getActivity().getComponentName();

            SearchableInfo info = searchManager.getSearchableInfo(name);

            searchView.setSearchableInfo(info);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.wifi_list_add:

                return true;
            case R.id.wifi_list_search:
                getActivity().onSearchRequested();
                return true;
            case R.id.wifi_list_signout:

                TipDailogFragment fg = TipDailogFragment.newInstance(R.string.dialog_singout_title,
                        R.string.dialog_singout_msg);
                fg.setTargetFragment(WiFiListFragment.this, AppConstant.REQUEST.REQUEST_TIP_DIALOG);
                fg.show(getFragmentManager(), AppConstant.Tag.TIPDIALOGFRAGMENT);


                return true;
            case R.id.wifi_list_about:
                Intent i = new Intent(getActivity(), AboutActivity.class);
                startActivity(i);
                return true;
            case R.id.wifi_list_toggle_notification:
                boolean shouldStartAlarmService = !LocationService.isServiceStarted(getActivity());
                LocationService.startLocationService(getActivity() , shouldStartAlarmService);

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
                    getActivity().invalidateOptionsMenu();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem item = menu.findItem(R.id.wifi_list_toggle_notification);
        boolean isOn = LocationService.isServiceStarted(getActivity());
        item.setTitle(isOn ? R.string.menu_wifi_list_toggle_close_notification : R.string.menu_wifi_list_toggle_open_notification);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case AppConstant.REQUEST.REQUEST_TIP_DIALOG:
                if(resultCode == Activity.RESULT_OK){
                    signout();
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(AppConstant.Action.SHOW_NOTIFICATION);
        getActivity().registerReceiver(mNewLocationReceiver ,
                filter ,
                AppConstant.Signature.ACTION_SHOW_NEW_LOCATION_SIGNATURE , null);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mNewLocationReceiver);
    }

    private void signout(){
        Intent i = new Intent(getActivity() , SigninActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Location location = mLocations.get(position);

        Intent i = new Intent(getActivity(), LocationDetailViewPagerActivity.class);
        i.putExtra(Location.ID, location.getId());
        startActivityForResult(i , AppConstant.REQUEST.REQUEST_LOCATION_DETAIL_VIEWPAGER);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wifi_list , container , false);
        return v;
    }

    private String formatUrl(){
        String url = ServerURL.GetLocations;

        url = Uri.parse(url).buildUpon()
                .appendQueryParameter("lng" , "-0.9690884")
                .appendQueryParameter("lat" , "51.455041")
                .appendQueryParameter("maxDistance" , "20")
                .build().toString();

        return  url;
        //lng=-0.9690884&lat=51.455041&maxDistance=20
//        return String.format("%s?lng=-0.9690884&lat=51.455041&maxDistance=20" , ServerURL.GetLocations);
//        return String.format("%s?lng=%f&lat=%.8f&maxDistance=%f" , ServerURL.GetLocations,-0.9690884,51.455041,20.0);

    }

    private void updateList(List<Location> locations){
        LocationsAdapter ladp = new LocationsAdapter(locations);
        mLocations = locations;
        LocationLab.get(getActivity()).setLocations(mLocations);
        this.setListAdapter(ladp);

    }

    public void updateData(){
        new WiFiListAsyncTask().execute();
    }

    private class LocationsAdapter extends ArrayAdapter<Location>{
        public LocationsAdapter(List<Location> ls){
            super(getActivity() , 0 , ls);
        }
        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.wifi_list_row ,
                        parent , false);
            }

            TextView mLocationNameTextView = (TextView)convertView.findViewById(R.id.row_location_name);
            TextView mDistanceextView = (TextView)convertView.findViewById(R.id.row_distance);
            TextView mAddressTextView = (TextView)convertView.findViewById(R.id.row_address);
            RatingBar mRatingRatingBar = (RatingBar)convertView.findViewById(R.id.row_rating);
            LinearLayout mLinearLayoutFacilities = (LinearLayout)convertView.findViewById(R.id.row_linear_facility);

            Location location = getItem(position);

            mLocationNameTextView.setText(location.getName());
            mDistanceextView.setText(new Float(location.getDistance()).toString());
            mAddressTextView.setText(location.getAddress());
            mRatingRatingBar.setRating(location.getRating());

            for (String fac : location.getFacilities()) {
                TextView tv = new TextView(getActivity());
                tv.setText(fac);
                tv.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
                tv.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                mLinearLayoutFacilities.addView(tv);
            }



            return  convertView;
        }
    }
    private class WiFiListAsyncTask extends AsyncTask<Integer , Void , List<Location>>{
        private ProgressDialogFragment pdfg;
        @Override
        protected void onPreExecute() {
            pdfg = ProgressDialogFragment.show(getActivity(), R.string.wifi_list_loading_title, R.string.wifi_list_loading_msg);
        }

        @Override
        protected List<Location> doInBackground(Integer... params) {
            return HttpHelper.GetLocations(formatUrl());
        }

        @Override
        protected void onPostExecute(List<Location> locations) {
            if(pdfg != null){
                pdfg.dismiss();
            }
          //  AppConstant.SharedPreferencesKey.LAST_LOCATION_ID
            if(locations != null && locations.size() > 0){
                StoreHelper.putToSharedPreferences(getActivity() , AppConstant.SharedPreferencesKey.LAST_LOCATION_ID , locations.get(0).getId());
            }
            updateList(locations);
        }
    }

    private BroadcastReceiver mNewLocationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setResultCode(Activity.RESULT_CANCELED);
        }
    };
}
