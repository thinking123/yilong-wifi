package com.wifi.yilong.yilongwifi.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.wifi.yilong.yilongwifi.R;

public abstract class FragmentManagement extends FragmentActivity {

	protected abstract Fragment createFragment();

	/**
	 * get layout id , can be override
	 * @return
	 */
	protected int getLayoutResId(){
		return R.layout.activity_fragment;
	}
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(getLayoutResId());
		FragmentManager fm = getSupportFragmentManager();
		
		if(fm.findFragmentById(R.id.fragmentContainer) == null){
			fm.beginTransaction()
			.add(R.id.fragmentContainer, createFragment())
			.commit();
		}
		
	}
	
	
}
