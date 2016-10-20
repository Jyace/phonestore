package com.phoneshop.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

/**
 * popupWinddow中“关于我们”跳转页面
 * @author 何
 *
 */
public class AboutUsActivity extends Activity {
	private Button bt_back_fromAboutUs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_about_us);
		
		bt_back_fromAboutUs = (Button) findViewById(R.id.bt_back_fromAboutUs);
		bt_back_fromAboutUs.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}
}
