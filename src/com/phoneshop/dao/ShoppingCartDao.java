package com.phoneshop.dao;

import java.util.ArrayList;
import java.util.List;

import com.phoneshop.db.DBHelper;
import com.phoneshop.entity.ShoppingCartInfo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ShoppingCartDao {
	private Context context;
	private DBHelper dbHelper; 
	public ShoppingCartDao(Context context) {
		this.context=context;
		dbHelper=new DBHelper(context);
	}
	public void addToShoppingCart(ShoppingCartInfo info){
		SQLiteDatabase db=dbHelper.getWritableDatabase();
		db.execSQL("insert into shoppingcart(username,phonename,phoneurl,phoneprice,phonenumber,ischecked) values(?,?,?,?,?,?)",
				new Object[]{info.getUsername(),info.getPhoneName(),info.getPhoneUrl(),info.getPhonePrice(),info.getPhoneNumber(),info.getIsChecked()});
		db.close();
	}
	public List<ShoppingCartInfo> queryByUserName(String username){
		List<ShoppingCartInfo> infos=new ArrayList<ShoppingCartInfo>();
		SQLiteDatabase db=dbHelper.getReadableDatabase();
		Cursor cursor=db.query("shoppingcart", null,"username=?", new String[]{username}, null, null, null);
		ShoppingCartInfo info=null;
		while(cursor.moveToNext()){
			String phoneName=cursor.getString(cursor.getColumnIndex("phonename"));
			String phoneUrl=cursor.getString(cursor.getColumnIndex("phoneurl"));
			int phonePrice=cursor.getInt(cursor.getColumnIndex("phoneprice"));
			int phoneNumber=cursor.getInt(cursor.getColumnIndex("phonenumber"));
			int isChecked=cursor.getInt(cursor.getColumnIndex("ischecked"));
			info=new ShoppingCartInfo(username, phoneName,phoneUrl,phonePrice, phoneNumber,isChecked,0,1);
			infos.add(info);
		}
		cursor.close();
		db.close();
		return infos;
	}
	public boolean queryByPhoneName(String phoneName){
		SQLiteDatabase db=dbHelper.getReadableDatabase();
		Cursor cursor=db.query("shoppingcart", null,"phonename=?", new String[]{phoneName}, null, null, null);
		boolean result=cursor.moveToNext();
		cursor.close();
		db.close();
		return result;
	}
	
	public void removeOut(String phongName) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		db.delete("shoppingcart", "phonename=?", new String[]{phongName});
		db.close();
	}
}
