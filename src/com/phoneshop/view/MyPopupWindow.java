package com.phoneshop.view;

import com.phoneshop.activity.AboutUsActivity;
import com.phoneshop.activity.FeedbackActivty;
import com.phoneshop.activity.R;
import com.phoneshop.activity.VersionUpdateActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

public class MyPopupWindow extends PopupWindow {
	private View popupWinView;
	private int screenWidth,screenHeigt;
	private Button bt_aboutUs,bt_feedback,bt_versionUpdate,bt_quitStore;
	private Activity activity;
	public MyPopupWindow(Activity context){
		super(context);
		this.activity = context;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		popupWinView = inflater.inflate(R.layout.popuwindow, null);
		//手机屏幕DisplayMetrics属性
		DisplayMetrics metrics=new DisplayMetrics();
		//获取屏幕的属性
		context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		//获得屏幕的宽和高
		screenWidth = metrics.widthPixels;
		screenHeigt = metrics.heightPixels;
		
		// 设置SelectPicPopupWindow的View  
        this.setContentView(popupWinView);  
        // 设置SelectPicPopupWindow弹出窗体的宽  
        this.setWidth(screenWidth / 2 );  
        // 设置SelectPicPopupWindow弹出窗体的高  
        this.setHeight(LayoutParams.WRAP_CONTENT);  
        // 设置SelectPicPopupWindow弹出窗体可点击  
        this.setFocusable(true);  
        this.setOutsideTouchable(true);  
        // 刷新状态  
        this.update();  
        // 实例化一个ColorDrawable颜色为半透明  
        ColorDrawable dw = new ColorDrawable(0000000000);  
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作  
        this.setBackgroundDrawable(dw);  
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);  
        // 设置SelectPicPopupWindow弹出窗体动画效果  
        this.setAnimationStyle(R.style.AnimationPreview);  
        //初始化控件
        initView();
      //设置mPopWindow里的item单击监听
      setItemOnClickListener();
	}
	
	/**设置mPopWindow里的item单击监听*/
	private void setItemOnClickListener() {
		bt_aboutUs.setOnClickListener(listener);
		bt_feedback.setOnClickListener(listener);
		bt_versionUpdate.setOnClickListener(listener);
		bt_quitStore.setOnClickListener(listener);
	}

	/**设置mPopWindow里的item单击监听*/
	private android.view.View.OnClickListener listener = new  OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.bt_aboutUs:
				MyPopupWindow.this.dismiss();  
				Intent intent1 = new Intent(activity.getApplicationContext(), AboutUsActivity.class);
				activity.startActivity(intent1);
				break;
			case R.id.bt_feedback:
				MyPopupWindow.this.dismiss();  
				Intent intent2 = new Intent(activity.getApplicationContext(), FeedbackActivty.class);
				activity.startActivity(intent2);
				break;
			case R.id.bt_versionUpdate:
				MyPopupWindow.this.dismiss();  
				Intent intent3 = new Intent(activity.getApplicationContext(), VersionUpdateActivity.class);
				activity.startActivity(intent3);
				break;
			case R.id.bt_quitStore:
				//退出popupWindow，当某个Dialog或者某个PopupWindow正在显示的时候我们去finish()了承载该Dialog(或PopupWindow)的Activity时，就会抛Window Leaked（窗体泄漏）异常
				//关闭(finish)某个Activity前，要确保附属在上面的Dialog或PopupWindow已经关闭(dismiss)了。
				MyPopupWindow.this.dismiss();  
				activity.finish();
				break;

			}
			
		}
	};


	/** 显示popupWindow  */  
    public void showPopupWindow(View parent) {  
        if (!this.isShowing()) {  
            // 以下拉方式显示popupwindow  
            this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 0);  
        } else {  
            this.dismiss();  
        }  
    }  
	
    /**初始化控件*/
	private void initView() {
		bt_aboutUs = (Button) popupWinView.findViewById(R.id.bt_aboutUs);
		bt_feedback = (Button) popupWinView.findViewById(R.id.bt_feedback);
		bt_versionUpdate = (Button) popupWinView.findViewById(R.id.bt_versionUpdate);
		bt_quitStore = (Button) popupWinView.findViewById(R.id.bt_quitStore);
	}
	
}
