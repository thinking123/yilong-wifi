package com.wifi.yilong.yilongwifi.newActivity.signin;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wifi.yilong.yilongwifi.App.AppController;
import com.wifi.yilong.yilongwifi.Http.rest.model.User;
import com.wifi.yilong.yilongwifi.Http.rest.retrofitService.RetrofitService;
import com.wifi.yilong.yilongwifi.Infrastructure.MyLog;
import com.wifi.yilong.yilongwifi.Infrastructure.Utils;
import com.wifi.yilong.yilongwifi.R;
import com.wifi.yilong.yilongwifi.newActivity.signin.Adapter.UserAutoCompleteAdapter;
import com.wifi.yilong.yilongwifi.newActivity.signup.SignupActivity;
import com.wifi.yilong.yilongwifi.newActivity.wifiList.WiFiListActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/2/15.
 */

public class SigninFragment extends Fragment {

    @BindView(R.id.header)
    LinearLayout header;
    @BindView(R.id.footer)
    LinearLayout footer;
    @BindView(R.id.signin_account_autocompletetextview)
    AutoCompleteTextView signinAccount;
    @BindView(R.id.signin_password)
    EditText signinPassword;
    @BindView(R.id.signin_remember_password_checkBox)
    CheckBox signinRememberPasswordCheckBox;
    @BindView(R.id.signin_login)
    Button signinLogin;
    @BindView(R.id.signin_signup)
    TextView signinSignup;
    @BindView(R.id.signin_forgot_password)
    TextView signinForgotPassword;

    @Inject
    RetrofitService mRetrofitService;
    @Inject
    SharedPreferences mSharedPreferences;

    Dialog mProgressDialog;
    private Unbinder unbinder;

    private Observable<User> userObservable;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inject dependency
        ((AppController) getActivity().getApplication()).getCommonComponent().inject(this);

        mProgressDialog = Utils.getCustomProgressDialog(getActivity() , "正在登入..." , null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //unbinding view
        unbinder.unbind();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_signin_new, container, false);
        //binding view
        unbinder = ButterKnife.bind(this, v);
        //hide keyboard
//        View focusView = this.getActivity().getCurrentFocus();
//        if(focusView != null){
//            InputMethodManager inputMethodManager =
//                    (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//            inputMethodManager.hideSoftInputFromWindow(
//                    focusView.getWindowToken() ,
//                    InputMethodManager.HIDE_IMPLICIT_ONLY);
//        }
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        signinAccount.setThreshold(1);
        signinAccount.setAdapter(new UserAutoCompleteAdapter(getActivity()));
        signinAccount.setOnItemClickListener((ad , view , p , id)->{
            User user = (User) ad.getItemAtPosition(p);
            signinAccount.setText(user.email);
            if(user.password != null && user.password.length() > 0){
                signinPassword.setText(user.password);
            }else {
                signinPassword.setText("");
            }
            Utils.Tip(getActivity() , user.name);
        });
        return v;
    }

    @OnClick({R.id.signin_login, R.id.signin_signup, R.id.signin_remember_password_checkBox, R.id.signin_forgot_password})
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.signin_login:
                MyLog.Debug("signin_login");
                signin();
                break;
            case R.id.signin_signup:
                MyLog.Debug("signin_signup");
                Intent intent = new Intent(getActivity() , SignupActivity.class);
                startActivity(intent);
                break;
            case R.id.signin_remember_password_checkBox:
                MyLog.Debug("signin_remember_password_checkBox");
                break;
            case R.id.signin_forgot_password:
                MyLog.Debug("signin_forgot_password");
                break;
            default:
                break;
        }
    }

    @OnTextChanged(value = {R.id.signin_account_autocompletetextview, R.id.signin_password},
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterTextChanged(Editable editable) {
        signinLogin.setEnabled(signinAccount.getText().length() > 0 &&
                signinPassword.getText().length() > 0);
    }

    public void signin() {
        signinLogin.setEnabled(false);
        signinAccount.setEnabled(false);
        signinPassword.setEnabled(false);
        signinSignup.setEnabled(false);
        signinRememberPasswordCheckBox.setEnabled(false);
        signinForgotPassword.setEnabled(false);
        mProgressDialog.show();
        mRetrofitService.sigin(this.getClass().getSimpleName(),
                signinAccount.getText().toString(),
                signinPassword.getText().toString())
                .doOnNext(user -> {
                    if (user != null) {
                        if (signinRememberPasswordCheckBox.isChecked()) {
                            user.password = signinPassword.getText().toString();
                        }

                        long id = user.save();
//                        if(id >= 0){
//                            mSharedPreferences.edit().putString(User.USERID , user.id).commit();
//                        }
                        MyLog.Debug("id = " + id);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    mSharedPreferences.edit().putString(User.USERID , user.id).apply();
                } , throwable -> {
                    Utils.Tip(getActivity(), throwable.getMessage());
                    signinLogin.setEnabled(true);
                    signinAccount.setEnabled(true);
                    signinPassword.setEnabled(true);
                    signinSignup.setEnabled(true);
                    signinRememberPasswordCheckBox.setEnabled(true);
                    signinForgotPassword.setEnabled(true);
                    mProgressDialog.dismiss();
                } , ()->{
                    mProgressDialog.dismiss();
                    Intent intent = new Intent(getActivity() , WiFiListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                });
//                .subscribe(new Observer<User>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(User value) {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Utils.Tip(getActivity(), e.getMessage());
//                        signinLogin.setEnabled(false);
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }
}
