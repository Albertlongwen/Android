package com.example.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.model.goods;

public class dingnumDBManager {
	private DBhelper helper;
	private SQLiteDatabase db;
	public String strwhere = "";

	public dingnumDBManager(Context context) {
		helper = new DBhelper(context);
		db = helper.getWritableDatabase();
	}

	/***
	 * 确定数据库中有没有对应的记录
	 * 
	 * @param id
	 * @return
	 */
	public boolean queryid(int id) {
		int sid = id;
		Cursor c = db
				.rawQuery(
						"SELECT * FROM [dingnum] where user_name = 'postdep' and submitnum = '5588' and ding_id = "
								+ sid, null);
		if (c.getCount() > 0) {
			c.moveToFirst();
			c.close();
			return true;
		}
		c.close();
		return false;
	}

	/**
	 * 根据菜品的id获取当前菜品的购买份数
	 * 
	 * @param id
	 * @return
	 */
	public String getbuynumber(String username, String submitnum,int id) {
		int sid = id;
		String result = "";
		Cursor c = null;
		c = db.rawQuery(
				"select a.buynumber from dingnum a where a.user_name = '"+username+"' and a.submitnum = '"+submitnum+"' and a.ding_id = "
						+ sid + " order by id limit 0,1", null);
		try {
			while (c.moveToNext()) {
				result = c.getString(c.getColumnIndex("buynumber"));
			}
		} catch (Exception e) {
			Log.v("msg", e.toString());
		}
		c.moveToFirst();
		c.close();
		return result;
	}

	/***
	 * 用来更新点菜的份数
	 * 
	 * @param _buynumber
	 * @param _id
	 */
	public void addbuynumber(int _buynumber,String username, String submitnum, int _id) {
		String UPDATE_DATA = "update [dingnum] set buynumber="
				+ _buynumber
				+ " where user_name = '"+username+"' and submitnum = '"+submitnum+"' and ding_id = "
				+ _id;
		db.execSQL(UPDATE_DATA);
	}

	/**
	 * 选择好的菜单删除
	 * 
	 * @param _id
	 */
	public void update(String username, String submitnum) {
		db = helper.getWritableDatabase();
		String sql = "update dingnum set user_name = '" + username
				+ "',submitnum = '" + submitnum + "' where submitnum = '5588'";
		db.execSQL(sql);
		db.close(); // *inp
	}

	/**
	 * 选择好的菜单删除
	 * 
	 * @param _id
	 */
	public void delete(String username, String submitnum, int _id) {
		db = helper.getWritableDatabase();
		String sql = "delete from [dingnum] where user_name = '" + username
				+ "' and submitnum = '" + submitnum + "' ding_id = " + _id;
		db.execSQL(sql);
		db.close(); // *inp
	}

