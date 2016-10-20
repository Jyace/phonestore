package com.phoneshop.activity;


import java.io.File;

import com.phoneshop.app.MyApplication;
import com.phoneshop.fragment.AccountCenterlFragment;
import com.phoneshop.fragment.CategoryFragment;
import com.phoneshop.fragment.HomeFragment;
import com.phoneshop.fragment.PersonalFragement;
import com.phoneshop.fragment.SerachFragment;
import com.phoneshop.view.Viwepager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.Toast;

public class HomeActivity extends FragmentActivity implements OnClickListener{
	private RadioButton mainButton,categoryButton,searchButton,personalButton;
	private static boolean isExit=false;
	private HomeActivityReceiver receiver;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		init();
		regiserReceiver();
		Intent intent=getIntent();
		boolean needLogin=intent.getBooleanExtra("needLogin", false);
		if (needLogin) {
			getSupportFragmentManager().beginTransaction().replace(R.id.tabContent, new PersonalFragement()).commit();
			personalButton.setChecked(true);
		}
		mainButton.setOnClickListener(this);
		searchButton.setOnClickListener(this);
		categoryButton.setOnClickListener(this);
		personalButton.setOnClickListener(this);
	}
	private void regiserReceiver() {
		receiver=new HomeActivityReceiver();
		IntentFilter filter=new IntentFilter();
		filter.addAction(MyApplication.FINISH_HOMEACTIVITY);
		registerReceiver(receiver, filter);
	}
	//初始化总按钮
	private void init() {
		mainButton=(RadioButton) findViewById(R.id.home_main);
		categoryButton=(RadioButton) findViewById(R.id.home_category);
		searchButton=(RadioButton) findViewById(R.id.home_search);
		personalButton=(RadioButton) findViewById(R.id.home_personal);
	}
	//转换fragment
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.home_main:
			getSupportFragmentManager().beginTransaction().replace(R.id.tabContent, new HomeFragment()).commit();
			break;
		case R.id.home_category:
			getSupportFragmentManager().beginTransaction().replace(R.id.tabContent, new CategoryFragment()).commit();
			break;
		case R.id.home_search:
			getSupportFragmentManager().beginTransaction().replace(R.id.tabContent,new SerachFragment()).commit();
			break;
		case R.id.home_personal:
			if (MyApplication.ISLOGIN) {
				getSupportFragmentManager().beginTransaction().replace(R.id.tabContent, new AccountCenterlFragment()).commit();
			}else{
				getSupportFragmentManager().beginTransaction().replace(R.id.tabContent, new PersonalFragement()).commit();
			}
			break;

		}
	}
	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			isExit=false;
		};
	};
	@Override
	public void onBackPressed() {
	if (PersonalFragement.unEixt) {
			super.onBackPressed();
			return;
		}
		if(!isExit){
			isExit=true;
			Toast.makeText(getApplicationContext(), "再次点击返回键退出程序", Toast.LENGTH_SHORT).show();
			handler.sendEmptyMessageDelayed(0, 2000);
			return;
		}
		MyApplication.ISLOGIN=false;
		Viwepager.stopPlay();
		deleteImageCache(new File(MyApplication.IMAGE_CACHE_PATH));
		//把fragment 返回栈清空
		getSupportFragmentManager().beginTransaction().addToBackStack(null).commit();
		finish();
		super.onBackPressed();
	}
	private void deleteImageCache(File file) {
		if (file.isFile()) {  
			file.delete();  
		    return;  
		 }  
		if(file.isDirectory()){  
    	   File[] childFiles = file.listFiles();  
           if (childFiles == null || childFiles.length == 0) {  
	            file.delete();  
	            return;  
	       }
           for (int i = 0; i < childFiles.length; i++) {  
        	   deleteImageCache(childFiles[i]);  
           }  
	       file.delete();  
       }  
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("menu");
		return super.onCreateOptionsMenu(menu);
	}
	@Override  
	  public boolean onMenuOpened(int featureId, Menu menu){  
//	      switchSysMenuShow();  
	      return false;// 返回为true 则显示系统menu   
	  }  
	class HomeActivityReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			finish();
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		MyApplication.ISLOGIN=false;
		unregisterReceiver(receiver);
	}
	
}
