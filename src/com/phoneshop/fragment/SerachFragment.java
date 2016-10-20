package com.phoneshop.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 搜索页面的fragment
 * @author 何
 *
 */
public class SerachFragment extends Fragment {
	private View view;
	private Button bt_search;
	private EditText et_searchContent;
	private TextView tv_searchResult;
	private ListView phoneList_search;
	private List<PhoneInfo> infos;
	private String url = MyApplication.LOCAL_HOST+"phoneshop/nameServlet";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.search, container, false);
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		initView();
		
		bt_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String editText = et_searchContent.getText().toString().trim(); //获得输入内容
				if (editText.equals(null)) {
					Toast.makeText(getActivity(), "搜索内容不能为空！", Toast.LENGTH_SHORT).show();
				}else{
					getSearchDataWithThread(editText);
				}
			}
		});
		//listView的单项点击监听，跳转到详情页面
		phoneList_search.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity().getApplicationContext(), PhoneDetailsActivity.class);
				intent.putExtra("phoneInfo", infos.get(position));
				startActivity(intent);
			}
		});
		
	}
	
	/**初始化控件*/
	private void initView() {
		bt_search = (Button) view.findViewById(R.id.bt_search);
		et_searchContent = (EditText) view.findViewById(R.id.et_searchContent);
		tv_searchResult = (TextView) view.findViewById(R.id.tv_searchResult);
		tv_searchResult.setVisibility(View.GONE);
		phoneList_search = (ListView) view.findViewById(R.id.phoneList_search);
	}
	private Handler handler = new Handler(){
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 125:
				infos = (List<PhoneInfo>) msg.obj;
				setSearchResultText();
				PhoneAdapter adapter = new PhoneAdapter(getActivity().getApplicationContext(), infos);
				phoneList_search.setAdapter(adapter);
				break;
			}
		}
		
		/** 设置“搜索结果”的是否隐藏和显示*/
		private void setSearchResultText() {
			tv_searchResult.setVisibility(View.VISIBLE);
			if (null != infos && infos.size() != 0) {
				tv_searchResult.setText("搜索结果 >>");
			}else {
				tv_searchResult.setText("没有找到符合条件的产品");
			}
		};
	};
	
	/** 按手机名字，开启线程从服务获得数据*/
	private void getSearchDataWithThread(final String phoneName) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpURLConnection conn = null;
				try {
					URL newUrl = new URL(url+"?searchPhoneName="+URLEncoder.encode(phoneName, "UTF-8"));
					conn = (HttpURLConnection) newUrl.openConnection();
					conn.setRequestMethod("GET");
					conn.setReadTimeout(5000);
					String line;
					StringBuilder sb = new StringBuilder();
					if (conn.getResponseCode() == 200) {
						BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						while((line = reader.readLine()) != null){
							sb.append(line);
						}
						String result = sb.toString();
						Gson gson = new Gson();
						Type type = new TypeToken<List<PhoneInfo>>(){}.getType();
						List<PhoneInfo> infos = gson.fromJson(result, type);
						handler.obtainMessage(125, infos).sendToTarget();
					}
					
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					if (conn != null) {
						conn.disconnect();
					}
				}
			}
		}).start();
	}
}
