package com.wifi.yilong.yilongwifi.newActivity.reviewlist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.wifi.yilong.yilongwifi.Http.rest.model.Review;
import com.wifi.yilong.yilongwifi.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/9.
 */

public class ReviewListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Review> mReviews;
    public ReviewListAdapter(Context context , List<Review> reviews) {
        mContext = context;
        mReviews = reviews;
    }

    @Override
    public int getCount() {
        return mReviews.size();
    }

    @Override
    public Review getItem(int position) {
        return mReviews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.review_list_row, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder viewHolder = (ViewHolder)convertView.getTag();
        Review review = mReviews.get(position);

        viewHolder.locationDetailReviewRatingBar.setRating(review.rating);
        viewHolder.locationDetailReviewAuthor.setText(review.author);
        viewHolder.locationDetailReviewCreatedOn.setText(review.createdOn.toString());
        viewHolder.locationDetailReviewReviewText.setText(review.reviewText);

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.location_detail_review_ratingBar)
        RatingBar locationDetailReviewRatingBar;
        @BindView(R.id.location_detail_review_author)
        TextView locationDetailReviewAuthor;
        @BindView(R.id.location_detail_review_createdOn)
        TextView locationDetailReviewCreatedOn;
        @BindView(R.id.location_detail_review_review_text)
        TextView locationDetailReviewReviewText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
