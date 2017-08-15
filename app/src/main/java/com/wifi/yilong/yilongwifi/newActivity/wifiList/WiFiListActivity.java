package com.wifi.yilong.yilongwifi.newActivity.wifiList;

import android.app.Dialog;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.wifi.yilong.yilongwifi.App.AppController;
import com.wifi.yilong.yilongwifi.Http.rest.model.User;
import com.wifi.yilong.yilongwifi.Infrastructure.Utils;
import com.wifi.yilong.yilongwifi.R;
import com.wifi.yilong.yilongwifi.newActivity.locationCreate.locationCreateActivity;
import com.wifi.yilong.yilongwifi.newActivity.signin.SigninActivity;
import com.wifi.yilong.yilongwifi.newActivity.wifiList.Adapter.SpinnerUserAccountAdapter;
import com.wifi.yilong.yilongwifi.newActivity.wifiList.fragment.AboutFragment;
import com.wifi.yilong.yilongwifi.newActivity.wifiList.fragment.MyReivewsFragment;
import com.wifi.yilong.yilongwifi.newActivity.wifiList.fragment.SettingFragment;
import com.wifi.yilong.yilongwifi.newActivity.wifiList.fragment.WiFiListFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/2/18.
 */

public class WiFiListActivity extends AppCompatActivity {
    @BindView(R.id.navigation_drawer_app_bar_toolbar)
    Toolbar navigationDrawerAppBarToolbar;
    @BindView(R.id.navigation_drawer_app_bar_collapsingtoolbarlayout)
    CollapsingToolbarLayout navigationDrawerAppBarCollapsingtoolbarlayout;
    @BindView(R.id.navigation_drawer_app_bar_fab)
    FloatingActionButton navigationDrawerAppBarFab;
    @BindView(R.id.navigation_drawer_app_bar_coordinatorlayout)
    CoordinatorLayout navigationDrawerAppBarCoordinatorlayout;
    @BindView(R.id.navigation_drawer_navigationview)
    NavigationView navigationDrawerNavigationview;
    @BindView(R.id.navigation_drawer_drawerlayout)
    DrawerLayout navigationDrawerDrawerlayout;
    @BindView(R.id.tool_bar_center_title)
    TextView mToolBarCenterTitle;

    @BindView(R.id.navigation_drawer_header_spinner_user_account)
    @Nullable
    Spinner mAccountSpinner;
    @BindView(R.id.navigation_drawer_header_user_name)
    @Nullable
    TextView mUserNameTextView;

    private ActionBarDrawerToggle mDrawerToggle;
    private Unbinder unbinder;

    @Inject
    SharedPreferences mSharedPreferences;
    @Inject
    AppController appController;
    private User mCurrentUser;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.wifi_list_new_menu , menu);

        MenuItem searchItem = menu.findItem(R.id.app_bar_search);

        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
