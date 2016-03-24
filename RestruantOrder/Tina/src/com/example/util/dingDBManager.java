package com.example.util;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.model.goods;

public class dingDBManager {
	private DBhelper helper;
	private SQLiteDatabase db;
	public String strwhere = "";

	public dingDBManager(Context context) {
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
		boolean result = false;
		Cursor c = db
				.rawQuery(
						"select a.* from dingnum a where a.user_name = 'postdep' and a.submitnum = '5588' and a.ding_id = "
								+ sid + " order by id limit 0,1", null);
		try {
			if (c.getCount() > 0) {
				result = true;
			}
		} catch (Exception e) {
			Log.v("msg", e.toString());
		}
		c.moveToFirst();
		c.close();
		return result;
	}

	/**
	 * 添加商品
	 */
	public void add(goods dt_goods) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("insert into dt_goods (_category_id,_title,_sell_price,_img_url,_content,_buynumber,_user) values(");
		stringBuilder.append(dt_goods.get_category_id() + ",");
		stringBuilder.append("'" + dt_goods.get_title() + "',");
		stringBuilder.append("'" + dt_goods.get_sell_price() + "',");
		stringBuilder.append("'" + dt_goods.get_img_url() + "',");
		stringBuilder.append("'" + dt_goods.get_content() + "',");
		stringBuilder.append(dt_goods.get_buynumber() + ",");
		stringBuilder.append("'" + dt_goods.get_user() + "'");
		stringBuilder.append(")");
		db.execSQL(stringBuilder.toString());
	}

	/**
	 * 添加商品
	 */
	public void add(int id, String submitnum, String user_name) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("insert into [dingnum] (ding_id,buynumber,submitnum,user_name,date) values (");
		stringBuilder.append(id + ",");
		stringBuilder.append("1,");
		stringBuilder.append("'" + submitnum + "',");
		stringBuilder.append("'" + user_name + "',");
		stringBuilder.append("datetime('now'))");
		db.execSQL(stringBuilder.toString());
	}

	/*
	 * 查询数据记录
	 */
	public List<goods> query(int spage, String strwhere) {
		ArrayList<goods> dt_goods = new ArrayList<goods>();
		Cursor c = getAllItems(5, spage, strwhere);
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

	// 查询记录的总数
	public long getCount() {
		String sql = "select count(*) from dt_goods";
		Cursor c = db.rawQuery(sql, null);
		c.moveToFirst();
		long length = c.getLong(0);
		c.moveToFirst();
		c.close();
		return length;
	}

	/**
	 * 拿到所有的记录条数
	 * 
	 * @param firstResult
	 *            从第几条数据开始查询。
	 * @param maxResult
	 *            每页显示多少条记录。
	 * @return 当前页的记录
	 */
	@SuppressLint("HandlerLeak")
	public Cursor getAllItems(int firstResult, int maxResult, String strwhere) {
		Cursor mCursor;
		String sql;
		if ("".equals(strwhere)) {
			sql = "select * from dt_goods limit ? offset ?";
		} else {

			sql = "select * from dt_goods where " + strwhere
					+ " limit ? offset ?";
		}
		mCursor = db.rawQuery(sql, new String[] { String.valueOf(firstResult),
				String.valueOf(maxResult) });
		return mCursor;
	}

	/**
	 * 关闭数据库
	 */
	public void dbclose() {
		db.close();
	}
}
