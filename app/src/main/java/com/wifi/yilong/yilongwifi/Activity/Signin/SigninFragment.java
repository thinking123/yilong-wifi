package com.wifi.yilong.yilongwifi.Activity.Signin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.wifi.yilong.yilongwifi.Activity.Dialog.ProgressDialogFragment;
import com.wifi.yilong.yilongwifi.Activity.Signup.SignupActivity;
import com.wifi.yilong.yilongwifi.Activity.WiFiList.WiFiListActivity;
import com.wifi.yilong.yilongwifi.DB.DatabaseHelper;
import com.wifi.yilong.yilongwifi.DB.UserLoader;
import com.wifi.yilong.yilongwifi.Http.HttpHelper;
import com.wifi.yilong.yilongwifi.Http.ServerURL;
import com.wifi.yilong.yilongwifi.Infrastructure.AppConstant;
import com.wifi.yilong.yilongwifi.Infrastructure.MyLog;
import com.wifi.yilong.yilongwifi.Infrastructure.StoreHelper;
import com.wifi.yilong.yilongwifi.Infrastructure.Utils;
import com.wifi.yilong.yilongwifi.Model.User;
import com.wifi.yilong.yilongwifi.R;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Date;

public class SigninFragment extends Fragment {


	
	private EditText mAccountEditText;
	private EditText mPwEditText;
	private Button mSigninButton;
	private TextView mSignupTextView;
	private TextView mSigninForgotPw;
    private Spinner mAccounts;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
	}

	
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_signin, container,false);
		mAccountEditText = (EditText)v.findViewById(R.id.signin_account);
		mPwEditText  = (EditText)v.findViewById(R.id.signin_password);
		mSigninButton  = (Button)v.findViewById(R.id.signin_login);
		mSignupTextView = (TextView)v.findViewById(R.id.signin_signup);
		mSigninForgotPw = (TextView)v.findViewById(R.id.signin_forgot_password);
//        mAccounts = (Spinner)v.findViewById(R.id.signin_account_spinner);
        //hide spinner
        mAccounts.setVisibility(View.INVISIBLE);

		mAccountEditText.addTextChangedListener(mTextWatcher);
		mPwEditText.addTextChangedListener(mTextWatcher);
		mSigninButton.setOnClickListener(monClickListener);
		mSignupTextView.setOnClickListener(monClickListener);
		mSigninForgotPw.setOnClickListener(monClickListener);
		mSigninButton.setEnabled(false);

		
		return v;
	}

	private boolean Signin(String email , String password) {

		return false;
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode){
			case AppConstant.REQUEST.REQUEST_SIGNUP:
				if(requestCode == Activity.RESULT_OK){
					//can not go to here
//					User user = (User)data.getSerializableExtra(User.USER);
//					ProgressDialogFragment.show(getActivity() , R.string.signin_loading_title , R.string.signin_loading_msg);

				}else{

				}
				break;
			default:
				break;
		}

	}

	private void checkInputFields(){
		if(mAccountEditText.getText().toString().trim().length() == 0 ||
				mPwEditText.getText().toString().trim().length() == 0){
			mSigninButton.setEnabled(false);
		}else{
			mSigninButton.setEnabled(true);
		}
	}
	private TextWatcher mTextWatcher = new TextWatcher(){

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

	private void signin(){
//		Observable<com.wifi.yilong.yilongwifi.Http.rest.model.User> observable =
//				RetrofitService.get(getContext()).sigin(
//						this.getClass().getSimpleName(),
//						mAccountEditText.getText().toString() ,
//						mPwEditText.getText().toString());
//		observable
//
////				.doOnNext(new Action)
//				.subscribeOn(Schedulers.io())
//				.observeOn(AndroidSchedulers.mainThread())
//				.subscribe(new Consumer<com.wifi.yilong.yilongwifi.Http.rest.model.User>() {
//
//					@Override
//					public void accept(com.wifi.yilong.yilongwifi.Http.rest.model.User user) throws Exception {
//						long id = user.save();
//					}
//				});

	}
	private void signinWithRetrofit(){
//
//		RetrofitService.get(getContext()).siginWithRt(this.getClass().getSimpleName(),
//				mAccountEditText.getText().toString(),
//				mPwEditText.getText().toString(), new Callback<com.wifi.yilong.yilongwifi.Http.rest.model.User>() {
//					@Override
//					public void onResponse(Call<com.wifi.yilong.yilongwifi.Http.rest.model.User> call, Response<com.wifi.yilong.yilongwifi.Http.rest.model.User> response) {
//						com.wifi.yilong.yilongwifi.Http.rest.model.User user = response.body();
//						if(user != null){
//							try {
//								Long id = user.save();
//								MyLog.Debug("save successfully , id = " + id);
//							}catch (Exception e){
//								MyLog.Debug(e.getMessage());
//							}
//
//						}
//						MyLog.Debug("onResponse");
//					}
//
//					@Override
//					public void onFailure(Call<com.wifi.yilong.yilongwifi.Http.rest.model.User> call, Throwable t) {
//						MyLog.Debug(t.getMessage());
//					}
//				});
//
//
//	if(true){
//		return;
//	}
//	 Observable<com.wifi.yilong.yilongwifi.Http.rest.model.User> observable =
//			 RetrofitService.get(getContext()).sigin(this.getClass().getSimpleName(),
//				mAccountEditText.getText().toString(),
//				mPwEditText.getText().toString());
//
//		observable
////				.map(new Function<com.wifi.yilong.yilongwifi.Http.rest.model.User, Object>() {
////					@Override
////					public Observable<String> call(List<String> urls) {
////						return Observable.from(urls);
////					}
////				})
//				.doOnNext(new Consumer<com.wifi.yilong.yilongwifi.Http.rest.model.User>() {
//					@Override
//					public void accept(com.wifi.yilong.yilongwifi.Http.rest.model.User user) throws Exception {
//						user.save();
//					}
//				})
//
//				.subscribeOn(Schedulers.io())
//				.observeOn(AndroidSchedulers.mainThread())
//				.subscribe(new Observer<com.wifi.yilong.yilongwifi.Http.rest.model.User>() {
//					@Override
//					public void onSubscribe(Disposable d) {
//						MyLog.Debug("onSubscribe");
//					}
//
//					@Override
//					public void onNext(com.wifi.yilong.yilongwifi.Http.rest.model.User value) {
//						MyLog.Debug("onNext");
//					}
//
//					@Override
//					public void onError(Throwable e) {
//						MyLog.Debug(e.getMessage());
//					}
//
//					@Override
//					public void onComplete() {
//						MyLog.Debug("completed");
//					}
//				});
	}
	private void test(){
		try {
			String payLoader = "{\"_id\":\"58883dff3ded9261a15e55c2\",\"email\":\"testlq@qq.com\",\"name\":\"lqtest\",\"exp\":1487599295,\"iat\":1486994495}";
			Gson gson = new GsonBuilder()
					.excludeFieldsWithoutExposeAnnotation()
					.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
						@Override
						public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
							return new Date(json.getAsJsonPrimitive().getAsLong());
						}
					})
