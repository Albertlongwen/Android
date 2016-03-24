package com.example.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UsersDBManager {
	private DBhelper helper;
	private SQLiteDatabase db;
	public String strwhere = "";

	public UsersDBManager(Context context) {
		helper = new DBhelper(context);
		db = helper.getWritableDatabase();
	}

	/***
	 * 确定数据库中有没有对应的记录
	 * 
	 * @param id
	 * @return
	 */
	public boolean queryuser(String username) {
		String susername = username;
		Cursor c = db.rawQuery("select * from dt_users where username = '"
				+ susername + "'", null);
		if (c.moveToNext()) {
			c.moveToFirst();
			c.close();
			return true;
		}
		c.moveToFirst();
		c.close();
		return false;
	}

	/***
	 * 确定数据库中有没有对应的记录
	 * 
	 * @param id
	 * @return
	 */
	public boolean loginuser(String username, String password) {
		Cursor c = db.rawQuery("select * from dt_users where username = '"
				+ username + "' and password = '" + password + "'", null);
		if (c.moveToNext()) {
			c.moveToFirst();
			c.close();
			return true;
		}
		c.moveToFirst();
		c.close();
		return false;
	}

	/**
	 * 新建用户
	 * 
	 * @param dingdanString
	 */
	public void createuser(String username, String password, String qq,
			String email) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("insert into [dt_users] (username,password,qq,email) values('"
						+ username
						+ "','"
						+ password
						+ "','"
						+ qq
						+ "','"
						+ email + "')");
		db.execSQL(stringBuilder.toString());

	}

	/***
	 * 首次登陆时同时也是为了保持登录状态
	 * 
	 * @param username
	 */
	public void loginpass(String username,int pass) {
		String sqlstr = "update dt_users set pass = "+ pass +" where username = '"+ username +"'";
		db.execSQL(sqlstr);
		db.close();
	}

	/**
	 * 返回用户名
	 * 
	 * @return
	 */
	public String username() {
		Cursor cursor;
		String username = null;
		cursor = db.rawQuery("select * from dt_users", null);
		if (cursor.moveToNext()) {
			username = cursor.getString(cursor.getColumnIndex("username"));
			cursor.close();
		}
		cursor.close();
		return username.trim();
	}

	/**
	 * 是否已经登录
	 * 
	 * @return
	 */
	public boolean ifpass(String username) {
		Cursor cursor;
		cursor = db.rawQuery("select pass from dt_users where username = '"
				+ username + "'", null);
		if (cursor.moveToNext()) {
			if ("1".equals(cursor.getString(cursor.getColumnIndex("pass")))) {
				cursor.close();
				return true;
			}
		} else {
			cursor.close();
			return false;
		}
		cursor.close();
		return false;
	}
	
	/**
	 * 返回指针自定义得到用户的信息
	 * @param username
	 * @return
	 */
	public Cursor getuserinfo(String username){
		Cursor cursor = db.rawQuery("select * from dt_users", null);
		return cursor;
		
	}

	/**
	 * 关闭数据库
	 */
	public void dbclose() {
		db.close();
		helper.close();
	}
}
