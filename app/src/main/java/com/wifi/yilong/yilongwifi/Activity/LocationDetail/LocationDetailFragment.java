package com.wifi.yilong.yilongwifi.Activity.LocationDetail;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.wifi.yilong.yilongwifi.Activity.Dialog.ProgressDialogFragment;
import com.wifi.yilong.yilongwifi.Activity.Review.AddReviewActivity;
import com.wifi.yilong.yilongwifi.Activity.Review.ReviewDetailActivity;
import com.wifi.yilong.yilongwifi.Http.HttpHelper;
import com.wifi.yilong.yilongwifi.Http.ServerURL;
import com.wifi.yilong.yilongwifi.Infrastructure.AppConstant;
import com.wifi.yilong.yilongwifi.Infrastructure.MyLog;
import com.wifi.yilong.yilongwifi.Infrastructure.Utils;
import com.wifi.yilong.yilongwifi.Model.Location;
import com.wifi.yilong.yilongwifi.Model.LocationLab;
import com.wifi.yilong.yilongwifi.Model.OpeningTime;
import com.wifi.yilong.yilongwifi.Model.Review;
import com.wifi.yilong.yilongwifi.R;

import java.util.List;

/**
 * Created by Administrator on 2017/1/24.
 */

public class LocationDetailFragment extends Fragment {
    private RatingBar mRatingBar;
    private TextView mAddressTextView;
    private LinearLayout mOpenTimeLinearLayout;
    private LinearLayout mFacilitiesLinearLayout;
    private ListView mReviewsListView;
    private Location mLocation;
    private List<Review> mReviews;
//    private static String _id;
    public static LocationDetailFragment newInstance(String id){
        LocationDetailFragment ldfg = new LocationDetailFragment();
        Bundle args = new Bundle();
        args.putString(Location.ID , id);
//        _id = id;
        ldfg.setArguments(args);

        return ldfg;
    }

    public void testTip(){
        Utils.Tip(getActivity() , "test tip");
    }
//    @Override
//    public boolean getUserVisibleHint() {
//        new LocationDetailAsyncTask().execute(_id);
//        return super.getUserVisibleHint();
//    }
//
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//
////        new LocationDetailAsyncTask().execute(_id);
//        super.setUserVisibleHint(isVisibleToUser);
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        String id = args.getString(Location.ID);

