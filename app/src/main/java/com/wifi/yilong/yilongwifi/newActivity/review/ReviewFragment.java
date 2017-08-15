package com.wifi.yilong.yilongwifi.newActivity.review;

import android.app.Dialog;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.wifi.yilong.yilongwifi.App.AppController;
import com.wifi.yilong.yilongwifi.Http.rest.model.Location;
import com.wifi.yilong.yilongwifi.Http.rest.model.Review;
import com.wifi.yilong.yilongwifi.Http.rest.retrofitService.RetrofitService;
import com.wifi.yilong.yilongwifi.Infrastructure.Utils;
import com.wifi.yilong.yilongwifi.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/1.
 */

public class ReviewFragment extends Fragment {
    @BindView(R.id.tool_bar_center_title)
    TextView toolBarCenterTitle;
//    @BindView(R.id.review_detail_new_fragment_spinner)
//    Spinner reviewDetailNewFragmentSpinner;
    @BindView(R.id.review_detail_new_fragment_text)
    EditText reviewDetailNewFragmentText;
    @BindView(R.id.review_detail_new_fragment_container)
    LinearLayout reviewDetailNewFragmentContainer;
    @BindView(R.id.review_detail_new_fragment_fab)
    FloatingActionButton reviewDetailNewFragmentFab;
    @BindView(R.id.review_detail_new_fragment_toolbar)
    Toolbar toolbar;

    Review review;
    Location mCurrentLocation;

    @Inject
    RetrofitService mRetrofitService;
    @Inject
    SharedPreferences mSharedPreferences;
    @BindView(R.id.review_detail_new_fragment_ratingbar)
    RatingBar reviewDetailNewFragmentRatingbar;

    private boolean isAddReview = true;

    private Dialog dialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args.containsKey(Review.REVIEW)) {
            isAddReview = false;

            String reviewId = getArguments().get(Review.REVIEW).toString();
            review = new Select().from(Review.class).where("id = ?", reviewId).executeSingle();
            mCurrentLocation = review.location;
        } else {
            isAddReview = true;

            String locationId = getArguments().get(Location.LOCATION).toString();
            mCurrentLocation = new Select().from(Location.class).where("id = ?", locationId).executeSingle();
        }


        //Inject dependency
        ((AppController) getActivity().getApplication()).getCommonComponent().inject(this);
        dialog = Utils.getCustomProgressDialog(getActivity() , "" , null);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.review_detail_new_fragment, container, false);
        ButterKnife.bind(this, v);
        toolbar.setNavigationIcon(R.drawable.left);
        toolBarCenterTitle.setText(R.string.location_detail_tool_bar_title);
        toolbar.setNavigationOnClickListener(view -> {
            getActivity().finish();
        });
        reviewDetailNewFragmentFab.setImageResource(isAddReview ? R.drawable.check : R.drawable.update);
//        String[] stars = getResources().getStringArray(R.array.review_detail_star_values);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
//                android.R.layout.simple_spinner_item,
//                stars);
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        reviewDetailNewFragmentSpinner.setAdapter(arrayAdapter);
        reviewDetailNewFragmentRatingbar.setOnRatingBarChangeListener(( ratingBar,  rating,  fromUse)->{

        });
        if (isAddReview) {

        } else {
//            reviewDetailNewFragmentSpinner.set
            reviewDetailNewFragmentRatingbar.setRating(review.rating);
            reviewDetailNewFragmentText.setText(review.reviewText);
        }

        return v;
    }

    @OnClick(R.id.review_detail_new_fragment_fab)
    public void onClick() {
//        Snackbar.make(reviewDetailNewFragmentContainer , "add review success" , Snackbar.LENGTH_LONG)
//                .setAction("this is action" , (v)->{
//                    Utils.Tip(getActivity() , "hide it");
//                })
//                .show();
        dialog.show();
        if (isAddReview) {
            addReivew();
        } else {
            updateReview();
        }

    }

    public void updateReview() {
        Utils.TipSnackBar(reviewDetailNewFragmentContainer, "update ok", null, "");
        dialog.dismiss();
    }

    public void addReivew() {

        mRetrofitService.addReview(mCurrentLocation.id,
//                Float.parseFloat(reviewDetailNewFragmentSpinner.getSelectedItem().toString()),
                reviewDetailNewFragmentRatingbar.getRating(),
                reviewDetailNewFragmentText.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(review -> {
                    Utils.Tip(getActivity(), review.reviewText);
                }, throwable -> {
                    Utils.Tip(getActivity(), throwable.getMessage());
                    dialog.dismiss();
                }, () -> {
                    dialog.dismiss();
                    Snackbar.make(reviewDetailNewFragmentContainer, "add review success", Snackbar.LENGTH_LONG)
                            .setAction("this is action", (v) -> {
                                Utils.Tip(getActivity(), "hide it");
                            })
                            .show();
//                    getActivity().finish();
                });
    }
}
