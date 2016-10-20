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
import com.phoneshop.activity.R;
import com.phoneshop.entity.User;
import com.phoneshop.tool.ValidatorTool;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterFragment extends Fragment   {
	private View view;
	private Button register,bt_registerBack;
	private EditText username,password,secondPassword,phone,email;
	private Handler handler;
	private TextView t_secondpassword;
	private User user=new User();
	private static final int REGISTER_MSG=102;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.fragement_register, container, false);
		t_secondpassword=(TextView) view.findViewById(R.id.t_secondpassword);
		intiView();
		 handler=new Handler(){
			 @Override
			 public void handleMessage(Message msg) {
				 switch (msg.what) {
				case REGISTER_MSG:
					if (user.isRegister()) {
						Toast.makeText(getActivity(), "注册成功", Toast.LENGTH_SHORT).show();
						PersonalFragement.currentUser=user.getUsername();
						user.setRegister(false);
						getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.tabContent,
							new PersonalFragement()).commit();
					}else {
						Toast.makeText(getActivity(), "此用户已被注册", Toast.LENGTH_SHORT).show();
					}
					break;

				default:
					break;
				}
		}};
		return view;
	}
	
	
	private void intiView() {
		username=(EditText) view.findViewById(R.id.username);
		password=(EditText) view.findViewById(R.id.password);
		secondPassword=(EditText) view.findViewById(R.id.secondpassword);
		phone=(EditText) view.findViewById(R.id.phone);
		email=(EditText) view.findViewById(R.id.email);
		register=(Button) view.findViewById(R.id.register);
		bt_registerBack=(Button) view.findViewById(R.id.bt_registerBack);
		bt_registerBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				getActivity().getSupportFragmentManager().popBackStack();
				getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.tabContent, new PersonalFragement()).commit();
			}
		});
		register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String usernameStr=username.getText().toString();
				final String passwordStr=password.getText().toString();
				final String secondPasswordStr=secondPassword.getText().toString();
				final String emailStr=email.getText().toString();
				final String phoneStr=phone.getText().toString();
				if (usernameStr!=null&&passwordStr!=null&&emailStr!=null&&phoneStr!=null
						&&!usernameStr.trim().equals("")&&!passwordStr.trim().equals("")&&!emailStr.trim().equals("")
						&&!phoneStr.trim().equals("")) {
						if (!passwordStr.equals(secondPasswordStr)) {
						t_secondpassword.setVisibility(View.VISIBLE);
						return;
					}
					t_secondpassword.setVisibility(View.GONE);
			boolean flag=new ValidatorTool().validatorRequestUser(getActivity(),view,usernameStr,passwordStr,phoneStr,emailStr);
				if (flag) {
				new Thread(){
				@Override
				public void run() {
					user=new User(usernameStr, passwordStr,phoneStr, emailStr);
					String json=doHttpPost("register", user);
					Gson gson=new Gson();
					Type type=new com.google.gson.reflect.TypeToken<com.phoneshop.entity.User>(){}.getType();
					user=gson.fromJson(json, type);
					handler.sendEmptyMessage(REGISTER_MSG);
					}
				}.start();
			}	
		}
				else{
					Toast.makeText(getActivity(), "请完善注册信息", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}


	
	
	private String doHttpPost(String opreation,User user) {
		HttpClient httpClient=new DefaultHttpClient();
		HttpPost httpPost=new HttpPost(PersonalFragement.url);
		List<NameValuePair> valuePairs=new ArrayList<NameValuePair>();
		valuePairs.add(new BasicNameValuePair("opreation", opreation));
		valuePairs.add(new BasicNameValuePair("username", user.getUsername()));
		valuePairs.add(new BasicNameValuePair("password", user.getPassword()));
		valuePairs.add(new BasicNameValuePair("phone", user.getPhone()));
		valuePairs.add(new BasicNameValuePair("email", user.getEmail()));
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
	public void onResume() {
		PersonalFragement.unEixt=true;
		super.onResume();
	}
	
	
	@Override
	public void onPause() {
		PersonalFragement.unEixt=false;
		super.onPause();
	}
}
