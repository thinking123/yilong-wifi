package com.wifi.yilong.yilongwifi.Activity.Signin;

import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import com.wifi.yilong.yilongwifi.Activity.FragmentManagement;


public class SigninActivity extends FragmentManagement {

    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.signin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		return new SigninFragment();
	}
}
