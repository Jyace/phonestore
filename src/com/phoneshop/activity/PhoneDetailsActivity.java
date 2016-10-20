package com.phoneshop.activity;

import java.net.HttpURLConnection;
import java.net.URL;

import com.phoneshop.app.MyApplication;
import com.phoneshop.dao.ShoppingCartDao;
import com.phoneshop.entity.PhoneInfo;
import com.phoneshop.entity.ShoppingCartInfo;
import com.phoneshop.fragment.PersonalFragement;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 显示手机详情的页面，从显示手机ListView的单项点击进入
 * @author eduask 何
 *
 */
public class PhoneDetailsActivity extends Activity implements OnClickListener{
	private ImageView im_large;
	private TextView tv_phoneName,tv_phoneSystem,tv_phonePrice,tv_phoneTime,
		tv_phoneThickness,tv_phoneScreenSize,tv_phoneCPUNum,tv_phonePixel,
		tv_phoneNetSize,tv_phoneDescribe;
	private Button bt_backList,bt_shoppingCart,bt_shoppingNow,bt_addToShoppingCart;
	private PhoneInfo info;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phonedetails);
		
		//通过intent获得该型号手机的信息
		Intent intent = getIntent();
		info = (PhoneInfo) intent.getSerializableExtra("phoneInfo");
		initView();
		setView();
		loadImage();
	}
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 111:
				Bitmap bitmap=(Bitmap) msg.obj;
				im_large.setImageBitmap(bitmap);
				break;
			}
		};
	};
	private void loadImage() {
		new Thread(){
			public void run() {
				HttpURLConnection conn=null;
				try {
					URL url=new URL(MyApplication.LOCAL_HOST+"images/"+info.getUrl());
					conn=(HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(5000);
					if (conn.getResponseCode()==200) {
						Bitmap bitmap=BitmapFactory.decodeStream(conn.getInputStream());
						handler.obtainMessage(111, bitmap).sendToTarget();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					conn.disconnect();
				}
			};
		}.start();
	}
	/**设置控件内容*/
	private void setView() {
		
		//im_large.setImageBitmap(bm) //bitmap 等待获得、设置
		tv_phoneName.setText(info.getName());
		tv_phoneSystem.setText(info.getSystem());
		tv_phonePrice.setText(info.getPrice()+"");
		tv_phoneTime.setText(info.getTimeToMarket());
		tv_phoneThickness.setText(info.getThick());
		tv_phoneScreenSize.setText(info.getScreenSize());
		tv_phoneCPUNum.setText(info.getCpu());
		tv_phonePixel.setText(info.getPixel());
		tv_phoneNetSize.setText(info.getNetSize());
		tv_phoneDescribe.setText(info.getDescribe());
		bt_backList.setOnClickListener(this);
		bt_shoppingCart.setOnClickListener(this);
		bt_addToShoppingCart.setOnClickListener(this);
		bt_shoppingNow.setOnClickListener(this);
	}
	
	/**视图初始化*/
	private void initView() {
		im_large = (ImageView) findViewById(R.id.im_large);
		tv_phoneName = (TextView) findViewById(R.id.tv_phoneName);
		tv_phoneSystem = (TextView) findViewById(R.id.tv_phoneSystem);
		tv_phonePrice = (TextView) findViewById(R.id.tv_phonePrice);
		tv_phoneTime = (TextView) findViewById(R.id.tv_phoneTime);
		tv_phoneThickness = (TextView) findViewById(R.id.tv_phoneThickness);
		tv_phoneScreenSize = (TextView) findViewById(R.id.tv_phoneScreenSize);
		tv_phoneCPUNum = (TextView) findViewById(R.id.tv_phoneCPUNum);
		tv_phonePixel = (TextView) findViewById(R.id.tv_phonePixel);
		tv_phoneNetSize = (TextView) findViewById(R.id.tv_phoneNetSize);
		tv_phoneDescribe = (TextView) findViewById(R.id.tv_phoneDescribe);
		bt_backList = (Button) findViewById(R.id.bt_backList);
		bt_shoppingNow = (Button) findViewById(R.id.bt_shoppingNow);
		bt_shoppingCart=(Button) findViewById(R.id.bt_shoppingCart);
		bt_addToShoppingCart=(Button) findViewById(R.id.bt_addToShoppingCart);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_backList:
			finish();
			break;
		case R.id.bt_shoppingCart:
			if(!MyApplication.ISLOGIN){
				command();
			}else{
				Intent intent=new Intent(this,ShoppingCartActivity.class);
				startActivity(intent);
			}
			break;
		case R.id.bt_shoppingNow:
			if(!MyApplication.ISLOGIN){
				command();
			}else{
				new AlertDialog.Builder(this)
				.setTitle("提示")
				.setMessage("手机："+tv_phoneName.getText()+"价格："+tv_phonePrice.getText()+"你确定购买？")
				.setNegativeButton("取消",null)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(getApplicationContext(), "购买成功", Toast.LENGTH_SHORT).show();
					}
				})
				.create().show();
			}
			break;
		case R.id.bt_addToShoppingCart:
			if (!MyApplication.ISLOGIN) {
				command();
			}else{
				ShoppingCartDao dao=new ShoppingCartDao(this);
				boolean result=dao.queryByPhoneName(info.getName());
				if (result) {
					Toast.makeText(this, "该商品已在购物车内,请勿重复操作", Toast.LENGTH_SHORT).show();
				}else{
					dao.addToShoppingCart(new ShoppingCartInfo(PersonalFragement.currentUser, info.getName(),info.getUrl(),info.getPrice(), 1,0,0,0));
					Toast.makeText(this, "已加入购物车", Toast.LENGTH_SHORT).show();
				}
			}
			break;
		}
	}
	//从手机详细界面进入登陆的控制
	private void command() {
		Toast.makeText(this, "您还没有登陆！请登陆后进行该操作！", Toast.LENGTH_SHORT).show();
		Intent intent1=new Intent(this,HomeActivity.class);
		intent1.putExtra("needLogin", true);
		startActivity(intent1);
		finish();
		Intent intent2=new Intent(MyApplication.FINISH_HOMEACTIVITY);
		sendBroadcast(intent2);
	}

}
