package com.phoneshop.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.phoneshop.db.MyDatabaseHelp;
import com.phoneshop.entity.Address;

public class AddressDao {
	private MyDatabaseHelp databaseHelp;
	public AddressDao(Context context) {
		 databaseHelp=new MyDatabaseHelp(context, "address.db", null, 1);
	}
	
	
	public long add(Address address){
		 SQLiteDatabase db= databaseHelp.getWritableDatabase();
		 ContentValues values=new ContentValues();
		 values.put("username", address.getUsername());
		 values.put("trueName", address.getTrueName());
		 values.put("phone", address.getPhone());
		 values.put("address", address.getAddress());
		 long id= db.insert("address", null, values);
		 db.close();
		return id;
	}
	
	
	
	public void update(Address address){
		 SQLiteDatabase db= databaseHelp.getWritableDatabase();
		 ContentValues values=new ContentValues();
		 values.put("trueName", address.getTrueName());
		 values.put("phone", address.getPhone());
		 values.put("address", address.getAddress());
		 db.update("address", values, "username=?", new String[]{address.getUsername()});
		 db.close();
	}
	
	
	
	/*public Address queryAddress(long id){
		Address address=null;
		SQLiteDatabase db=databaseHelp.getReadableDatabase();
		Cursor cursor=db.rawQuery("select *from address where id=?", new String[]{String.valueOf(id)});
		if (cursor!=null) {
			if (cursor.moveToNext()) {
				String trueNameStr=cursor.getString(cursor.getColumnIndex("trueName"));
				String phoneStr=cursor.getString(cursor.getColumnIndex("phone"));
				String addressStr=cursor.getString(cursor.getColumnIndex("address"));
				address=new Address(trueNameStr, phoneStr, addressStr);
			}
			cursor.close();
		}
		db.close();
		return address;
	}
	*/
	
	
	public Address queryAddress(String username){
		Address address=null;
		SQLiteDatabase db=databaseHelp.getReadableDatabase();
		Cursor cursor=db.rawQuery("select *from address where username=?", new String[]{username+""});
		if (cursor!=null) {
			if (cursor.moveToNext()) {
				String trueNameStr=cursor.getString(cursor.getColumnIndex("trueName"));
				String phoneStr=cursor.getString(cursor.getColumnIndex("phone"));
				String addressStr=cursor.getString(cursor.getColumnIndex("address"));
				address=new Address(username,trueNameStr, phoneStr, addressStr);
			}
			cursor.close();
		}
		db.close();
		return address;
}
	
}