	/**
	 * 添加商品
	 */
	public void insertdingnum(String submitnum, String user_name) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("insert into [dingnum] (ding_id,buynumber,submitnum,user_name,date) select _id,_buynumber,'"
						+ submitnum
						+ "','"
						+ user_name
						+ "',datetime('now') from dt_goods ");
		db.execSQL(stringBuilder.toString());
		db.close();
	}

	/**
	 * 获得总金额
	 * 
	 * @param id
	 * @return
	 */
	public String gettotalmoney(String user_name, String submitnum) {
		String result = "";
		Cursor c = null;
		c = db.rawQuery(
				"select sum(a.buynumber * b.[_sell_price]) as totalmoney from dingnum a left join dt_goods b on b.id = a.ding_id where a.user_name = '"
						+ user_name + "' and a.submitnum = '" + submitnum + "'",
				null);
		try {
			while (c.moveToNext()) {
				result = c.getString(c.getColumnIndex("totalmoney"));
			}
		} catch (Exception e) {
			Log.v("msg", e.toString());
		}
		c.moveToFirst();
		c.close();
		return result;
	}

	public String dingnum(String submitnum, String user_name) {
		StringBuilder stringBuilder = new StringBuilder();
		Cursor cursor;
		stringBuilder
				.append("select count(*) as totalcount from [dingnum] where  submitnum = '"
						+ submitnum + "' and user_name = '" + user_name + "'");
		cursor = db.rawQuery(stringBuilder.toString(), null);
		String dingnum = "";
		if (cursor.moveToNext()) {
			dingnum = cursor.getString(cursor.getColumnIndex("totalcount"));
		}
		cursor.moveToFirst();
		cursor.close();
		return dingnum;
	}

	/*
	 * 根据下单的列表获取下单菜品的相关信息，注意这里是多表查询
	 */
	public List<goods> getDingquery(String user_name, String submitnum) {
		ArrayList<goods> dt_goods = new ArrayList<goods>();
		Cursor c = getDingItems(user_name, submitnum);
		while (c.moveToNext()) {
			goods goods1 = new goods();
			goods1.set_id(c.getInt(c.getColumnIndex("id")));
			goods1.set_channel_id(c.getInt(c.getColumnIndex("_channel_id")));
			goods1.set_category_id(c.getInt(c.getColumnIndex("_category_id")));
			goods1.set_title(c.getString(c.getColumnIndex("_title")));
			goods1.set_goods_no(c.getString(c.getColumnIndex("_goods_no")));
			goods1.set_stock_quantity(c.getInt(c
					.getColumnIndex("_stock_quantity")));
			goods1.set_market_price(c.getString(c
					.getColumnIndex("_market_price")));
			goods1.set_sell_price(c.getString(c.getColumnIndex("_sell_price")));
			goods1.set_point(c.getInt(c.getColumnIndex("_point")));
			goods1.set_link_url(c.getString(c.getColumnIndex("_link_url")));
			goods1.set_img_url(c.getString(c.getColumnIndex("_img_url")));
			goods1.set_seo_title(c.getString(c.getColumnIndex("_seo_title")));
			goods1.set_seo_keywords(c.getString(c
					.getColumnIndex("_seo_keywords")));
			goods1.set_seo_description(c.getString(c
					.getColumnIndex("_seo_description")));
			goods1.set_content(c.getString(c.getColumnIndex("_content")));
			goods1.set_sort_id(c.getInt(c.getColumnIndex("_sort_id")));
			goods1.set_click(c.getInt(c.getColumnIndex("_click")));
			goods1.set_is_msg(c.getInt(c.getColumnIndex("_is_msg")));
			goods1.set_is_top(c.getInt(c.getColumnIndex("_is_top")));
			goods1.set_is_red(c.getInt(c.getColumnIndex("_is_red")));
			goods1.set_is_hot(c.getInt(c.getColumnIndex("_is_hot")));
			goods1.set_is_slide(c.getInt(c.getColumnIndex("_is_slide")));
			goods1.set_is_lock(c.getInt(c.getColumnIndex("_is_lock")));
			goods1.set_user_id(c.getInt(c.getColumnIndex("_user_id")));
			goods1.set_add_time(c.getString(c.getColumnIndex("_add_time")));
			goods1.set_digg_good(c.getInt(c.getColumnIndex("_digg_good")));
			goods1.set_digg_bad(c.getInt(c.getColumnIndex("_digg_bad")));
			goods1.set_buynumber(c.getInt(c.getColumnIndex("_buynumber")));
			goods1.set_user(c.getString(c.getColumnIndex("_user")));
			dt_goods.add(goods1);
		}
		c.moveToFirst();
		c.close();
		return dt_goods;
	}

	/**
	 * 根据下单的列表获取下单菜品的相关信息，注意这里是多表查询
	 * 
	 * @param user_name
	 * @param submitnum
	 * @return
	 */
	public Cursor getDingItems(String user_name, String submitnum) {
		Cursor mCursor;
		String sql = "select b.* from dingnum a inner join dt_goods b on b.[id] = a.ding_id where a.[user_name] = ? and a.[submitnum] = ? ";

		mCursor = db.rawQuery(sql, new String[] { String.valueOf(user_name),
				String.valueOf(submitnum) });
		return mCursor;
	}

	/**
	 * 关闭数据库
	 */
	public void dbclose() {
		db.close();
		helper.close();
	}
}
