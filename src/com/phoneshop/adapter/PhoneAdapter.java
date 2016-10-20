package com.phoneshop.adapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import com.phoneshop.activity.R;
import com.phoneshop.app.MyApplication;
import com.phoneshop.entity.PhoneInfo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PhoneAdapter extends BaseAdapter {
	private Context context;
	private List<PhoneInfo> infos;
	private ViewHolder viewHolder;
	private String imageName;
	private String name;
	private HashMap<String, SoftReference<Bitmap>> cacheMap;
	public PhoneAdapter(Context context, List<PhoneInfo> infos) {
		this.context=context;
		this.infos=infos;
		cacheMap=new HashMap<String, SoftReference<Bitmap>>();
	}
	@Override
	public int getCount() {
		return infos.size();
	}

	@Override
	public Object getItem(int position) {
		return infos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView==null) {
			//如果为空就初始化
			convertView=LayoutInflater.from(context).inflate(R.layout.phonelist, null);
			//实例化viewHolder
			viewHolder=new ViewHolder();
			//手机描述控件初始化
			viewHolder.tv_phoneIntroduce=(TextView) convertView.findViewById(R.id.tv_phoneIntroduce);
			//手机价格控件初始化
			viewHolder.tv_phonePrice=(TextView) convertView.findViewById(R.id.tv_phonePrice);
			//把初始化完毕的convertView setTag
			convertView.setTag(viewHolder);
		}else{
			//如果convertView不为空直接getTag
			viewHolder=(ViewHolder) convertView.getTag();
			
		}
		//手机图片控件的初始化
		final ImageView phoneImage=(ImageView) convertView.findViewById(R.id.phoneImage);
		//设置手机的描述
		viewHolder.tv_phoneIntroduce.setText(infos.get(position).getDescribe());
		//设置手机的价格
		viewHolder.tv_phonePrice.setText("￥"+infos.get(position).getPrice()+"");
		//获取手机图片的名字
		imageName=infos.get(position).getUrl();
		//手机图片本地缓存的完整路径
		String imagePath=MyApplication.IMAGE_CACHE_PATH+imageName;
		//判断加载本地缓存或从网络获取图片
		Handler handler=new Handler(){
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
					//获取图片的名字
					case 123:
						name=(String) msg.obj;
						break;
					//bitmap位图
					case 124:
						Bitmap bitmap=(Bitmap) msg.obj;
						//设置图片
						phoneImage.setImageBitmap(bitmap);
						cacheMap.put(name, new SoftReference<Bitmap>(bitmap));
						//将bitmap转为byte数组
						byte[] bs=bitmapToBytes(bitmap);
						//将byte数组写到sdcard中
						writeToSdcard(bs,name);
						break;
				}
			}
		};
		if(cacheMap.containsKey(imageName)){
			SoftReference<Bitmap> reference=cacheMap.get(imageName);
			Bitmap bitmap=reference.get();
			if (bitmap==null&&new File(imagePath).exists()) {
				bitmap=BitmapFactory.decodeFile(imagePath);
			}
			phoneImage.setImageBitmap(bitmap);
		}else if(new File(imagePath).exists()){
			//把本地缓存的图片转为bitmap
			Bitmap bitmap=BitmapFactory.decodeFile(imagePath);
			//设置图片
			phoneImage.setImageBitmap(bitmap);
		}else{
			//否则就开启加载图片的线程
			imageThread(imageName,handler);
		}
		return convertView;
	}
	
	
		//保存图片到本地缓存
		private void writeToSdcard(byte[] bs,String name) {
			//本地缓存的文件夹地址转为file
			File file=new File(MyApplication.IMAGE_CACHE_PATH);
			//判断文件夹不存在
			if (!file.exists()) {
				//创建一个文件夹
				file.mkdirs();
			}
			//定义file的输出流
			FileOutputStream out=null;
			try {
				//本地缓存图片文件的完整路径
				out=new FileOutputStream(file.getAbsoluteFile()+"/"+name);
				//写出byte数组
				out.write(bs);
				//结束输出流
				out.flush();
				//关闭输出流节约内存
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		private byte[] bitmapToBytes(Bitmap bitmap) {
			ByteArrayOutputStream out=new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			return out.toByteArray();
		}
	
	private void imageThread(final String imagePath,final Handler handler) {
		//一个图片开启一个线程
		new Thread(){
			public void run() {
				HttpURLConnection conn=null;
				try {
					//把图片的名字转为url
					URL newUrl=new URL(MyApplication.LOCAL_HOST+"images/"+imageName);
					//使用HTTP协议打开url
					conn=(HttpURLConnection) newUrl.openConnection();
					//提交方式是get
					conn.setRequestMethod("GET");
					//设置5秒超时
					conn.setConnectTimeout(5000);
					//转换为输入流
					InputStream in=conn.getInputStream();
					//判断是否连接上
					if (conn.getResponseCode()==200) {
						//设置options
						Options options=new Options();
						//按比例缩小
						options.inSampleSize=2;
						//图片的色彩度
						options.inPreferredConfig=Bitmap.Config.ARGB_4444;
						//是否加载边框
						options.inJustDecodeBounds = false;
						//把输入流转化为bitmap
						Bitmap bitmap=BitmapFactory.decodeStream(in, null, options);
						//用handler机制发送图片的名字
						handler.obtainMessage(123, imagePath).sendToTarget();
						//发送bitmap位图
						handler.obtainMessage(124, bitmap).sendToTarget();
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally{
					conn.disconnect();
				}
			};
		}.start();
	}
	class ViewHolder{
		TextView tv_phoneIntroduce,tv_phonePrice;
	}
}
