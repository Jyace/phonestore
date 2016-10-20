package com.phoneshop.fragment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.phoneshop.activity.PhoneDetailsActivity;
import com.phoneshop.activity.R;
import com.phoneshop.adapter.PhoneAdapter;
import com.phoneshop.app.MyApplication;
import com.phoneshop.entity.PhoneInfo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class PhoneFragment extends Fragment {
	private View view;
	private String brand;
	private List<PhoneInfo> infos;
	private ListView phoneList;
	private String url=MyApplication.LOCAL_HOST+"phoneshop/brandServlet";
	public PhoneFragment(String brand) {
		//获取标题
		this.brand=brand;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.phone, null);
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//phoneList初始化
		phoneList=(ListView) view.findViewById(R.id.phoneList);
		GetDataThread();
		phoneList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity().getApplicationContext(),PhoneDetailsActivity.class);
				intent.putExtra("phoneInfo", infos.get(position));
				startActivity(intent);
			}
		});
	}
	private Handler handler=new Handler(){
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 122:
				infos=(List<PhoneInfo>) msg.obj;
				PhoneAdapter adapter=new PhoneAdapter(getActivity().getApplicationContext(),infos);
				phoneList.setAdapter(adapter);
				break;
			}
		};
	};
	private void GetDataThread() {
		new Thread(){
			public void run() {
				HttpURLConnection conn=null;
				try {
					//服务端brandServlet的url并且设置编码为utf-8
					URL newUrl=new URL(url+"?brand="+URLEncoder.encode(brand, "UTF-8"));
					//打开连接
					conn=(HttpURLConnection) newUrl.openConnection();
					//get提交方式
					conn.setRequestMethod("GET");
					//超时5秒
					conn.setConnectTimeout(5000);
					//设置行
					String line;
					//设置StringBuilder
					StringBuilder sb=new StringBuilder();
					//判断是否连接上
					if (conn.getResponseCode()==200) {
						//缓存读取
						BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
						//循环行
						while((line=reader.readLine())!=null){
							//增加到StringBuilder
							sb.append(line);
						}
						//内容加到result
						String result=sb.toString();
						//进行json解析
						Gson gson=new Gson();
						Type type=new TypeToken<List<PhoneInfo>>(){}.getType();
						List<PhoneInfo> infos=gson.fromJson(result, type);
						handler.obtainMessage(122, infos).sendToTarget();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					conn.disconnect();
				}
			};
		}.start();
	}
}
