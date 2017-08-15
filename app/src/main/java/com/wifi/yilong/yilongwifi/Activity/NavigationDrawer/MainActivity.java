package com.wifi.yilong.yilongwifi.Activity.NavigationDrawer;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.VolleyError;
import com.wifi.yilong.yilongwifi.App.AppController;
import com.wifi.yilong.yilongwifi.Http.ServerURL;
import com.wifi.yilong.yilongwifi.Infrastructure.Utils;
import com.wifi.yilong.yilongwifi.R;
import com.wifi.yilong.yilongwifi.VolleyHttp.JsonArrayHttp;
import com.wifi.yilong.yilongwifi.VolleyHttp.VolleyCallback;

import org.json.JSONArray;

/**
 * Created by Administrator on 2017/2/6.
 */

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private FloatingActionButton mFab;
    private CoordinatorLayout mCoordinatorLayout;
    private NavigationView mNavigationView;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

//        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.navigation_drawer_app_bar_swiperefreshlayout);
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                mSwipeRefreshLayout.setRefreshing(false);
//            }
//        });
        mCoordinatorLayout= (CoordinatorLayout)findViewById(R.id.navigation_drawer_app_bar_coordinatorlayout);
        mNavigationView = (NavigationView)findViewById(R.id.navigation_drawer_navigationview);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                return false;
//                if(item.isChecked()){
//                    item.setChecked(false);
//                }
                item.setChecked(!item.isChecked());
                return true;
            }
        });
        mCollapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.navigation_drawer_app_bar_collapsingtoolbarlayout);

//        mRecyclerView = (RecyclerView)findViewById(R.id.navigation_drawer_app_bar_recyclerview);
//        String[] datas = new String[100];
//        for(int i = 0 ; i < 100 ; i++){
//            datas[i] = "test : " + i;
//        }
//        mRecyclerView.setHasFixedSize(true);
//        mLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        RecyclerViewAdaper adaper = new RecyclerViewAdaper(datas);
//        mRecyclerView.setAdapter(adaper);
//        adaper.notifyDataSetChanged();
        mToolbar = (Toolbar)findViewById(R.id.navigation_drawer_app_bar_toolbar);
        mToolbar.setNavigationIcon(R.drawable.menu);
        mDrawerLayout= (DrawerLayout)findViewById(R.id.navigation_drawer_drawerlayout);
        mFab = (FloatingActionButton)findViewById(R.id.navigation_drawer_app_bar_fab);
        mFab.setImageResource(R.drawable.add);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(mCoordinatorLayout ,
                        R.string.custom_review ,
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.dialog_ok, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Utils.Tip(getApplicationContext() , R.string.tip_add_review_success);
                            }
                        })
                        .show();
            }
        });
        mDrawerToggle = new ActionBarDrawerToggle(this ,mDrawerLayout ,mToolbar ,R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
                ){
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle("open it");
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("close it");
            }

        };
        setSupportActionBar(mToolbar);

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);



    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
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

    }    @Override
    protected void onStart() {
        super.onStart();

        JsonArrayHttp http = new JsonArrayHttp(formatUrl(), new VolleyCallback<JSONArray>() {
            @Override
            public void Success(JSONArray res) {
                if(res != null){
                    Utils.Tip(getApplicationContext() ,"length : " +  res.length());
                }
            }

            @Override
            public void Error(VolleyError error) {
                Utils.Tip(getApplicationContext() , "error : " + error.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(http , this.getClass().getSimpleName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.wifi_list_menu , menu);
        return true;
    }
}
