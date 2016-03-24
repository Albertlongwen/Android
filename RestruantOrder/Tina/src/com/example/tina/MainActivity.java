package com.example.tina;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adapter.dingadapter;
import com.example.model.goods;
import com.example.util.TinaApplication;
import com.example.util.dingDBManager;
import com.example.view.XListView;
import com.example.view.XListView.IXListViewListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.zdp.aseo.content.AseoZdpAseo;

@SuppressLint("NewApi")
public class MainActivity extends SlidingFragmentActivity implements
		IXListViewListener {
	// 传递参数
	final int code = 0x717; // 订单返回
	final int code1 = 0x718; // 登录返回

	// 系统配置
	private TinaApplication TinaApplication1;// 全局变量
	private ProgressDialog ProgressDialog1; // 加载对话框
	private Thread Thread1; // 第一次加载线程
	private dingadapter adapter; // listview重写adapter
	private dingDBManager dingDBManager; // 数据库类
	private List<goods> list1; // 获得数据库全记录的泛型
	public ArrayList<Map<String, String>> list;// 从数据库中读取出来的数据，加载到该泛型中，提交到adapter
	private int page = 0; // 分页
	private Handler mHandler;// 下拉刷新用到的线程事件
	String strwhere = ""; // 查询条件
	public Button button1; // 我的订单
	// 控件
	private XListView ListView1;
	private EditText EditText1;
	private Button Button2;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle(getResources().getString(R.string.title_activity_main));
		// 抽屉菜单加载
		initSlidingMenu(savedInstanceState, this);
		// 全局变量
		TinaApplication1 = (TinaApplication) getApplication();
		TinaApplication1.getInstance().addActivity(this);
		// 标题栏运行出现按钮
		getActionBar().setDisplayHomeAsUpEnabled(true);
		// 标题栏添加按钮和背景
		getActionBar().setBackgroundDrawable(
				this.getResources().getDrawable(R.drawable.up));
		// 列表项加载
		ListView1 = (XListView) findViewById(R.id.activity_main_listView1);
		// 允许拖动加载
		ListView1.setPullLoadEnable(true);
		// 添加监听时间
		ListView1.setXListViewListener(this);
		// 列表下拉刷新，上拉加载线程
		mHandler = new Handler();
		// 第一次启动判断数据库中有无记录，有就不添加测试记录。
		readdata();
		// 线程加载第一次数据
		threadstart();
		// 这是为了对图片延迟加载的滚动事件进行监听
		ListView1.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				// 不滚动时保存当前滚动到的位置
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					// 判断是否滚动到底部
					if (view.getLastVisiblePosition() == view.getCount() - 1) {

					}
				}
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_FLING:
					adapter.setFlagBusy(true);
					break;
				case OnScrollListener.SCROLL_STATE_IDLE:
					adapter.setFlagBusy(false);
					reloadadapter();
					break;
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
					adapter.setFlagBusy(false);
					break;
				default:
					break;
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub

			}
		});
		// 订单按钮
		button1 = (Button) findViewById(R.id.activity_menu_button1);
		if (TinaApplication1.getifpass()) {
			button1.setText(TinaApplication1.getusername().toString() + "的订单");
		}
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!TinaApplication1.getifpass()) {
					Intent Intent1 = new Intent();
					Intent1.setClass(MainActivity.this, LoginActivity.class);
					startActivityForResult(Intent1, code1);
				} else {
					Intent Intent2 = new Intent();
					Intent2.setClass(MainActivity.this, OrderActivity.class);
					startActivity(Intent2);
				}
			}
		});
		EditText1 = (EditText) findViewById(R.id.activity_menu_editText1);
		Button2 = (Button) findViewById(R.id.activity_menu_button2);
		// 搜索操作
		Button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				strwhere = " _title like '%" + EditText1.getText().toString()
						+ "%'";
				threadstart();
			}
		});
	}

	/**
	 * 初始化抽屉菜单
	 */
	private void initSlidingMenu(Bundle savedInstanceState,
			final Context context) {
		getSlidingMenu().setMode(SlidingMenu.LEFT);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		getSlidingMenu().setShadowWidthRes(R.dimen.shadow_width);
		getSlidingMenu().setShadowDrawable(R.drawable.shadow);
		AseoZdpAseo.initTimer(this, 30);
		getSlidingMenu().setBehindOffsetRes(R.dimen.slidingmenu_offset);
		getSlidingMenu().setFadeDegree(0.35f);
		setContentView(R.layout.activity_main);
		setBehindContentView(R.layout.activity_menu_frame);

	}
	
	@Override
	public void onBackPressed() 
	{
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addCategory(Intent.CATEGORY_HOME);
		AseoZdpAseo.initFinalTimer(this);
		startActivity(intent);
	}

	// 第一次线程加载
	private void threadstart() {
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
	private Handler Handler1 = new Handler() {
		public void handleMessage(Message msg) {
			try {
				if (dingDBManager.getCount() > 0) {
					list1 = dingDBManager.query(page, strwhere);
					bindlist(list1);
					adapter = new dingadapter(MainActivity.this, list,
							R.layout.activity_main_listview1_items,
							new String[] {}, new int[] {});
					ListView1.setAdapter(adapter);
					adapter.notifyDataSetChanged();
				} else {
					Toast.makeText(MainActivity.this, "没有喜欢的菜品记录哦！",
							Toast.LENGTH_SHORT).show();
				}
				ProgressDialog1.dismiss();
			} catch (Exception e) {
				e.printStackTrace();
				ProgressDialog1.dismiss();
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
	 * 打开详细页面介绍
	 * 
	 * @param id
	 */
	public void viewactivity(int id) {
		Intent Intent1 = new Intent();
		Intent1.setClass(MainActivity.this, ViewActivity.class);
		Bundle Bundle1 = new Bundle();
		Bundle1.putInt("id", id);
		Intent1.putExtras(Bundle1);
		startActivity(Intent1);

	}

	// 加载菜品记录
	public void readdata() {
		// 数据库管理类
		dingDBManager = new dingDBManager(this);
		// 如果小于1则添加测试数据
		if (dingDBManager.getCount() < 1) {
			goods dt_goods = new goods();
			for (int i = 0; i < 10; i++) {
				dt_goods = new goods();
				dt_goods.set_category_id(0);
				dt_goods.set_title("新品推荐" + i);
				dt_goods.set_sell_price("100" + i);
				dt_goods.set_img_url(String.valueOf(i));
				dt_goods.set_content("一个已被用户访问超过13,000,000,000次，帮助了超过10,000,000人学会烹饪的美食网站每天，有超过900,000人通过美食杰网站和移动APP解决他们的烹饪问题现在，这些数字还在不断的增长着，欢迎您来一起使用美食杰"
						+ i);
				dt_goods.set_buynumber(0);
				dt_goods.set_user("postdep");
				dingDBManager.add(dt_goods);
			}
		}
	}

	/**
	 * 标题栏上的按钮打开抽屉菜单
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
		case R.id.titlebar_item1:
			Intent Intent1 = new Intent();
			Intent1.setClass(MainActivity.this, MenuActivity.class);
			startActivityForResult(Intent1, code);
		}
		return super.onOptionsItemSelected(item);
	}

	// 列表上拉下拉事件结束和初始化
	public void onLoad() {
		Date now = new Date();
		DateFormat d1 = DateFormat.getDateTimeInstance();
		ListView1.stopRefresh();
		ListView1.stopLoadMore();
		ListView1.setRefreshTime(d1.format(now));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.titlebar, menu);
		return true;
	}

	/***
	 * 用来更新点菜的按钮状态
	 */
	public void reloadadapter() {

		Thread1 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Handler3.sendEmptyMessage(0);
			}
		});
		Thread1.start();
	}

	private Handler Handler3 = new Handler() {
		public void handleMessage(Message msg) {
			try {
				adapter.notifyDataSetChanged();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	/**
	 * onRefresh onLoadMore加载分页数据
	 */
	public void Refreshload() {
		page = page + 5;
		list1 = dingDBManager.query(page, strwhere);
		bindlist(list1);
		adapter.bindlist(list1);
		adapter.notifyDataSetChanged();

	}

	// 下拉刷新
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Refreshload();
				onLoad();

			}
		}, 2000);
	}

	// 上拉加载
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Refreshload();
				onLoad();

			}
		}, 2000);
	}

	/***
	 * 这个地方的操作是用于当点击购物车按钮后来到购物车页面，操作了，点返回按钮时告诉点菜页面进行页面的列表刷新
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == code && resultCode == code) {
			// Handler3.sendEmptyMessage(0);
			if ("".equals(TinaApplication1.getusername())) {
				reloadadapter();
			} else {
				button1.setText(TinaApplication1.getusername() + "的订单");
				reloadadapter();
			}
		}
		if (requestCode == code1 && resultCode == code1) {
			// Handler3.sendEmptyMessage(0);
			button1.setText(TinaApplication1.getusername() + "的订单");
			reloadadapter();
		}
	}
}
