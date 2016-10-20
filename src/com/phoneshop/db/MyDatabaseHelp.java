package com.phoneshop.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelp extends SQLiteOpenHelper {
	private static final String CREAT_ADDRESS="create table address("
			+"id integer primary key autoincrement,"
			+"username varchar(15),trueName varchar(15),"
			+"phone varchar(15),address varchar(50)"
			+")";
	
	public MyDatabaseHelp(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREAT_ADDRESS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("alter table address add age int(3)");
	}

}
