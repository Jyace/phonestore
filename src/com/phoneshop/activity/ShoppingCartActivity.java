package com.phoneshop.activity;

import java.util.List;

import com.phoneshop.adapter.ShoppingCartAdapter;
import com.phoneshop.app.MyApplication;
import com.phoneshop.dao.ShoppingCartDao;
import com.phoneshop.entity.ShoppingCartInfo;
import com.phoneshop.fragment.PersonalFragement;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShoppingCartActivity extends Activity implements OnClickListener{
	private Button bt_shoppingCartBack,playment;
	private ListView shoppingCartListView;
	private TextView totalPrice;
	private CheckBox selectAll;
	private int playmentNum;
	private List<ShoppingCartInfo> infos;
	private ShoppingCartAdapter adapter;
	private ShoppingCartReceiver receiver;
	public static boolean allCheck=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shoppingcart);
		initView();
		registerBroadcast();
		final ShoppingCartDao dao=new ShoppingCartDao(this);
		infos=dao.queryByUserName(PersonalFragement.currentUser);
		adapter=new ShoppingCartAdapter(this,infos);
		shoppingCartListView.setAdapter(adapter);
		bt_shoppingCartBack.setOnClickListener(this);
		selectAll.setOnClickListener(this);
		playment.setOnClickListener(this);
		shoppingCartListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				AlertDialog.Builder builder=new Builder(ShoppingCartActivity.this);
				builder.setTitle("删除操作").setMessage("是否将本商品从购物车删除？")
						.setNegativeButton("取消", null)
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								String phongName = infos.get(position).getPhoneName();
								dao.removeOut(phongName);
								infos.remove(position);
								adapter.notifyDataSetChanged();
							}		
					}).create().show();
				return true;
			}
		});
	}
	
	private void registerBroadcast() {
		receiver=new ShoppingCartReceiver();
		IntentFilter filter=new IntentFilter(MyApplication.SHOPPINGCARTACTIVITY);
		registerReceiver(receiver, filter);
	}
	private void initView() {
		bt_shoppingCartBack=(Button) findViewById(R.id.bt_shoppingCartBack);
		playment=(Button) findViewById(R.id.playment);
		shoppingCartListView=(ListView) findViewById(R.id.shoppingCartListView);
		totalPrice=(TextView) findViewById(R.id.totalMoney);
		selectAll=(CheckBox) findViewById(R.id.selectAll);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_shoppingCartBack:
			finish();
			break;
		case R.id.playment:
			new AlertDialog.Builder(this)
			.setTitle("提示")
			.setMessage("所选商品总价"+totalPrice.getText()+",你确定购买？")
			.setNegativeButton("取消",null)
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Toast.makeText(getApplicationContext(), "购买成功", Toast.LENGTH_SHORT).show();
				}
			})
			.create().show();
			break;
		case R.id.selectAll:
			ShoppingCartAdapter.totalPrice = 0;
			if (selectAll.isChecked()) {
				System.out.println("点击全选框，选中");
				for (int i = 0; i < infos.size(); i++) {
					infos.get(i).setIsChecked(1);
				}
				allCheck=true;
				playmentNum = infos.size();
				playment.setText("结算("+infos.size()+")");
			}else{
				System.out.println("点击全选框，没有选中");
				for (int i = 0; i < infos.size(); i++) {
					infos.get(i).setIsChecked(0);
				}
				playment.setText("结算("+0+")");
				allCheck=false;
				totalPrice.setText("合计:￥"+0);
				playmentNum = 0;
			}
			adapter.notifyDataSetChanged();
			break;
		}
	}
	public void operate(String operate, int state, int position, int totalPrice2) {
		if (operate.equals("add")) {
			playmentNum++;
		}else{;
			playmentNum--;
		}
		
		totalPrice.setText("合计:￥"+totalPrice2);
		infos.get(position).setIsChecked(state);
		playment.setText("结算("+playmentNum+")");
		
		if (playmentNum<infos.size()) {
			selectAll.setChecked(false);
			allCheck=false;
		}else if(playmentNum==infos.size()){
			selectAll.setChecked(true);
			allCheck=true;
		}
		
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}
	class ShoppingCartReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			int totalPrice1=intent.getIntExtra("totalPrice", 0);
			totalPrice.setText("合计:￥"+totalPrice1);
		}
	}
}
