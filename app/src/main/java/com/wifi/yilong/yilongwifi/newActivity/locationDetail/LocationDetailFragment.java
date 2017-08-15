package com.wifi.yilong.yilongwifi.newActivity.locationDetail;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.wifi.yilong.yilongwifi.App.AppController;
import com.wifi.yilong.yilongwifi.Http.rest.model.Location;
import com.wifi.yilong.yilongwifi.Http.rest.model.OpeningTime;
import com.wifi.yilong.yilongwifi.Http.rest.model.Review;
import com.wifi.yilong.yilongwifi.Http.rest.retrofitService.RetrofitService;
import com.wifi.yilong.yilongwifi.Infrastructure.MyLog;
import com.wifi.yilong.yilongwifi.Infrastructure.Utils;
import com.wifi.yilong.yilongwifi.R;
import com.wifi.yilong.yilongwifi.newActivity.locationDetail.Adapter.OpeninghoursAdapter;
import com.wifi.yilong.yilongwifi.newActivity.locationDetail.Adapter.ReviewAdapter;
import com.wifi.yilong.yilongwifi.newActivity.reviewlist.ReviewListActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/2/22.
 */

public class LocationDetailFragment extends Fragment {
    @BindView(R.id.tool_bar_center_title)
    TextView toolBarCenterTitle;
    @BindView(R.id.location_detail_new_fragment_ratingBar)
    RatingBar locationDetailNewFragmentRatingBar;
    @BindView(R.id.location_detail_new_fragment_address)
    TextView locationDetailNewFragmentAddress;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.location_detail_new_fragment_opening_hours)
    RecyclerView locationDetailNewFragmentOpeningHours;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.location_detail_new_fragment_facilities)
    LinearLayout locationDetailNewFragmentFacilities;

//    @BindView(R.id.location_detail_new_fragment_customer_reviews)
//    RecyclerView locationDetailNewFragmentCustomerReviews;
//    @BindView(R.id.location_detail_new_fragment_floatingActionButton)
//    FloatingActionButton locationDetailNewFragmentFloatingActionButton;
    @BindView(R.id.location_detail_new_fragment_container)
    FrameLayout locationDetailNewFragmentContainer;
    @BindView(R.id.location_detail_new_fragment_toolbar)
    Toolbar toolbar;

    @Inject
    RetrofitService mRetrofitService;
    @Inject
    SharedPreferences mSharedPreferences;

    Location mCurrentLocation;
    Dialog dialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mCurrentLocation = (Location) args.get(Location.LOCATION);
        }
        //Inject dependency
        ((AppController) getActivity().getApplication()).getCommonComponent().inject(this);

        dialog = Utils.getCustomProgressDialog(getActivity() , "" , null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.location_detail_new_fragment, container, false);
        ButterKnife.bind(this, view);
        //set tool bar
        setHasOptionsMenu(true);
        toolbar.setNavigationIcon(R.drawable.left);
        toolBarCenterTitle.setText(R.string.location_detail_tool_bar_title);
        toolbar.setNavigationOnClickListener(v -> {
            getActivity().finish();
        });

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (mCurrentLocation != null) {
            getLocationDetail();
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.location_detail_new_menu , menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.location_detail_menu_comments:
                Intent intent = new Intent(getActivity() , ReviewListActivity.class);
                intent.putExtra(Location.LOCATION , mCurrentLocation.getId());
                startActivity(intent);
                return true;
            case android.R.id.home:
                getActivity().finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getLocationDetail() {
        dialog.show();
        mRetrofitService
                .getLocationById(mCurrentLocation.id)
                .doOnNext(location -> {
                    if (location != null) {
                        ActiveAndroid.beginTransaction();
                        try {
                            long id = 0;
                            id = location.save();
                            MyLog.Debug("id = " + id);

                            for (OpeningTime time : location.openingTimes) {
                                if (time != null) {
                                    time.location = location;
                                    id = time.save();
                                    MyLog.Debug("id = " + id);

                                }
                            }

                            for (Review review : location.reviews){
                                if(review != null){
                                    review.location = location;
                                    id = review.save();
                                    MyLog.Debug("id = " + id);
                                }
                            }


                            ActiveAndroid.setTransactionSuccessful();
                        }finally {
                            ActiveAndroid.endTransaction();
                        }

                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(location -> {
                    //update location value
                    mCurrentLocation = location;
                }, throwable -> {
                    dialog.dismiss();
                    //error
                    Utils.Tip(getActivity(),
                            "get location detail failure : " + throwable.getMessage());
                }, () -> {
                    dialog.show();
                    //completed
                    updateUI();
                });
    }



    private void updateUI() {
        //title
        toolBarCenterTitle.setText(mCurrentLocation.name);
        locationDetailNewFragmentRatingBar.setRating(mCurrentLocation.rating);
        locationDetailNewFragmentAddress.setText(mCurrentLocation.address);
        //opening hours
        OpeninghoursAdapter openinghoursAdapter = new OpeninghoursAdapter(
                mCurrentLocation.getOpeningTimes(),
                getActivity()
        );
        locationDetailNewFragmentOpeningHours.setAdapter(openinghoursAdapter);
        locationDetailNewFragmentOpeningHours.setLayoutManager(
                new LinearLayoutManager(getActivity().getApplicationContext()));
        locationDetailNewFragmentOpeningHours.setHasFixedSize(true);
        locationDetailNewFragmentOpeningHours.setItemAnimator(new DefaultItemAnimator());
        //facilites
        for (String fac : mCurrentLocation.facilities) {
            TextView textView = new TextView(getActivity());
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            llp.setMargins(6, 0, 0, 6);
            textView.setLayoutParams(llp);

            textView.setBackgroundResource(R.drawable.rounded_corner);
            textView.setText(fac);
            textView.setTextColor(Color.WHITE);
            locationDetailNewFragmentFacilities.addView(textView);
        }
        //reviews
        ReviewAdapter reviewAdapter = new ReviewAdapter(
                getActivity(),
                mCurrentLocation.getReviews()
        );
//        locationDetailNewFragmentCustomerReviews.setAdapter(reviewAdapter);
//        locationDetailNewFragmentCustomerReviews.setLayoutManager(
//                new LinearLayoutManager(getActivity().getApplicationContext()));
//        locationDetailNewFragmentCustomerReviews.setHasFixedSize(true);
//        locationDetailNewFragmentCustomerReviews.setItemAnimator(new DefaultItemAnimator());
//        locationDetailNewFragmentCustomerReviews.setVisibility(
//                mCurrentLocation.getReviews().size() > 0 ? View.VISIBLE : View.INVISIBLE);

//        locationDetailNewFragmentCustomerReviews.setVisibility(View.INVISIBLE);
    }
}
