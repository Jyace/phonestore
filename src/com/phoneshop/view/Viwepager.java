package com.phoneshop.view;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.phoneshop.activity.R;
import com.phoneshop.adapter.ViwepagerAdapter;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class Viwepager extends FrameLayout{
	private static boolean isAutoPlay=true;
	private int[] imageResId;
	private List<ImageView> images;
	private List<ImageView> dots;
	private ViewPager viewPager;
	private int currentItem=0;
	private static ScheduledExecutorService service;
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 110:
				int position=(Integer) msg.obj;
				for (int i = 0; i < dots.size(); i++) {
					if (i==position) {
						dots.get(i).setImageResource(R.drawable.dot_black);
					}else{
						dots.get(i).setImageResource(R.drawable.dot_white);
					}
				}
				break;
			case 111:
				viewPager.setCurrentItem(currentItem);
				break;
			}
		};
	};
	public Viwepager(Context context, AttributeSet attrs) {
		super(context, attrs);
		initData();
		initUI(context);
		if (isAutoPlay) {
			startPlay();
		}
	}
	private void startPlay() {
		service=Executors.newSingleThreadScheduledExecutor();
		service.scheduleAtFixedRate(new ScheduleTask() , 1, 5, TimeUnit.SECONDS);
	}
	public static void stopPlay(){
		service.shutdown();
	}
	private void initUI(Context context) {
		View layout=LayoutInflater.from(context).inflate(R.layout.customview, this, true);
		for (int id : imageResId) {
			ImageView view=new ImageView(context);
			view.setImageResource(id);
			view.setScaleType(ScaleType.FIT_XY);
			images.add(view);
		}
		
		dots.add((ImageView) layout.findViewById(R.id.dot_1));
		dots.add((ImageView) layout.findViewById(R.id.dot_2));
		dots.add((ImageView) layout.findViewById(R.id.dot_3));
		dots.add((ImageView) layout.findViewById(R.id.dot_4));
		dots.add((ImageView) layout.findViewById(R.id.dot_5));
		
		viewPager=(ViewPager) layout.findViewById(R.id.customViewPager);
		ViwepagerAdapter adapter=new ViwepagerAdapter(images);
		viewPager.setFocusable(true);
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				currentItem=position;
				for (int i = 0; i < dots.size(); i++) {
					if (i==position) {
						handler.obtainMessage(110, position).sendToTarget();
						break;
					}
				}
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}
	private void initData() {
		imageResId=new int[]{R.drawable.iphone_logo,R.drawable.samsung_logo,R.drawable.mi_logo,R.drawable.huawei_logo,R.drawable.meizu_logo};
		images=new ArrayList<ImageView>();
		dots=new ArrayList<ImageView>();
		
	}
	class ScheduleTask implements Runnable{
		@Override
		public void run() {
			currentItem=(currentItem+1)%images.size();
			handler.obtainMessage(111).sendToTarget();
		}
		
	}
}
