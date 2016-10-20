package com.phoneshop.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private static final String CREAT_TABLE="create table shoppingcart("
			+"id integer primary key autoincrement,"
			+"username varchar,phonename varchar,phoneurl varchar,"
			+"phoneprice integer,phonenumber integer,ischecked integer"
			+")";
	
	public DBHelper(Context context) {
		super(context, "shoppingcart.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREAT_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
