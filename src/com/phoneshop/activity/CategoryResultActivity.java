package com.phoneshop.activity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.phoneshop.adapter.PhoneAdapter;
import com.phoneshop.app.MyApplication;
import com.phoneshop.entity.PhoneInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 显示分类查找结果的页面
 * @author eduask 何
 *
 */
public class CategoryResultActivity extends Activity {
	private TextView tv_categoryModel;
	private ListView listView_categoryResult;
	private List<PhoneInfo> infos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.categoryresult);
		
		Intent intent = getIntent();
		String categoryModel = intent.getStringExtra("categoryModel").trim();
		if (categoryModel.equals("1000元以下")) {
			getData("price","0/1000");
		}else if(categoryModel.equals("1000-2000元")){
			getData("price", "1000/2000");
		}else if(categoryModel.equals("2000-3000元")){
			getData("price", "2000/3000");
		}else if(categoryModel.equals("3000元以上")){
			getData("price", "3000/10000");
		}else if(categoryModel.equals("小于4.5英寸")){
			getData("screenSize", "0英寸/4.5英寸");
		}else if(categoryModel.equals("4.5-5.0英寸")){
			getData("screenSize", "4.5英寸/5.0英寸");
		}else if(categoryModel.equals("大于5.0英寸")){
			getData("screenSize", "5.0英寸/8.0英寸");
		}else if(categoryModel.equals("android")){
			getData("system", "android");
		}else if(categoryModel.equals("ios")){
			getData("system", "ios");
		}
		tv_categoryModel = (TextView) findViewById(R.id.tv_categoryModel);
		listView_categoryResult = (ListView) findViewById(R.id.listView_categoryResult);
		
		tv_categoryModel.setText(categoryModel);
		
		
		listView_categoryResult.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				PhoneInfo info = infos.get(position);
				Intent intent = new Intent(getApplicationContext(), PhoneDetailsActivity.class);
				intent.putExtra("phoneInfo", info);
				startActivity(intent);
			}
		});
		
	}
	
	/**
	 * 根据字符串categoryModel判断，对数据库进行条件查找，返回符合条件的集合
	 * @param categoryModel
	 * @return	List<PhoneInfo>
	 */
	private Handler handler=new Handler(){
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 145:
				infos=(List<PhoneInfo>) msg.obj;
				PhoneAdapter adapter=new PhoneAdapter(getApplicationContext(),infos);
				listView_categoryResult.setAdapter(adapter);
				break;
			}
		};
	};
	private void getData(final String main,final String other) {
		new Thread(){
			public void run() {
				HttpURLConnection conn=null;
				try {
					URL url=new URL(MyApplication.LOCAL_HOST+"phoneshop/"+main+"Servlet?"+main+"="+URLEncoder.encode(other, "UTF-8"));
					conn=(HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(5000);
					String line;
					StringBuilder sb=new StringBuilder();
					if (conn.getResponseCode()==200) {
						BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
						while((line=reader.readLine())!=null){
							sb.append(line);
						}
						String result=sb.toString();
						Gson gson=new Gson();
						Type type=new TypeToken<List<PhoneInfo>>(){}.getType();
						List<PhoneInfo> infos=gson.fromJson(result, type);
						handler.obtainMessage(145, infos).sendToTarget();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					conn.disconnect();
				}
			};
		}.start();
	}
	
	public void goBack(View v){
		finish();
	}
}
