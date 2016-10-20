package com.phoneshop.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
/**
 * 主界面pager的适配器
 * */
public class HomeFragmentAdapter extends FragmentPagerAdapter {
	private List<Fragment> fragments;
	public HomeFragmentAdapter(FragmentManager fm,List<Fragment> fragments) {
		super(fm);
		this.fragments=fragments;
	}	
	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}
	@Override
	public int getCount() {
		return fragments.size();
	}

}
