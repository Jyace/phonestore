package com.phoneshop.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
/**
 * 版本更新的显示页面
 * @author 何
 *
 */
public class VersionUpdateActivity extends Activity {
	private Button bt_back_fromVersionUpdate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_version_update);
		 
		bt_back_fromVersionUpdate = (Button) findViewById(R.id.bt_back_fromVersionUpdate);
		bt_back_fromVersionUpdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				VersionUpdateActivity.this.finish();
				
			}
		});
	}
}
