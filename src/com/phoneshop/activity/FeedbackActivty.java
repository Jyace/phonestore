package com.phoneshop.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * 意见反馈页面
 * @author 何
 *
 */
public class FeedbackActivty extends Activity {
	private Button bt_back_fromFeedback,bt_feedbackCommit;
	private ImageView im_ok;
	private TextView tv_commitOK;
	private EditText et_feedbackContent,et_contactInfo;
	private RelativeLayout ReLayout_commitOK;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_feedback);
		
		initView();
		setOnClickAndView();
	}
	//返回按钮监听
	private void setOnClickAndView() {
		bt_back_fromFeedback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FeedbackActivty.this.finish();
			}
		});
		//提交按钮监听
		bt_feedbackCommit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String feedbackContent = et_feedbackContent.getText().toString().trim();
				String contactInfo = et_contactInfo.getText().toString().trim();
				//反馈信息为空
				if (feedbackContent.equals(null)|| feedbackContent.equals("")) {
					ReLayout_commitOK.setVisibility(View.VISIBLE);
					im_ok.setVisibility(View.GONE);
					tv_commitOK.setText("*请输入反馈信息");
					tv_commitOK.setTextColor(Color.RED);
				//联系信息为空
				}else if(contactInfo.equals(null)|| contactInfo.equals("")){
					ReLayout_commitOK.setVisibility(View.VISIBLE);
					im_ok.setVisibility(View.GONE);
					tv_commitOK.setText("*请提供联系信息");
					tv_commitOK.setTextColor(Color.RED);
				}else {	//正常提交
					ReLayout_commitOK.setVisibility(View.VISIBLE);
					im_ok.setVisibility(View.VISIBLE);
					tv_commitOK.setText("提交成功");
					tv_commitOK.setTextColor(Color.GREEN);
					//这里写内容提交代码
				}
			}
		});
	}
	private void initView() {
		bt_back_fromFeedback = (Button) findViewById(R.id.bt_back_fromFeedback);
		bt_feedbackCommit = (Button) findViewById(R.id.bt_feedbackCommit);
		im_ok = (ImageView) findViewById(R.id.im_ok);
		tv_commitOK = (TextView) findViewById(R.id.tv_commitOK);
		et_feedbackContent = (EditText) findViewById(R.id.et_feedbackContent);
		et_contactInfo = (EditText) findViewById(R.id.et_contactInfo);
		ReLayout_commitOK = (RelativeLayout) findViewById(R.id.ReLayout_commitOK);
		ReLayout_commitOK.setVisibility(View.GONE);
	}

}