//					.setDateFormat("yyyy-MM-dd'T'HH:mm:ssz")
//                    .serializeNulls()
					.create();


			com.wifi.yilong.yilongwifi.Http.rest.model.User user = gson.fromJson(payLoader , com.wifi.yilong.yilongwifi.Http.rest.model.User.class);

			MyLog.Debug(user.toString());
		}catch (Exception ex){
			MyLog.Debug(ex.getMessage());
		}

	}
	private OnClickListener monClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.signin_login:
//				new SigninAsyncTask().execute();
//				test();
				signin();
//				signinWithRetrofit();
				break;
			case R.id.signin_signup:
				Intent i = new Intent(getActivity() , SignupActivity.class);
				startActivityForResult(i, AppConstant.REQUEST.REQUEST_SIGNUP);
				break;
			case R.id.signin_forgot_password:
				
				break;
			}
			
		}
	};
	private JSONObject formateData() {
		JSONObject js = new JSONObject();
		try {
			js.put("email", mAccountEditText.getText());
			js.put("password", mPwEditText.getText());
		} catch (Exception ex) {
			MyLog.Debug(ex.getMessage());
		}

		return js;
	}
	private void SigninSuccess(User user){
		Intent i = new Intent(getActivity() , WiFiListActivity.class);
		i.putExtra(User.USER , user);
//		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(i);
	}
	private class SigninAsyncTask extends AsyncTask<Void , Void , User>{
		private ProgressDialogFragment pdfg;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pdfg = ProgressDialogFragment.show(getActivity() , R.string.signin_loading_title , R.string.signin_loading_msg);
		}

		@Override
		protected void onPostExecute(User user) {
			if(pdfg != null){
				pdfg.dismiss();
			}

			if(user != null){
				Utils.Tip(getActivity() , R.string.tip_signin_success);
				DatabaseHelper.get(getActivity()).addUser(user);
//				DatabaseHelper db = DatabaseHelper.get(getActivity());

				StoreHelper.putToSharedPreferences(getActivity() , AppConstant.SharedPreferencesKey.ID, user.getId());
				SigninSuccess(user);
			}else{
				Utils.Tip(getActivity() , R.string.tip_signin_failure);
			}

		}

		@Override
		protected User doInBackground(Void... params) {
			return HttpHelper.Signin(ServerURL.Signin , formateData());
		}
	}

	private com.wifi.yilong.yilongwifi.Http.rest.model.User signinHttp(){
		com.wifi.yilong.yilongwifi.Http.rest.model.User user = new com.wifi.yilong.yilongwifi.Http.rest.model.User();
		String email = mAccountEditText.getText().toString();
		String pw = mPwEditText.getText().toString();

		return user;
	}
    private static class UserCusorAdapter extends CursorAdapter{
        private Cursor cursor;

        public UserCusorAdapter(Context c , Cursor _cursor){
            super(c , _cursor , 0);
            cursor = _cursor;
        }
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            return inf.inflate(android.R.layout.simple_list_item_1 , parent , false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            User user;
            if(cursor.isBeforeFirst() || cursor.isAfterLast()){
                user = null;
            }else{
                user = new User(
                        cursor.getString(0) ,
                        cursor.getString(1) ,
                        cursor.getString(2) ,
                        new Date(Integer.parseInt(cursor.getString(3))) ,
                        cursor.getString(4));
            }
            if(user != null){
                TextView tv = (TextView)view;
                tv.setText(user.getEmail());
            }

        }
    }

    private class SpinnerItemSelected implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
	private class UserLoaderCallbacks implements LoaderManager.LoaderCallbacks<Cursor>{
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new UserLoader(getActivity());
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            UserCusorAdapter adapter = new UserCusorAdapter(getActivity() , data);
            mAccounts.setAdapter(adapter);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            mAccounts.setAdapter(null);
        }
    }


}
