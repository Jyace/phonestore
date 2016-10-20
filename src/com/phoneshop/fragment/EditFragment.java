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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditFragment extends Fragment  {
	private View view;
	private TextView save;
	private EditText trueName,phoneNum,address;
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			view=inflater.inflate(R.layout.address, container, false);
			intiView();
			save.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
				Address goodsAddress=new Address(PersonalFragement.currentUser,trueName.getText().toString(), phoneNum.getText().toString(),
									address.getText().toString());
				if (AddressFragment.haveAddress) {
					new AddressDao(getActivity()).update(goodsAddress);
					Toast.makeText(getActivity(), "信息已保存", Toast.LENGTH_SHORT).show();
				}
				else{
				long id=new AddressDao(getActivity()).add(goodsAddress);
				if (id==-1) {
					Toast.makeText(getActivity(), "信息保存失败，请重新编辑", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(getActivity(), "信息已保存", Toast.LENGTH_SHORT).show();
				}
			}		
				}
			});
			return view;
		}
		
		private void intiView() {
			save=(TextView) view.findViewById(R.id.save);
			trueName=(EditText) view.findViewById(R.id.truename);
			phoneNum=(EditText) view.findViewById(R.id.phonenum);
			address=(EditText) view.findViewById(R.id.address);
		}
}
