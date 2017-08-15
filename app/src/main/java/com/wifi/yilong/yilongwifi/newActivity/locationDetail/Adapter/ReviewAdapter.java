package com.wifi.yilong.yilongwifi.newActivity.locationDetail.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.wifi.yilong.yilongwifi.Http.rest.model.Review;
import com.wifi.yilong.yilongwifi.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/26.
 */

public class ReviewAdapter extends  RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    Context mContext;
    List<Review> mReviews;

    public ReviewAdapter(Context context, List<Review> reviews) {
        mContext = context;
        mReviews = reviews;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ViewHolder viewHolder = null;
        Review review = mReviews.get(position);
        viewHolder = holder;

        viewHolder.locationDetailReviewRatingBar.setRating(review.rating);
        viewHolder.locationDetailReviewAuthor.setText(review.author);
        viewHolder.locationDetailReviewCreatedOn.setText(review.createdOn.toString());
        viewHolder.locationDetailReviewReviewText.setText(review.reviewText);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }



    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.location_detail_review_ratingBar)
        RatingBar locationDetailReviewRatingBar;
        @BindView(R.id.location_detail_review_author)
        TextView locationDetailReviewAuthor;
        @BindView(R.id.location_detail_review_createdOn)
        TextView locationDetailReviewCreatedOn;
        @BindView(R.id.location_detail_review_review_text)
        TextView locationDetailReviewReviewText;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
