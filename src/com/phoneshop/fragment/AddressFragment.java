package com.phoneshop.fragment;

import com.phoneshop.activity.R;
import com.phoneshop.dao.AddressDao;
import com.phoneshop.entity.Address;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddressFragment extends Fragment  {
	private View view;
	private TextView edit,trueName,phone,address_tv,no_address;
	public static long address_id=-1;//记录收货地址id	
	private Address address;
	public  static boolean haveAddress;
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			view=inflater.inflate(R.layout.fragement_address, container, false);
			intiView();
			address=new AddressDao(getActivity()).queryAddress(PersonalFragement.currentUser);
			if (address==null) {
				no_address.setVisibility(View.VISIBLE);
				Toast.makeText(getActivity(), "还没创建收货地址，点击“编辑”创建吧",Toast.LENGTH_SHORT).show();
			}
			else{
				show(address.getTrueName(), address.getPhone(), address.getAddress());
				haveAddress=true;
				Toast.makeText(getActivity(),  "收货地址已创建",Toast.LENGTH_SHORT).show();
			}
			edit.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.tabContent,
							new EditFragment()).addToBackStack(null).commit();
				}
			});
			return view;
		}



private void intiView() {
	edit=(TextView) view.findViewById(R.id.editimage);
	trueName=(TextView) view.findViewById(R.id.name);
	phone=(TextView) view.findViewById(R.id.phonenum);
	address_tv=(TextView) view.findViewById(R.id.address);
	no_address=(TextView) view.findViewById(R.id.no_address);
	}





private void show(String trueNameStr,String phoneStr,String addressStr){
	trueName.setVisibility(View.VISIBLE);
	phone.setVisibility(View.VISIBLE);
	address_tv.setVisibility(View.VISIBLE);
	trueName.setText(trueNameStr);
	phone.setText(phoneStr);
	address_tv.setText(addressStr);
	
}
@Override
public void onResume() {
	PersonalFragement.unEixt=true;
	super.onResume();
}
}
