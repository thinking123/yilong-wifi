package com.wifi.yilong.yilongwifi.newActivity.signup;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wifi.yilong.yilongwifi.App.AppController;
import com.wifi.yilong.yilongwifi.Http.rest.retrofitService.RetrofitService;
import com.wifi.yilong.yilongwifi.Infrastructure.MyLog;
import com.wifi.yilong.yilongwifi.Infrastructure.Utils;
import com.wifi.yilong.yilongwifi.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/2/18.
 */

public class SignupFragment extends Fragment {

    @BindView(R.id.signup_username)
    EditText signupUsername;
    @BindView(R.id.signup_account)
    EditText signupAccount;
    @BindView(R.id.signup_password)
    EditText signupPassword;
    @BindView(R.id.signup_reg_button)
    Button signupRegButton;
    @BindView(R.id.signup_signin_link)
    TextView signupSigninLink;


    @Inject
    RetrofitService mRetrofitService;
    @BindView(R.id.signup_tool_bar)
    Toolbar signupToolBar;
    @BindView(R.id.tool_bar_center_title)
    TextView toolBarCenterTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide keyboard
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        //Inject dependency
        ((AppController) getActivity().getApplication()).getCommonComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_signup, container, false);
        //binding view
        ButterKnife.bind(this, v);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(signupToolBar);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ((AppCompatActivity) getActivity()).setTitle(R.string.signin_login_button);
//        signupToolBar.setTitle(R.string.signin_login_button);
        signupToolBar.setNavigationIcon(R.drawable.left);
        toolBarCenterTitle.setText(R.string.signup_registration_button);
        signupToolBar.setNavigationOnClickListener(view -> {
            getActivity().finish();
        });
        return v;
    }

    private void signup(String email, String pw, String name) {
        mRetrofitService.signup(email, pw, name)
                .doOnNext(user -> {
                    if (user != null) {
                        user.password = pw;
                        long id = user.save();
                        MyLog.Debug("id : " + id);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {

                }, throwable -> {
                    //error
                    Utils.Tip(getActivity(), throwable.getMessage());
                    signupRegButton.setEnabled(true);
                }, () -> {
                    //completed
                    Utils.Tip(getActivity(), R.string.tip_signup_success);
                    getActivity().finish();
                });
    }

    @OnTextChanged(value = {R.id.signup_account, R.id.signup_password, R.id.signup_username},
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterTextChanged(Editable editable) {
        signupRegButton.setEnabled(
                signupUsername.getText().length() > 0 &&
                        signupAccount.getText().length() > 0 &&
                        signupPassword.getText().length() > 0
        );
    }

    @OnClick({R.id.signup_reg_button, R.id.signup_signin_link})
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.signup_reg_button:
                MyLog.Debug("signin_login");
                v.setEnabled(false);
                signup(signupAccount.getText().toString(), signupPassword.getText().toString(), signupUsername.getText().toString());
                break;
            case R.id.signup_signin_link:
                MyLog.Debug("signin_signup");
//                getActivity().onBackPressed();
                getActivity().finish();
                break;
            default:
                break;
        }
    }

}
