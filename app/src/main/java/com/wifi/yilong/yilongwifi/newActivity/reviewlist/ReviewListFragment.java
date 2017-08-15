package com.wifi.yilong.yilongwifi.newActivity.reviewlist;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.wifi.yilong.yilongwifi.Http.rest.model.Location;
import com.wifi.yilong.yilongwifi.Http.rest.model.Review;
import com.wifi.yilong.yilongwifi.Infrastructure.Utils;
import com.wifi.yilong.yilongwifi.R;
import com.wifi.yilong.yilongwifi.newActivity.review.ReviewActivity;
import com.wifi.yilong.yilongwifi.newActivity.reviewlist.Adapter.ReviewListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/8.
 */

public class ReviewListFragment extends Fragment {
    @BindView(R.id.tool_bar_center_title)
    TextView toolBarCenterTitle;
    @BindView(R.id.reivew_list_fragment_swipe_listView)
    SwipeMenuListView reivewListFragmentSwipeListView;
    @BindView(R.id.review_list_fragment_fab)
    FloatingActionButton reviewListFragmentFab;
    @BindView(R.id.review_list_fragment_toolbar)
    Toolbar toolbar;
    Location mLocation;
//    Dialog dialog;
//    List<Review> reviews;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null && args.containsKey(Location.LOCATION)){
            String id = args.get(Location.LOCATION).toString();
            mLocation = new Select().from(Location.class).where("id = ?" , id).executeSingle();
            if(mLocation == null){
                throw new Resources.NotFoundException("not find location");
            }

//            reviews = mLocation.getReviews();
        }else {
            throw new Resources.NotFoundException("not find location id");
        }

//        dialog = Utils.getCustomProgressDialog(getActivity() , "" , null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.review_list_fragment, container, false);
        ButterKnife.bind(this, view);

        reivewListFragmentSwipeListView.setAdapter(new ReviewListAdapter(getActivity() , mLocation.getReviews()));
        reivewListFragmentSwipeListView.setOnItemClickListener(( parent,  v,  position,  id)->{
            Intent intent = new Intent(getActivity() , ReviewActivity.class);
            intent.putExtra(Review.REVIEW , mLocation.getReviews().get(position).getId());
            startActivity(intent);
        });
        initSwipeListView();

        toolBarCenterTitle.setText(R.string.review_list_tool_bar_title);
        toolbar.setNavigationOnClickListener(v->{
            getActivity().finish();
        });
        toolbar.setNavigationIcon(R.drawable.left);
        return view;
    }

    @OnClick(R.id.review_list_fragment_fab)
    public void onClick() {
        Intent intent = new Intent(getActivity() , ReviewActivity.class);
        intent.putExtra(Location.LOCATION , mLocation.getId());
                startActivity(intent);
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
                switch (index){
                    case 0:
//                        Utils.Tip(getActivity() , "delete review");
                        Utils.TipSnackBar(reivewListFragmentSwipeListView , "delete review" , null , "");
                        break;
                }
                return false;
            }
        });

        reivewListFragmentSwipeListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
    }
}
