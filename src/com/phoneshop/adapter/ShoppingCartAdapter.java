package com.phoneshop.adapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Random;

import com.phoneshop.activity.R;
import com.phoneshop.activity.ShoppingCartActivity;
import com.phoneshop.app.MyApplication;
import com.phoneshop.entity.ShoppingCartInfo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShoppingCartAdapter extends BaseAdapter{
	private List<ShoppingCartInfo> infos;
	private Context context;
	public static int totalPrice;
	public static int totalMoney;
	private String imagePath;
	private int stockNum;
	public ShoppingCartAdapter(Context context,List<ShoppingCartInfo> infos) {
		this.context=context;
		this.infos=infos;
	}
	@Override
	public int getCount() {
		return infos.size();
	}
	@Override
	public Object getItem(int position) {
		return infos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	ViewHolder viewHolder;
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView==null) {
			convertView=LayoutInflater.from(context).inflate(R.layout.shoppingcart_item, null);
			viewHolder=new ViewHolder();
			viewHolder.tv_shopping_phoneName=(TextView) convertView.findViewById(R.id.tv_shopping_phoneName);
			viewHolder.tv_shopping_phonePrice=(TextView) convertView.findViewById(R.id.tv_shopping_phonePrice);
			viewHolder.tv_shopping_surplusNum=(TextView) convertView.findViewById(R.id.tv_shopping_surplusNum);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		final ImageView imageView_shopping_item=(ImageView) convertView.findViewById(R.id.imageView_shopping_item);
		Handler handler=new Handler(){
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case 234:
					imagePath=(String) msg.obj;
					break;
				case 456:
					Bitmap bitmap=(Bitmap) msg.obj;
					imageView_shopping_item.setImageBitmap(bitmap);
					FileOutputStream out=null;
					try {
						out = new FileOutputStream(new File(MyApplication.IMAGE_CACHE_PATH+imagePath));
						bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}finally{
						try {
							out.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					break;
				}
			}
		};
		String imagePath=MyApplication.IMAGE_CACHE_PATH+infos.get(position).getPhoneUrl();
		if (!new File(imagePath).exists()) {
			imageThread(infos.get(position).getPhoneUrl(),handler);
		}else{
			imageView_shopping_item.setImageBitmap(BitmapFactory.decodeFile(imagePath));
		}
		viewHolder.tv_shopping_phoneName.setText(infos.get(position).getPhoneName());
		viewHolder.tv_shopping_phonePrice.setText("价格：￥"+infos.get(position).getPhonePrice()+"");
		stockNum= new Random().nextInt(20)+10;
		infos.get(position).setStockNum(stockNum);
		viewHolder.tv_shopping_surplusNum.setText("剩余库存("+stockNum+"件)");
		final CheckBox checkBox_shopping_item=(CheckBox) convertView.findViewById(R.id.checkBox_shopping_item);
		Button bt_shopping_add=(Button) convertView.findViewById(R.id.bt_shopping_add);
		Button bt_shopping_redu=(Button) convertView.findViewById(R.id.bt_shopping_redu);
		final EditText shoppingNum_editText=(EditText) convertView.findViewById(R.id.shoppingNum_editText);
		shoppingNum_editText.setSelection(1);
		int orderNum = Integer.parseInt(shoppingNum_editText.getText().toString().trim());
		infos.get(position).setOrderNum(orderNum);
		if (infos.get(position).getIsChecked()==1) {
			checkBox_shopping_item.setChecked(true);
		}else{
			checkBox_shopping_item.setChecked(false);
		}
		if (ShoppingCartActivity.allCheck ) {
			totalPrice=0;
//			System.out.println("总商品数： "+infos.size());
			for (int i = 0; i < infos.size(); i++) {
				int currentOrderNum = infos.get(i).getOrderNum();
				int price = currentOrderNum * infos.get(i).getPhonePrice();
				totalPrice += price;
//				System.out.println("商品"+i+"单价："+price);
//				System.out.println("商品总价："+totalPrice);
			}
			Intent intent=new Intent(MyApplication.SHOPPINGCARTACTIVITY);
			intent.putExtra("totalPrice", totalPrice);
			context.sendBroadcast(intent);
		}
		bt_shopping_add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!checkBox_shopping_item.isChecked()) {
					int orderNum = Integer.parseInt(shoppingNum_editText.getText().toString().trim());
					if (orderNum < infos.get(position).getStockNum()) {
						shoppingNum_editText.setText(orderNum+1+"");
						infos.get(position).setOrderNum(orderNum+1);
					}else {
						Toast.makeText(context, "库存量不足", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		bt_shopping_redu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!checkBox_shopping_item.isChecked()) {
					int orderNum = Integer.parseInt(shoppingNum_editText.getText().toString().trim());
					if (orderNum>1) {
						shoppingNum_editText.setText(orderNum-1+"");
						infos.get(position).setOrderNum(orderNum-1);
					}
				}
			}
		});
		
	//单选框点击
	checkBox_shopping_item.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			int orderNum = Integer.parseInt(shoppingNum_editText.getText().toString().trim());
			infos.get(position).setOrderNum(orderNum);
			if (orderNum>0 && orderNum<=infos.get(position).getStockNum()) {
				int price =infos.get(position).getPhonePrice()*orderNum;
				if (checkBox_shopping_item.isChecked()) {
					totalPrice=totalPrice+price;
					infos.get(position).setIsChecked(1);
					int state  = 1;
					((ShoppingCartActivity)context).operate("add",state,position,totalPrice);
				}else{
					infos.get(position).setIsChecked(0);
					int state  = 0;
					totalPrice = totalPrice - price;
					((ShoppingCartActivity)context).operate("dec",state,position,totalPrice);
				}
			}else {
				Toast.makeText(context, "库存量不足", Toast.LENGTH_SHORT).show();
			}
		}
	});
		return convertView;
	}
	private void imageThread(final String imageUrl, final Handler handler) {
		new Thread(){
			public void run() {
				HttpURLConnection conn=null;
				try {
					URL url=new URL(MyApplication.LOCAL_HOST+"images/"+imageUrl);
					conn=(HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(5000);
					if (conn.getResponseCode()==200) {
						Options options=new Options();
						options.inSampleSize=2;
						options.inPreferredConfig=Bitmap.Config.ARGB_4444;
						options.inJustDecodeBounds = false;
						Bitmap bitmap=BitmapFactory.decodeStream(conn.getInputStream());
						handler.obtainMessage(234, imageUrl).sendToTarget();
						handler.obtainMessage(456, bitmap).sendToTarget();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					conn.disconnect();
				}
			};
		}.start();
	}
	class ViewHolder{
		TextView tv_shopping_phoneName,tv_shopping_phonePrice,tv_shopping_surplusNum;
	}
}