//            searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));
            searchView.setSubmitButtonEnabled(true);
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
        }


        if(CURRENT_TAG == TAG_HOME || CURRENT_TAG == TAG_MY_REVIEWS){
            searchItem.setVisible(true);
        }else{
            searchItem.setVisible(false);
        }

        return true;
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        if(CURRENT_TAG == TAG_HOME || CURRENT_TAG == TAG_MY_REVIEWS){
//            menu.findItem(R.id.app_bar_search).setVisible(false);
//        }else{
//            menu.findItem(R.id.app_bar_search).setVisible(true);
//        }
//        return super.onPrepareOptionsMenu(menu);
//    }

    private void logout(){
        //show logout dialog
        Dialog dialog = Utils.getAlertDialog(this , 0 , 0 , R.drawable.logout ,
                0 , (d , w)->{
                    Intent intent = new Intent(this , SigninActivity.class);
                    startActivity(intent);
                    finish();
                } ,
                true ,
                0 , null);
        dialog.show();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_logout:

                logout();
                return true;

            case R.id.app_bar_search:

                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
//        setContentView(R.layout.navigation_drawer_app_bar);

        //Inject dependency
        ((AppController) this.getApplication()).getCommonComponent().inject(this);
        loadUserInfo();
        //binding view
        unbinder = ButterKnife.bind(this);

//        this.startActionMode(new ActionBarCallback());
        View headerLayout = navigationDrawerNavigationview.getHeaderView(0);
        setSupportActionBar(navigationDrawerAppBarToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(
                this ,
                navigationDrawerDrawerlayout ,
                navigationDrawerAppBarToolbar ,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        ){
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

        };

        navigationDrawerDrawerlayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        navigationDrawerNavigationview.getMenu();


        mToolBarCenterTitle.setText(R.string.status_title_wifi_list);

        if(mAccountSpinner == null){
            mAccountSpinner = (Spinner)headerLayout.findViewById(R.id.navigation_drawer_header_spinner_user_account);
            mUserNameTextView = (TextView) headerLayout.findViewById(R.id.navigation_drawer_header_user_name);
        }
        mAccountSpinner.setAdapter(new SpinnerUserAccountAdapter(this , mCurrentUser.id , null));
        mAccountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        mAccountSpinner.setSelection(0);

        mUserNameTextView.setText(mCurrentUser.name);
        navigationDrawerAppBarFab.setImageResource(R.drawable.add);

        setUpNavigationView();
        CURRENT_TAG = TAG_HOME;
        loadFragment();
    }
    private static final String TAG_HOME = "home";
    private static final String TAG_MY_REVIEWS = "my_reviews";
    private static final String TAG_NOTIFICATION = "notification";
    private static final String TAG_SETTING = "setting";
    private static final String TAG_ABOUT = "about";
    public  String CURRENT_TAG = TAG_HOME;
    private int navCurrentSelectedItemIndex = 0;

    private void setUpNavigationView(){
        navigationDrawerNavigationview.setNavigationItemSelectedListener(menuItem->{
            switch (menuItem.getItemId()){
                case R.id.navigation_drawer_home:
                    CURRENT_TAG = TAG_HOME;
                    navCurrentSelectedItemIndex = 0;
                    break;
                case R.id.navigation_drawer_my_reviews:
                    CURRENT_TAG = TAG_MY_REVIEWS;
                    navCurrentSelectedItemIndex = 1;
                    break;
                case R.id.navigation_drawer_notification:
                    CURRENT_TAG = TAG_NOTIFICATION;
//                    navCurrentSelectedItemIndex = 2;
                    updateNotificationStatus();
                    return true;
                case R.id.navigation_drawer_setting:
                    CURRENT_TAG = TAG_SETTING;
                    navCurrentSelectedItemIndex = 3;
                    break;
                case R.id.navigation_drawer_about:
                    CURRENT_TAG = TAG_ABOUT;
                    navCurrentSelectedItemIndex = 4;
                    break;
                default:
                    navCurrentSelectedItemIndex = 0;
                    break;
            }

            loadFragment();

            return true;
        });
    }
    Handler mHandler = new Handler();
    private void loadFragment(){
        selectNavMenu();
        setToolbarTitle();

        if(getFragmentManager().findFragmentByTag(CURRENT_TAG) != null){
            navigationDrawerDrawerlayout.closeDrawers();
            updateUI();
            return;
        }

        Runnable mReplaceFragmentRunnable = new Runnable() {

            @Override
            public void run() {
                Fragment fragment = getFragment();
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(android.R.animator.fade_in ,
                                android.R.animator.fade_out)
                        .replace(R.id.activity_wifi_list_container ,
                                fragment , CURRENT_TAG)
                        .commitAllowingStateLoss();
            }
        };

        if(mReplaceFragmentRunnable != null){
            mHandler.post(mReplaceFragmentRunnable);
        }
        navigationDrawerDrawerlayout.closeDrawers();
        updateUI();
        invalidateOptionsMenu();
    }

    private void selectNavMenu(){
        navigationDrawerNavigationview.getMenu().getItem(navCurrentSelectedItemIndex).setChecked(true);
    }

    private void setToolbarTitle(){
        switch (CURRENT_TAG){
            case TAG_HOME:
                mToolBarCenterTitle.setText(R.string.navigation_drawer_menu_home);
                navigationDrawerAppBarCollapsingtoolbarlayout.setTitle(
                        getResources().getString(R.string.navigation_drawer_menu_home));
                break;
            case TAG_MY_REVIEWS:
                mToolBarCenterTitle.setText(R.string.navigation_drawer_menu_my_reviews);
                break;
            case TAG_SETTING:
                mToolBarCenterTitle.setText(R.string.navigation_drawer_menu_setting);
                break;
            case TAG_ABOUT:
                mToolBarCenterTitle.setText(R.string.navigation_drawer_menu_about);
                break;
            default:
                mToolBarCenterTitle.setText(R.string.navigation_drawer_menu_home);
                break;
        }
    }
    private void updateUI(){
//        toggleFab();
        navigationDrawerAppBarFab.setVisibility(CURRENT_TAG == TAG_HOME ? View.VISIBLE : View.INVISIBLE);
        navigationDrawerAppBarCollapsingtoolbarlayout.setVisibility(CURRENT_TAG == TAG_HOME ? View.VISIBLE : View.GONE);
    }

    private void updateNotificationStatus(){
        MenuItem menuItem = navigationDrawerNavigationview.getMenu().getItem(2);
        boolean isOpen = appController.isNotificationOpen();

        if(isOpen){
//            menuItem.setIcon(R.drawable.notification);
//            Utils.TipSnackBar(navigationDrawerAppBarCoordinatorlayout , R.string.wifi_list_notification_closed_msg , null , R.string.wifi_list_notification_open_msg);
            menuItem.getActionView().setVisibility(View.INVISIBLE);
        }else{
//            menuItem.setIcon(R.drawable.notification_checked);
//            Utils.TipSnackBar(navigationDrawerAppBarCoordinatorlayout , R.string.wifi_list_notification_open_msg , null , R.string.wifi_list_notification_open_msg);
            menuItem.setActionView(R.layout.notification_open_dot);
        }

        appController.setNotificationOpen(!isOpen);
        menuItem.setChecked(false);
        menuItem.setCheckable(false);
    }

    private Fragment getFragment(){
        Fragment fragment;
        Bundle args = new Bundle();
        switch (CURRENT_TAG){
            case TAG_HOME:
                return new WiFiListFragment();
            case TAG_MY_REVIEWS:
                fragment = new MyReivewsFragment();
                args.putString(User.USERID , mCurrentUser.getId().toString());
                fragment.setArguments(args);
                return fragment;
            case TAG_SETTING:
                return new SettingFragment();
            case TAG_ABOUT:
                return new AboutFragment();
            default:
                return new WiFiListFragment();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onBackPressed() {
        if(navigationDrawerDrawerlayout.isDrawerOpen(Gravity.LEFT)){
            navigationDrawerDrawerlayout.closeDrawer(Gravity.LEFT);
            return;
        }else{
            logout();
            return;
        }
//        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case locationCreateActivity.STARTREQUEST:
                //locationCreateActivity result
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.navigation_drawer_app_bar_fab})
    void onClick(View v){
        Intent intent = new Intent(this , locationCreateActivity.class);
        startActivityForResult(intent , locationCreateActivity.STARTREQUEST);
    }

    private void loadUserInfo() {
        String id = mSharedPreferences.getString(User.USERID, null);
        if (id != null) {
            mCurrentUser = new Select().from(User.class).where("userId = ?", id).executeSingle();

        } else {
            new NullPointerException("can not find current user");
        }
    }
}
