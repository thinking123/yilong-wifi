package com.wifi.yilong.yilongwifi.Activity.Signup;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wifi.yilong.yilongwifi.Activity.Dialog.ProgressDialogFragment;
import com.wifi.yilong.yilongwifi.Activity.WiFiList.WiFiListActivity;
import com.wifi.yilong.yilongwifi.DB.DatabaseHelper;
import com.wifi.yilong.yilongwifi.Http.HttpHelper;
import com.wifi.yilong.yilongwifi.Http.ServerURL;
import com.wifi.yilong.yilongwifi.Infrastructure.AppConstant;
import com.wifi.yilong.yilongwifi.Infrastructure.MyLog;
import com.wifi.yilong.yilongwifi.Infrastructure.StoreHelper;
import com.wifi.yilong.yilongwifi.Infrastructure.Utils;
import com.wifi.yilong.yilongwifi.Model.User;
import com.wifi.yilong.yilongwifi.R;

import org.json.JSONObject;

public class SignupFragment extends Fragment {


    private EditText mUserNameEditText;
    private EditText mAccountEditText;
    private EditText mPasswordEditText;
    private Button mRegistrationButton;
    private TextView mSigninTextView;
    private ProgressBar mProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_signup, container, false);

        mUserNameEditText = (EditText) v.findViewById(R.id.signup_username);
        mAccountEditText = (EditText) v.findViewById(R.id.signup_account);
        mPasswordEditText = (EditText) v.findViewById(R.id.signup_password);
        mRegistrationButton = (Button) v.findViewById(R.id.signup_reg_button);
        mSigninTextView = (TextView) v.findViewById(R.id.signup_signin_link);
//        mProgressBar = (ProgressBar) v.findViewById(R.id.signup_Pg);

        mUserNameEditText.addTextChangedListener(mTextWatcher);
        mAccountEditText.addTextChangedListener(mTextWatcher);
        mPasswordEditText.addTextChangedListener(mTextWatcher);

        mRegistrationButton.setOnClickListener(monClickListener);
        mSigninTextView.setOnClickListener(monClickListener);

        mRegistrationButton.setEnabled(false);
        mProgressBar.setVisibility(View.INVISIBLE);

        return v;
    }

    private void checkInputFields() {
        if (mUserNameEditText.getText().toString().trim().length() == 0 ||
                mAccountEditText.getText().toString().trim().length() == 0 ||
                mPasswordEditText.getText().toString().trim().length() == 0) {
            mRegistrationButton.setEnabled(false);
        } else {
            mRegistrationButton.setEnabled(true);
        }
    }

    private TextWatcher mTextWatcher = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTextChanged(CharSequence s, int arg1, int arg2,
                                  int arg3) {
            checkInputFields();
        }

    };

    private OnClickListener monClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.signup_reg_button:
//                    thread.execute("");
                    //A AsyncTask instance can only exec once
                    new SignUpThread().execute("");
                    break;
                case R.id.signup_signin_link:
                    getActivity().finish();
                    break;

            }

        }
    };

    private JSONObject formateData() {
        JSONObject js = new JSONObject();
        try {
            js.put("name", mUserNameEditText.getText());
            js.put("email", mAccountEditText.getText());
            js.put("password", mPasswordEditText.getText());
        } catch (Exception ex) {
            MyLog.Debug(ex.getMessage());
        }

        return js;
    }

    private void parseStringToJson(String str) {
        try {
            JSONObject js = new JSONObject(str);
            String token = js.getString("token");
            Toast.makeText(getActivity(), token, Toast.LENGTH_LONG);
        } catch (Exception ex) {
            MyLog.Debug(ex.getMessage());
        }


    }

    private void SignupSuccess(User user){
        Intent i = new Intent(getActivity() , WiFiListActivity.class);
        i.putExtra(User.USER , user);
//        getActivity().setResult(Activity.RESULT_OK , i);
//        getActivity().finish();
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

//    private SignUpThread thread = new SignUpThread();

    private class SignUpThread extends AsyncTask<String, Integer, User> {
        ProgressDialogFragment pdfg;
        @Override
        protected void onPreExecute() {
            pdfg = ProgressDialogFragment.show(getActivity() , R.string.signup_loading_title,
                    R.string.signup_loading_msg);
        }


        @Override
        protected User doInBackground(String... params) {
            return HttpHelper.SignUp(ServerURL.SignUp, formateData());
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(User user) {
            if(pdfg != null){
                pdfg.dismiss();
            }

            try {


                if (user != null) {
                    Utils.Tip(getActivity(), R.string.tip_signup_success);
                    DatabaseHelper.get(getActivity()).addUser(user);
                    StoreHelper.putToSharedPreferences(getActivity(), AppConstant.SharedPreferencesKey.ID, user.getId());
                    SignupSuccess(user);
                } else {
                    Utils.Tip(getActivity(), R.string.tip_signup_failure);
                }
            }catch (Exception e){
                MyLog.Debug(e.getMessage());
            }


//			super.onPostExecute(s);
//            if (s.isEmpty()) {
//                s = "this is none string";
//            }else{
//                parseStringToJson(s);
//            }
//            Toast.makeText(getActivity(), s, Toast.LENGTH_LONG);
        }

    }

}
