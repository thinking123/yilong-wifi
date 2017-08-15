package com.wifi.yilong.yilongwifi.newActivity.wifiList.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.wifi.yilong.yilongwifi.Http.rest.model.Review;
import com.wifi.yilong.yilongwifi.Http.rest.model.User;
import com.wifi.yilong.yilongwifi.Infrastructure.Utils;
import com.wifi.yilong.yilongwifi.R;
import com.wifi.yilong.yilongwifi.newActivity.review.ReviewActivity;
import com.wifi.yilong.yilongwifi.newActivity.reviewlist.Adapter.ReviewListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017/2/20.
 */

public class MyReivewsFragment extends Fragment {
    @BindView(R.id.my_review_list_fragment_swipe_listView)
    SwipeMenuListView reivewListFragmentSwipeListView;

    List<Review> mReviews;
    @BindView(R.id.my_review_list_fragment_empty)
    TextView myReviewListFragmentEmpty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();

        String userId = args.getString(User.USERID);
        User user = new Select().from(User.class).where("id = ?" , userId).executeSingle();
        if (TextUtils.isEmpty(userId)) {
            mReviews = new ArrayList<>();
        } else {
            mReviews = new Select().from(Review.class).where("author = ?" , user.name).execute();
        }

//        mReviews =  new Select().from(Review.class).groupBy("location").where("user = ?" , userId).execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_review_list_fragment, container, false);
        ButterKnife.bind(this, view);

        updateUI();
        reivewListFragmentSwipeListView.setAdapter(new ReviewListAdapter(getActivity(), mReviews));
        reivewListFragmentSwipeListView.setOnItemClickListener((parent, v, position, id) -> {
            Intent intent = new Intent(getActivity(), ReviewActivity.class);
            intent.putExtra(Review.REVIEW, mReviews.get(position).getId());
            startActivity(intent);
        });
        initSwipeListView();

        return view;
    }

    private void updateUI(){
        myReviewListFragmentEmpty.setVisibility(mReviews.size() > 0 ? View.GONE : View.VISIBLE);
    }
    private void initSwipeListView() {

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                deleteItem.setWidth(150);
                deleteItem.setIcon(R.drawable.delete_1);
                menu.addMenuItem(deleteItem);
            }
        };

        reivewListFragmentSwipeListView.setMenuCreator(creator);

        reivewListFragmentSwipeListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
//                        Utils.Tip(getActivity() , "delete review");
                        Utils.TipSnackBar(reivewListFragmentSwipeListView, "delete review", null, "");
                        break;
                }
                return false;
            }
        });

        reivewListFragmentSwipeListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
    }
}
