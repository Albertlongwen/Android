package com.example.tina;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adapter.menuadapter;
import com.example.model.goods;
import com.example.util.TinaApplication;
import com.example.util.dingnumDBManager;
import com.zdp.aseo.content.AseoZdpAseo;

public class MenuActivity extends Activity {
	// 页面配置
	final int code = 0x719;
	private ProgressDialog ProgressDialog1; // 加载对话框
	private Thread Thread1;
	private TinaApplication TinaApplication1; // 全局应用层
	private dingnumDBManager dingnumDBManager1;
	private menuadapter adapter; // listview重写adapter
	private List<goods> list1; // 获得数据库全记录的泛型
	private ArrayList<Map<String, String>> list;// 从数据库中读取出来的数据，加载到该泛型中，提交到adapter

	// 控件
	private ListView ListView1;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		TinaApplication1 = (TinaApplication) getApplication();
		TinaApplication1.getInstance().addActivity(this);// 这个是为了退出时清除所有打开的activity
		// 标题栏运行出现按钮
		getActionBar().setDisplayHomeAsUpEnabled(true);
		// 标题栏添加按钮和背景
		AseoZdpAseo.initType(this, AseoZdpAseo.INSERT_TYPE);
		getActionBar().setBackgroundDrawable(
				this.getResources().getDrawable(R.drawable.up));
		dingnumDBManager1 = new dingnumDBManager(this);
		ListView1 = (ListView) findViewById(R.id.activity_menu_listView1);
		threadstart();
	}

	// 第一次线程加载
	public void threadstart() {
		ProgressDialog1 = new ProgressDialog(this);
		ProgressDialog1.setMessage("数据加载中，请稍后...");
		ProgressDialog1.show();
		Thread1 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread1.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Handler1.sendEmptyMessage(0);
			}
		});
		Thread1.start();
	}

	// 第一次线程加载事件
	public Handler Handler1 = new Handler() {
		public void handleMessage(Message msg) {
			try {
				list1 = dingnumDBManager1.getDingquery("postdep", "5588");
				bindlist(list1);
				adapter = new menuadapter(MenuActivity.this, list,
						R.layout.activity_menu_listview1_items,
						new String[] {}, new int[] {});
				ListView1.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				ProgressDialog1.dismiss();
			} catch (Exception e) {
				e.printStackTrace();
				ProgressDialog1.dismiss();
				Toast.makeText(MenuActivity.this, "网络不给力，无法获得菜品介绍。", 1).show();
			}
		}
	};

	// 添加修改线程事件
	public Handler Handler2 = new Handler() {
		public void handleMessage(Message msg) {
			try {
				adapter.notifyDataSetChanged();
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(MenuActivity.this, "网络不给力，无法获得菜品介绍。", 1).show();
			}
		}
	};

	// 转化数据库中的字段，用于提交给adapter
	public void bindlist(List<goods> goods) {
		list = new ArrayList<Map<String, String>>();
		for (goods dt_goods1 : goods) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("_id", String.valueOf(dt_goods1.get_id()));
			map.put("_channel_id", String.valueOf(dt_goods1.get_channel_id()));
			map.put("_category_id", String.valueOf(dt_goods1.get_category_id()));
			map.put("_title", dt_goods1.get_title());
			map.put("_goods_no", dt_goods1.get_goods_no());
			map.put("_stock_quantity",
					String.valueOf(dt_goods1.get_stock_quantity()));
			map.put("_market_price", dt_goods1.get_market_price());
			map.put("_sell_price", dt_goods1.get_sell_price());
			map.put("_point", String.valueOf(dt_goods1.get_point()));
			map.put("_link_url", dt_goods1.get_link_url());
			map.put("_img_url", dt_goods1.get_img_url());
			map.put("_seo_title", dt_goods1.get_seo_title());
			map.put("_seo_keywords", dt_goods1.get_seo_keywords());
			map.put("_seo_description", dt_goods1.get_seo_description());
			map.put("_content", dt_goods1.get_content());
			map.put("_sort_id", String.valueOf(dt_goods1.get_sort_id()));
			map.put("_click", String.valueOf(dt_goods1.get_click()));
			map.put("_is_msg", String.valueOf(dt_goods1.get_is_msg()));
			map.put("_is_top", String.valueOf(dt_goods1.get_is_top()));
			map.put("_is_red", String.valueOf(dt_goods1.get_is_red()));
			map.put("_is_hot", String.valueOf(dt_goods1.get_is_hot()));
			map.put("_is_slide", String.valueOf(dt_goods1.get_is_slide()));
			map.put("_is_lock", String.valueOf(dt_goods1.get_is_lock()));
			map.put("_user_id", String.valueOf(dt_goods1.get_user_id()));
			map.put("_add_time", String.valueOf(dt_goods1.get_add_time()));
			map.put("_digg_good", String.valueOf(dt_goods1.get_digg_good()));
			map.put("_digg_bad", String.valueOf(dt_goods1.get_digg_bad()));
			map.put("_buynumber", String.valueOf(dt_goods1.get_buynumber()));
			map.put("_user", dt_goods1.get_user());
			list.add(map);
		}

	}

	/**
	 * 标题栏上的按钮打开抽屉菜单
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			dingnumDBManager1.dbclose();
			setResult(0x717);
			finish();
			return true;
		case R.id.menu_titlebar_item1:
			if (ListView1.getCount() >= 1) {
				Intent Intent1 = new Intent();
				Intent1.setClass(MenuActivity.this, SubmitActivity.class);
				startActivityForResult(Intent1, code);
			} else {
				Toast.makeText(MenuActivity.this, "您还没有点餐哦!", 1).show();
			}
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_titlebar, menu);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& KeyEvent.KEYCODE_BACK == keyCode) {
			dingnumDBManager1.dbclose();
			setResult(0x717);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);

	}

	/***
	 * 这个地方的操作是用于当点击购物车按钮后来到购物车页面，操作了，点返回按钮时告诉点菜页面进行页面的列表刷新
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == code && resultCode == code) {
			// Handler3.sendEmptyMessage(0);
			setResult(0x717);
			finish();
		}

	}

}
