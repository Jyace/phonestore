package com.phoneshop.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ViwepagerAdapter extends PagerAdapter {
	private List<ImageView> images;
	public ViwepagerAdapter(List<ImageView> images) {
		this.images=images;
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(images.get(position));
		return images.get(position);
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(images.get(position));
	}
	@Override
	public int getCount() {
		return images.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view==object;
	}

}
