package com.phoneshop.app;

import android.os.Environment;

public class MyApplication{
	//服务端ip地址
	public static final String LOCAL_HOST="http://192.168.1.199:80/";
	//本地缓存地址
	public static final String IMAGE_CACHE_PATH=Environment.getExternalStorageDirectory().getAbsolutePath()+"/imagecache/";
	//判断用户是否登陆
	public static boolean ISLOGIN=false;
	public static final String FINISH_HOMEACTIVITY="FINISH_HOMEACTIVITY";
	public static final String SHOPPINGCARTACTIVITY="SHOPPINGCARTACTIVITY";
}
