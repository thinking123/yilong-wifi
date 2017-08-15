package com.wifi.yilong.yilongwifi.Activity.Review;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.wifi.yilong.yilongwifi.Infrastructure.Utils;
import com.wifi.yilong.yilongwifi.Model.Review;
import com.wifi.yilong.yilongwifi.R;

/**
 * Created by Administrator on 2017/1/28.
 */

public class ReviewDetailFragment extends Fragment {

    private Review mReview;
    private RatingBar mRatingBar;
    private TextView mAuthor;
    private TextView mCreatedOn;
    private  TextView mReviewText;
    public static ReviewDetailFragment newInstance(Review review){
        Bundle args = new Bundle();
        args.putSerializable(Review.REVIEW , review);

        ReviewDetailFragment rdfg = new ReviewDetailFragment();
        rdfg.setArguments(args);

        return rdfg;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            if(NavUtils.getParentActivityName(getActivity()) != null){
                getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        View v = inflater.inflate(R.layout.review_detail_fragment , container , false);
        mRatingBar = (RatingBar)v.findViewById(R.id.review_detail_fragment_ratingBar);
        mAuthor = (TextView)v.findViewById(R.id.review_detail_fragment_author);
        mCreatedOn = (TextView)v.findViewById(R.id.review_detail_fragment_created_on);
        mReviewText = (TextView)v.findViewById(R.id.review_detail_fragment_review_text);

        mRatingBar.setRating(mReview.getRating());
        mAuthor.setText(mReview.getAuthor());
        mCreatedOn.setText(Utils.DateToString(mReview.getCreatedOn()));
        mReviewText.setText(mReview.getReviewText());

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if(args != null){
            mReview = (Review)args.getSerializable(Review.REVIEW);
        }


    }
}