        mLocation = LocationLab.get(getActivity()).getLocation(id);
        new LocationDetailAsyncTask().execute(id);
        setHasOptionsMenu(true);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MyLog.Debug("onAttach");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case AppConstant.REQUEST.REQUEST_ADD_REVIEW:
                if(requestCode == Activity.RESULT_OK){
                    Utils.Tip(getActivity() ,  R.string.tip_add_review_success );
                }
                break;
        }
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        MyLog.Debug("onAttachFragment");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.location_detail_menu , menu);
    }

    @TargetApi(11)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            if(NavUtils.getParentActivityName(getActivity()) != null){
                getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
        View v = inflater.inflate(R.layout.location_detail_fragment , container , false);

        mRatingBar = (RatingBar)v.findViewById(R.id.location_detail_rating);
        mAddressTextView =(TextView)v.findViewById(R.id.location_detail_address);
        mOpenTimeLinearLayout = (LinearLayout)v.findViewById(R.id.location_detail_linear_opening_time);
        mFacilitiesLinearLayout = (LinearLayout)v.findViewById(R.id.location_detail_linear_facilities);
        mReviewsListView = (ListView)v.findViewById(R.id.location_detail_list_view_reviews);

        TextView header = (TextView)inflater.inflate(R.layout.review_list_header , mReviewsListView , false);
        if(header != null){
            header.setText(R.string.custom_review);
            mReviewsListView.addHeaderView(header);
        }

        mReviewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Review review = mReviews.get(position);
                Review review = (Review)parent.getItemAtPosition(position);
                Intent i = new Intent(getActivity() , ReviewDetailActivity.class);
                i.putExtra(Review.REVIEW , review);
                startActivity(i);
            }
        });

        return v;
    }

    private void updateUI(){
        if(mLocation != null){
            mRatingBar.setNumStars(5);
            mRatingBar.setRating(mLocation.getRating());
//            mRatingBar.setVisibility(View.INVISIBLE);
            mAddressTextView.setText(mLocation.getAddress());

            if(mLocation != null){
                if(mLocation.getOpeningTimes() != null){
                    for(OpeningTime openingTime : mLocation.getOpeningTimes()){
                        TextView tv = new TextView(getActivity());
                        tv.setText(openingTime.toString());
                        tv.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                        tv.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

                        mOpenTimeLinearLayout.addView(tv);
                    }
                }

                if(mLocation.getFacilities() != null){
                    for (String fac : mLocation.getFacilities()) {
                        TextView tv = new TextView(getActivity());
                        tv.setText(fac);
                        tv.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
                        tv.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
                        mFacilitiesLinearLayout.addView(tv);
                    }
                }

                if(mLocation.getReviews() != null){
                    mReviews = mLocation.getReviews();
                    ReviewAdapter adapter = new ReviewAdapter(mLocation.getReviews());
                    mReviewsListView.setAdapter(adapter);
                }

            }

        }
    }

    private String formatUrl(String id){
        return ServerURL.GetLocationDetail + id;
    }

    private class ReviewAdapter extends ArrayAdapter<Review>{

        private List<Review> mReviews;
        private class ViewHolder{
            TextView mAuthor;
            TextView mCreatedOn;
            TextView mReviewText;
            RatingBar mRatingBar;
        }
        public ReviewAdapter(List<Review> reviews){
            super(getActivity() , 0 , reviews);
            mReviews = reviews;
        }
        @Override
        public int getCount() {
            return mReviews.size();
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.review_list_row ,
                        null);
                ViewHolder holer = new ViewHolder();
                holer.mAuthor = (TextView) convertView.findViewById(R.id.location_detail_review_author);
                holer.mCreatedOn = (TextView) convertView.findViewById(R.id.location_detail_review_createdOn);
                holer.mReviewText = (TextView) convertView.findViewById(R.id.location_detail_review_review_text);
                holer.mRatingBar = (RatingBar) convertView.findViewById(R.id.location_detail_review_ratingBar);

                convertView.setTag(holer);
            }

            Review review = mReviews.get(position);
            ViewHolder viewHolder = (ViewHolder)convertView.getTag();
            viewHolder.mAuthor.setText(review.getAuthor());
            viewHolder.mCreatedOn.setText(review.getCreatedOn().toString());
            viewHolder.mReviewText.setText(review.getReviewText());
            viewHolder.mRatingBar.setRating(review.getRating());

            return convertView;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if(NavUtils.getParentActivityName(getActivity()) != null){
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            case R.id.menu_location_detail_add_review:
                Intent i = new Intent(getActivity() , AddReviewActivity.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class LocationDetailAsyncTask extends AsyncTask<String , Void , Location>{
        ProgressDialogFragment pdfg;
        @Override
        protected void onPostExecute(Location location) {
            if(pdfg != null){
                pdfg.dismiss();
            }

            location.setDistance(mLocation.getDistance());
            mLocation = location;
            updateUI();
        }

        @Override
        protected void onPreExecute() {
//            pdfg = ProgressDialogFragment.show(getActivity() ,
//                    R.string.location_detail_loading_title,
//                    R.string.location_detail_loading_msg);

        }

        @Override
        protected Location doInBackground(String... params) {
//            Location lc = null;
            try {
//                lc = HttpHelper.GetLocationDetail(formatUrl(mLocation.getId()));
                return HttpHelper.GetLocationDetail(formatUrl(mLocation.getId()));
            } catch (Exception e) {
                MyLog.Debug(e.getMessage());
            }

            return null;
//            return lc;
        }
    }
}
