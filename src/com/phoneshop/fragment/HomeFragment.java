package com.phoneshop.fragment;

import java.util.ArrayList;
import java.util.List;

import com.phoneshop.activity.R;
import com.phoneshop.adapter.HomeFragmentAdapter;
import com.phoneshop.view.MyPopupWindow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class HomeFragment extends Fragment {
	private View view;
	private ViewPager viewPager;
	private Button bt_showPopupWin;
	private ImageView iv_bottom_line;
	private int screenWidth;
	private List<Fragment> fragments;
	private RadioGroup home_radio_button_group;
	private HomeFragmentAdapter adapter;
	private Animation animation;
	private MyPopupWindow mPopWindow;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.activity_index,container,false);
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//手机屏幕DisplayMetrics属性
		DisplayMetrics metrics=new DisplayMetrics();
		//获取屏幕的属性
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
		//获得屏幕的宽
		screenWidth=metrics.widthPixels;
		//初始化
		initData();
		//在fragment中添加一个fragment要用到getChildFragmentManager
		adapter=new HomeFragmentAdapter(getChildFragmentManager(),fragments);
		//设置adapter
		viewPager.setAdapter(adapter);
		//viewPager转化事件
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				//设置品牌控件的下标
				home_radio_button_group.check(position+R.id.index_tab1);
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		
		//找到点击出mPopWindow的按钮
		bt_showPopupWin = (Button) view.findViewById(R.id.bt_showPopupWin);
		//实例化弹出窗口
		mPopWindow = new MyPopupWindow(getActivity());  
		bt_showPopupWin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
	            mPopWindow.showPopupWindow(bt_showPopupWin);  
			}
		});
	}
	//初始化
	private void initData() {
		//viewPager初始化
		viewPager=(ViewPager) view.findViewById(R.id.viewPager);
		//下标线控件初始化
		iv_bottom_line=(ImageView) view.findViewById(R.id.iv_bottom_line);
		//品牌控件的初始化
		home_radio_button_group=(RadioGroup) view.findViewById(R.id.home_radio_button_group);
		//获取下标线的布局参数
		LayoutParams params=iv_bottom_line.getLayoutParams();
		//设置为屏幕宽的1/5
		params.width=screenWidth/5;
		//保存参数
		iv_bottom_line.setLayoutParams(params);
		//品牌控件的点击改变事件
		home_radio_button_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			//位移的初始值
			int preOffset=0;
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				//获取下标线的位移量
				int offset=(checkedId-R.id.index_tab1)*screenWidth/5;
				//设置动画
				animation=new TranslateAnimation(preOffset, offset, 0, 0);
				//下标线走到的位置为初始值
				preOffset=offset;
				//设置动画的长度
				animation.setDuration(100);
				//保留动画状态
				animation.setFillAfter(true);
				//加载动画
				iv_bottom_line.setAnimation(animation);
				//viewPager选中下标
				viewPager.setCurrentItem(checkedId-R.id.index_tab1);
			}
		});
		//为每个品牌控件增加一个fragment
		fragments=new ArrayList<Fragment>();
		//iPhonefragment
		fragments.add(new PhoneFragment("iPhone"));
		//SamSungfragment
		fragments.add(new PhoneFragment("SamSung"));
		//Mifragment
		fragments.add(new PhoneFragment("Mi"));
		//HuaWeifragment
		fragments.add(new PhoneFragment("HuaWei"));
		//MeiZufragment
		fragments.add(new PhoneFragment("MeiZu"));
	}
	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
}
