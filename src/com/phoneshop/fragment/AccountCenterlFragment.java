package com.phoneshop.fragment;

import com.phoneshop.activity.R;
import com.phoneshop.activity.ShoppingCartActivity;
import com.phoneshop.app.MyApplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class AccountCenterlFragment extends Fragment {
	private View view;
	private Button bt_changeAccout;
	private TextView tv_accountName,tv_address,tv_myShoppingCart;
	public  static boolean reEntrance_account;//标记为登录后进入AccountCenterlFragment
	public static boolean exit_account;//标记为从AccountCenterlFragment退出到PersonalCenterlFragment
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			view=inflater.inflate(R.layout.account_centerl, container, false);
			initView();
			setListener();
			return view;
			
		}
		private void initView() {
			tv_address=(TextView) view.findViewById(R.id.tv_address);
			tv_accountName=(TextView) view.findViewById(R.id.tv_accountName);
			tv_accountName.setText(PersonalFragement.currentUser);
			
			tv_myShoppingCart=(TextView) view.findViewById(R.id.tv_myShoppingCart);
			bt_changeAccout = (Button) view.findViewById(R.id.bt_changeAccout);
		}
		
		private void setListener() {
			tv_address.setOnClickListener(listener);
			tv_myShoppingCart.setOnClickListener(listener);
			bt_changeAccout.setOnClickListener(listener);
		}
		
		private OnClickListener listener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				//进入收货地址
				case R.id.tv_address:
					getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.tabContent,
							new AddressFragment()).addToBackStack(null).commit();
					break;
				//进入购物车
				case R.id.tv_myShoppingCart:
					Intent intent = new Intent(getActivity(),ShoppingCartActivity.class);
					startActivity(intent);
				break;
				//切换用户
				case R.id.bt_changeAccout:
					MyApplication.ISLOGIN = false;
					getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.tabContent, new PersonalFragement()).commit();
					break;
				}
			}
		};
		@Override
		public void onResume() {
			if (reEntrance_account) {
				PersonalFragement.unEixt=false;
			}else{
			PersonalFragement.unEixt=true;
			}
			super.onResume();
		}
		@Override
		public void onPause() {
			PersonalFragement.unEixt=false;
			exit_account=true;
			super.onPause();
		}
}
