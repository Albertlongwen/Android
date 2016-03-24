/**
 * 全局application，为什么要用application，主要是因为这就像一个session，
 * 用于临时保存各种传值，服务器url，如果用数据库或者其他的操作来保存这些
 * 数据的话管理起来就很繁琐，而且也不利于程序的运行速度。
 * 
 */
package com.example.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.os.StrictMode;

public class TinaApplication extends Application {
	private SubmitDBManager submitDBManager1;

	/**
	 * 为了完全退出程序调用方法 myapplication1.getInstance().addActivity(this);
	 * myapplication1.getInstance().exit();
	 */
	private static TinaApplication instance;

	private List<Activity> activityList = new LinkedList<Activity>();

	public TinaApplication() {
	}

	// 单例模式获取唯一的MyApplication实例
	public static TinaApplication getInstance() {
		if (null == instance) {
			instance = new TinaApplication();
		}
		return instance;
	}

	// 添加Activity到容器中
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	// 遍历所有Activity并finish
	public void exit() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		System.exit(0);
	}

	/***
	 * 当前登录用户
	 */
	private String username = "";

	public String getusername() {
		return username;
	}

	public void setusername(String username) {
		this.username = username;
	}

	/***
	 * 登录
	 */
	private boolean ifpass = false;

	public boolean getifpass() {
		return ifpass;
	}

	public void setifpass(boolean ifpass) {
		this.ifpass = ifpass;
	}

	/***
	 * 总金额
	 */
	private String totalmoney = "0";

	public String gettotalmoney() {
		return totalmoney;
	}

	public void settotalmoney(String totalmoney) {
		this.totalmoney = totalmoney;
	}

	/***
	 * 订餐总数
	 */
	private String totalgoods = "0";

	public String gettotalgoods() {
		return totalgoods;
	}

	public void settotalgoods(String totalgoods) {
		this.totalgoods = totalgoods;
	}

	/**
	 * 全局订单设置
	 */
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat format=new SimpleDateFormat("yyyyMMddhhmmss");
	 String nowTime=format.format(new Date());

	private String dingdanString = "dd" + nowTime;

	public void set_dingdansring() {
		nowTime=format.format(new Date());
		this.dingdanString = "dd" + nowTime;
	}

	public String get_dingdanstring() {
		return dingdanString;
	}

	/**
	 * 下订单,当在购物车中点确认订单时，就新建一个基本订单，当单击确认预定时做更新操作
	 * 
	 * @param dingdanString
	 */
	public void createding(String dingdanString) {
		submitDBManager1.createding(dingdanString);
	}

	/**
	 * 删除订单
	 * 
	 * @param dingdanString
	 */
	public void deleteding(String dingdanString) {
		submitDBManager1.deleteding(dingdanString);
	}

	@SuppressLint("NewApi")
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub

		super.onCreate();
		submitDBManager1 = new SubmitDBManager(this);
		// 初始化全局变量
		try {
			/**
			 * 添加网络权限，安卓4.03必须
			 */
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectDiskReads().detectDiskWrites().detectNetwork()
					.penaltyLog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
					.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
					.penaltyLog().penaltyDeath().build());
			/**
			 * 添加网络权限，安卓4.03必须
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
