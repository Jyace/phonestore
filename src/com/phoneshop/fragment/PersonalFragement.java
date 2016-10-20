package com.phoneshop.fragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.phoneshop.activity.R;
import com.phoneshop.app.MyApplication;
import com.phoneshop.entity.User;
public class PersonalFragement extends Fragment implements OnClickListener {
	private View view;
	private TextView title;
	private EditText username,password;
	private Button login,register;
	protected static final String url=MyApplication.LOCAL_HOST+"phoneshop/loginServlet";//web网页url
	private Handler handler;
	private User user;
	private static final int LOGIN_MSG=101;
	public static String currentUser;//记录当前登录的用户名
	public static boolean unEixt;//如果当前fragment为PersonalFragement的子fragement，为true,按下返回键不会退出应用，而是返回上一级fragment
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.fragement_personal, container, false);
		intiView();
		 handler=new Handler(){
			 @Override
			 public void handleMessage(Message msg) {
				 switch (msg.what) {
				case LOGIN_MSG:
					if (user.isLogin()) {
						Toast.makeText(getActivity(), "登录成功", Toast.LENGTH_SHORT).show();
						currentUser=user.getUsername();
						user.setLogin(false);
						MyApplication.ISLOGIN=true;
						getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.tabContent,
								new AccountCenterlFragment()).addToBackStack(null).commit();
					}else {
						Toast.makeText(getActivity(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
					}
					break;
				}
		}};
		return view;
	}
	
	private void intiView() {
		username=(EditText) view.findViewById(R.id.userName);
		password=(EditText) view.findViewById(R.id.passWord);
		login=(Button) view.findViewById(R.id.login);
		register=(Button) view.findViewById(R.id.regist);
		login.setOnClickListener(this);
		register.setOnClickListener(this);
		title=(TextView)getActivity().findViewById(R.id.home_title);
		title.setText("登录");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login:
			final String usernameStr=username.getText().toString();
			final String passwordStr=password.getText().toString();
			if (usernameStr!=null&&!usernameStr.trim().equals("")&&passwordStr!=null&&!passwordStr.trim().equals("")) {
				//final String passwordEncrypted=new MessageTool().getMess(passwordStr);//密码加密
				new Thread(){
					@Override
					public void run() {
						String json=doHttpPost(usernameStr,  passwordStr);
						Gson gson=new Gson();
						Type type=new com.google.gson.reflect.TypeToken<com.phoneshop.entity.User>(){}.getType();
						user=gson.fromJson(json, type);
						handler.sendEmptyMessage(LOGIN_MSG);
					}
				}.start();
			}
			else
			{
				Toast.makeText(getActivity(), "用户名与密码不能为空", Toast.LENGTH_SHORT).show();
			}
			break;
			
		case R.id.regist:
			title.setText("注册");
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.tabContent,
					new RegisterFragment()).addToBackStack(null).commit();
			break;
		}
	}

	
	
	
	private String doHttpPost(String username,String password) {
		HttpClient httpClient=new DefaultHttpClient();
		HttpPost httpPost=new HttpPost(url);
		List<NameValuePair> valuePairs=new ArrayList<NameValuePair>();
		valuePairs.add(new BasicNameValuePair("opreation", "login"));
		valuePairs.add(new BasicNameValuePair("username", username));
		valuePairs.add(new BasicNameValuePair("password", password));
		String result=null;
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(valuePairs, "UTF-8"));
			 HttpResponse  httpResponse=httpClient.execute(httpPost);
			 if (httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK) {
				 result=EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	@Override
	public void onPause() {
		if (AccountCenterlFragment.exit_account) {
			AccountCenterlFragment.reEntrance_account=true;
		}
		super.onPause();
	}
}
