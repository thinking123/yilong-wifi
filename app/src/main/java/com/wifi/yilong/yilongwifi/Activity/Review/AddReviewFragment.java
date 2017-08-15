package com.wifi.yilong.yilongwifi.Activity.Review;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.wifi.yilong.yilongwifi.Activity.Dialog.ProgressDialogFragment;
import com.wifi.yilong.yilongwifi.DB.DatabaseHelper;
import com.wifi.yilong.yilongwifi.Http.ServerURL;
import com.wifi.yilong.yilongwifi.Infrastructure.AppConstant;
import com.wifi.yilong.yilongwifi.Infrastructure.MyLog;
import com.wifi.yilong.yilongwifi.Infrastructure.StoreHelper;
import com.wifi.yilong.yilongwifi.Infrastructure.Utils;
import com.wifi.yilong.yilongwifi.Model.Review;
import com.wifi.yilong.yilongwifi.Model.User;
import com.wifi.yilong.yilongwifi.R;
import com.wifi.yilong.yilongwifi.ThreadHelper.TaskHandlerThead;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/1/28.
 */

public class AddReviewFragment extends Fragment {
    private RatingBar mRatingBar;
    private EditText mReviewTextEditText;
    private Review mReview;
    private Button mSubmit;
    private TaskHandlerThead<JSONObject> mTaskHandlerThead;
    private ProgressDialogFragment pdfg;
    private boolean isRated =true;

    public static final int MINREVIEWTEXTLENGTH = 5;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            if(NavUtils.getParentActivityName(getActivity()) != null){
                getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
        mReview = new Review();
        if(savedInstanceState != null){

        }

        mTaskHandlerThead = new TaskHandlerThead<JSONObject>(new TaskHandlerThead.TaskCompleted(){

            @Override
            public void TaskCompletedCallback(Object result) {
                if(pdfg != null){
                    pdfg.dismiss();
                }

                boolean isOk = false;
                if(result instanceof Review){
                    Review review = (Review)result;
                    isOk = true;
                    addReviewSuccess(review);
                }else{
                    mSubmit.setEnabled(true);
                    Utils.Tip(getActivity() , isOk ? R.string.tip_add_review_success : R.string.tip_add_review_failure);
                }


            }
        } , new Handler());

        mTaskHandlerThead.start();
        mTaskHandlerThead.getLooper();
    }

    private void addReviewSuccess(Review review){
        Intent i = new Intent();
        i.putExtra(Review.REVIEW , review);
        getActivity().setResult(Activity.RESULT_OK ,i);
        getActivity().finish();
    }
    private JSONObject formatData() {
        JSONObject js = new JSONObject();

        try {
            js.put(Review.RATING, String.valueOf(mRatingBar.getRating()));
            js.put(Review.REVIEWTEXT, mReviewTextEditText.getText());
        } catch (JSONException e) {
            MyLog.Debug(e.getMessage());
        }


        return js;
    }
    private void submitData(){
        pdfg = ProgressDialogFragment.show(getActivity() ,R.string.add_review_loading_title , R.string.add_review_loading_msg);
        mTaskHandlerThead.queueTask(TaskHandlerThead.POSTREVIEWDATA , formatData() , formatUrl() , formatHttpHeader());
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_review_fragment , container , false);
        mRatingBar = (RatingBar)v.findViewById(R.id.add_review_ratingBar);
        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                isRated = true;
            }
        });
        mReviewTextEditText = (EditText)v.findViewById(R.id.add_review_text);
        mReviewTextEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputFields();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mSubmit = (Button)v.findViewById(R.id.add_review_submit);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();
                mSubmit.setEnabled(false);
            }
        });

        mSubmit.setEnabled(false);

        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

            }
        });
        return  v;
    }

    private void checkInputFields(){
        if(isRated && mReviewTextEditText.getText().length() > MINREVIEWTEXTLENGTH){
            mSubmit.setEnabled(true);
        }else {
            mSubmit.setEnabled(false);
        }
    }
    private String formatUrl(){
        String locationId = StoreHelper.getStringFromSharedPreferences(getActivity() , AppConstant.SharedPreferencesKey.ID);
        return ServerURL.AddReview + locationId + "/reviews";
    }
    private HashMap<String , String> formatHttpHeader(){
        String locationId = StoreHelper.getStringFromSharedPreferences(getActivity() , AppConstant.SharedPreferencesKey.ID);
        HashMap<String , String> headers = new HashMap<>();
        //Authorization:Bearer + ' ' + token(
        User user = DatabaseHelper.get(getActivity()).getUser(locationId);
        headers.put(ServerURL.AUTHORIZATION , ServerURL.BEARER + user.getToken());
        return headers;
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

    @Override
    public void onDestroy() {
        mTaskHandlerThead.quit();
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        mTaskHandlerThead.clearTaskQueue();
        super.onDestroyView();
    }
}
