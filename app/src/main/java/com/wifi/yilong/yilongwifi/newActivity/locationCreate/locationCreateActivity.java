package com.wifi.yilong.yilongwifi.newActivity.locationCreate;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.anton46.stepsview.StepsView;
import com.wifi.yilong.yilongwifi.R;
import com.wifi.yilong.yilongwifi.newActivity.locationCreate.fragment.step1Fragment;
import com.wifi.yilong.yilongwifi.newActivity.locationCreate.fragment.step2Fragment;
import com.wifi.yilong.yilongwifi.newActivity.locationCreate.fragment.step3Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/5.
 */

public class locationCreateActivity extends AppCompatActivity {
    @BindView(R.id.tool_bar_center_title)
    TextView toolBarCenterTitle;
    @BindView(R.id.location_create_stepsview)
    StepsView locationCreateStepsview;
    @BindView(R.id.location_create_container)
    FrameLayout locationCreateContainer;
    @BindView(R.id.location_create_previous_step)
    Button locationCreatePreviousStep;
    @BindView(R.id.location_create_next_step)
    Button locationCreateNextStep;

    @BindView(R.id.location_create_toolbar)
    Toolbar locationCreateToolbar;

    int mFragmentIndex = 0;
    int mFragmentMaxIndex = 2;
    String[] steps;
    public static final int STARTREQUEST = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_create_activity);
        ButterKnife.bind(this);

        InitActivity();
    }


    @OnClick({R.id.location_create_previous_step, R.id.location_create_next_step})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.location_create_previous_step:
                mFragmentIndex--;
                updateUI();
                break;
            case R.id.location_create_next_step:
                if(mFragmentIndex >= mFragmentMaxIndex){
                    //completed
                    finish();
                }else{
                    mFragmentIndex++;
                    updateUI();
                }

                break;
        }
    }

    private void addLocation(){

    }
    private void InitActivity(){
        toolBarCenterTitle.setText(R.string.location_create_activity_toolbar_title);
        locationCreateToolbar.setNavigationIcon(R.drawable.left);
        locationCreateToolbar.setNavigationOnClickListener(v -> {
            finish();
        });
        steps = getResources().getStringArray(R.array.location_create_activity_steps_info);
        updateUI();
    }

    private void setCurrentFragment(){
        FragmentManager fm = getFragmentManager();
        if(fm.findFragmentById(R.id.location_create_container) == null){
            fm
                    .beginTransaction()
                    .add(R.id.location_create_container , getCurrentFragment())
                    .commit();
        }else{
            fm
                    .beginTransaction()
                    .replace(R.id.location_create_container , getCurrentFragment())
                    .commit();
        }

    }

    private void updateUI(){
        locationCreatePreviousStep.setVisibility(mFragmentIndex > 0 ? View.VISIBLE : View.INVISIBLE);
//        locationCreatePreviousStep.setEnabled(mFragmentIndex > 0 && mFragmentIndex < mFragmentMaxIndex);

        locationCreateNextStep.setText(
                mFragmentIndex == mFragmentMaxIndex ?
                        R.string.location_create_activity_complete_step :
                        R.string.location_create_activity_next_step);
        setStepsViewStatus();
        setCurrentFragment();
    }

    private void setStepsViewStatus(){
        locationCreateStepsview
                .setLabels(steps)
                .setBarColorIndicator(getResources().getColor(R.color.primary)) //R.color.cardview_dark_background)
                .setProgressColorIndicator(getResources().getColor(R.color.colorAccent))
                .setLabelColorIndicator(getResources().getColor(R.color.orange))
                .setCompletedPosition(mFragmentIndex)
                .drawView();
    }
    private Fragment getCurrentFragment(){
        switch (mFragmentIndex){
            case 0:
                return new step1Fragment();
            case 1:
                return new step2Fragment();
            case 2:
                return new step3Fragment();
            default:
                return new step1Fragment();
        }
    }
}
