package com.phoneshop.tool;

import java.util.regex.Pattern;

import com.phoneshop.activity.R;

import android.content.Context;
import android.view.View;
import android.widget.TextView;


public class ValidatorTool {
	public static boolean validatorRequestUser(Context context,View view,String usernameStr,String passwordStr,String phoneStr,String emailStr){
		TextView t_username,t_password, t_phone, t_email;
		t_username=(TextView) view.findViewById(R.id.t_username);
		t_password=(TextView) view.findViewById(R.id.t_password);
		t_phone=(TextView) view.findViewById(R.id.t_phone);
		t_email=(TextView) view.findViewById(R.id.t_email);
		if (!Pattern.matches("[a-zA-Z0-9_]{6,20}",usernameStr)) {
			t_username.setVisibility(View.VISIBLE);
			return false;
		}else{
			t_username.setVisibility(View.GONE);
		}
		//格式为6-20位，只能是字母、数字和下划线
		if (!Pattern.matches("[A-Za-z0-9_]{6,20}",passwordStr)) {
			t_password.setVisibility(View.VISIBLE);
			return false;
		}else{
			t_password.setVisibility(View.GONE);
		}
		
		//11个数字组成的字符串[\w]{6,12}
		if (!Pattern.matches("[0-9]{11}",phoneStr)) {
			t_phone.setVisibility(View.VISIBLE);
			return false;
		}else{
			t_phone.setVisibility(View.GONE);
		}
		//邮箱格式
		if (!Pattern.matches("[a-zA-Z0-9_]{6,12}+@[a-zA-Z0-9]+\\.[a-zA-Z]{3}",emailStr)) {
			t_email.setVisibility(View.VISIBLE);
			return false;
		}else{
			t_email.setVisibility(View.GONE);
		}
		
		return true;
	}
}
