package com.example.tina;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.model.submit;
import com.example.util.SubmitDBManager;
import com.example.util.TinaApplication;
import com.example.util.UsersDBManager;
import com.example.util.dingnumDBManager;

public class OrderActivity extends Activity {
	private SubmitDBManager submitDBManager1;
	private UsersDBManager usersDBManager1;
	private dingnumDBManager dingnumDBManager;
	private TinaApplication TinaApplication1;
	private ListView listView1;
	private List<submit> list1;
	private ArrayList<Map<String, String>> arrarylist;
	private sadapter simpleAdapter;

	public TextView TextView1;
	public TextView TextView2;
	public TextView TextView3;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		TinaApplication1 = (TinaApplication) getApplication();
		TinaApplication1.getInstance().addActivity(this);
		// 标题栏添加按钮和背景
		getActionBar().setBackgroundDrawable(
				this.getResources().getDrawable(R.drawable.up));
		submitDBManager1 = new SubmitDBManager(this);
		usersDBManager1 = new UsersDBManager(this);
		dingnumDBManager = new dingnumDBManager(this);
		listView1 = (ListView) findViewById(R.id.activity_order_listView1);
		TextView1 = (TextView) findViewById(R.id.activity_order_textView1);
		TextView2 = (TextView) findViewById(R.id.activity_order_textView2);
		TextView3 = (TextView) findViewById(R.id.activity_order_textView3);
		bindsimpleadapter();
	}

	public void bindsimpleadapter() {

		Cursor Cursor = usersDBManager1.getuserinfo(TinaApplication1
				.getusername());
		if (Cursor.moveToNext()) {
			TextView1.setText(Cursor.getString(Cursor
					.getColumnIndex("username")));
			TextView2.setText("QQ："
					+ Cursor.getString(Cursor.getColumnIndex("qq")));
			TextView3.setText("EMAIL："
					+ Cursor.getString(Cursor.getColumnIndex("email")));
			Cursor.moveToFirst();
			Cursor.close();
		}

		list1 = submitDBManager1.query(TinaApplication1.getusername());
		arrarylist = new ArrayList<Map<String, String>>();
		for (submit submit : list1) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("_submitnum", submit.get_submitnum().toString());
			map.put("_username", submit.get_username().toString());
			map.put("_tel", submit.get_tel().toString());
			map.put("_renshu", Integer.valueOf(submit.get_renshu()).toString());
			map.put("_cantingname", submit.get_cantingname().toString());
			map.put("_daodiantime", submit.get_daodiantime().toString());
			map.put("_totalmoney", Double.valueOf(submit.get_totalmoney())
					.toString());
			map.put("_contract", submit.get_contract().toString());
			map.put("_adddate", submit.get_adddate());
			map.put("_fukuan", String.valueOf(submit.get_fukuan()));
			map.put("_queding", String.valueOf(submit.get_queding()));
			arrarylist.add(map);
		}
		simpleAdapter = new sadapter(this, arrarylist, R.layout.item_dmanager,
				null, null);
		listView1.setAdapter(simpleAdapter);

		listView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				LinearLayout LinearLayout1 = (LinearLayout) getLayoutInflater()
						.inflate(R.layout.item_dmanager_view, null);
				TextView textView1 = (TextView) LinearLayout1
						.findViewById(R.id.item_dmanager_view_textView1);
				TextView textView2 = (TextView) LinearLayout1
						.findViewById(R.id.item_dmanager_view_textView2);
				TextView textView3 = (TextView) LinearLayout1
						.findViewById(R.id.item_dmanager_view_textView3);
				TextView textView4 = (TextView) LinearLayout1
						.findViewById(R.id.item_dmanager_view_textView4);
				TextView textView5 = (TextView) LinearLayout1
						.findViewById(R.id.item_dmanager_view_textView5);
				TextView textView6 = (TextView) LinearLayout1
						.findViewById(R.id.item_dmanager_view_textView6);
				TextView textView7 = (TextView) LinearLayout1
						.findViewById(R.id.item_dmanager_view_textView7);
				TextView textView8 = (TextView) LinearLayout1
						.findViewById(R.id.item_dmanager_view_textView8);
				textView1.setText(getResources().getString(
						R.string.item_dmanager_view1)
						+ arrarylist.get(arg2).get("_username").toString());
				textView2.setText(getResources().getString(
						R.string.item_dmanager_view2)
						+ arrarylist.get(arg2).get("_tel").toString());
				textView3.setText(getResources().getString(
						R.string.item_dmanager_view3)
						+ arrarylist.get(arg2).get("_renshu").toString());
				textView4.setText(getResources().getString(
						R.string.item_dmanager_view4)
						+ arrarylist.get(arg2).get("_cantingname").toString());
				textView5.setText(getResources().getString(
						R.string.item_dmanager_view5)
						+ arrarylist.get(arg2).get("_daodiantime").toString());
				textView6.setText(getResources().getString(
						R.string.item_dmanager_view6)
						+ dingnumDBManager.dingnum(
								arrarylist.get(arg2).get("_submitnum")
										.toString(),
								arrarylist.get(arg2).get("_username")
										.toString()));
				textView7.setText(getResources().getString(
						R.string.item_dmanager_view7)
						+ arrarylist.get(arg2).get("_totalmoney").toString());
				textView8.setText(getResources().getString(
						R.string.item_dmanager_view8)
						+ arrarylist.get(arg2).get("_contract").toString());
				new AlertDialog.Builder(OrderActivity.this)
						.setTitle("订单详情")
						.setIcon(
								getResources().getDrawable(
										R.drawable.ic_launcher))
						.setView(LinearLayout1).show();
			}
		});
	}

	public class sadapter extends SimpleAdapter {
		private List<? extends Map<String, ?>> list;
		private LayoutInflater layoutInflater;
		private Context context;

		public sadapter(Context context, List<? extends Map<String, ?>> data,
				int resource, String[] from, int[] to) {
			super(context, data, resource, from, to);
			// TODO Auto-generated constructor stub
			this.list = data;
			this.context = context;
			this.layoutInflater = layoutInflater.from(context);

		}

		public int getCount() {
			return list.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			convertView = layoutInflater.inflate(R.layout.item_dmanager, null);
			TextView textView1 = (TextView) convertView
					.findViewById(R.id.item_dmanager_textView1);
			TextView textView2 = (TextView) convertView
					.findViewById(R.id.item_dmanager_textView2);
			TextView textView3 = (TextView) convertView
					.findViewById(R.id.item_dmanager_textView3);
			TextView textView4 = (TextView) convertView
					.findViewById(R.id.item_dmanager_textView4);
			textView1.setText("就餐人："
					+ list.get(position).get("_username").toString());
			textView2.setText("时间："
					+ list.get(position).get("_adddate").toString());
			if ("true".equals(list.get(position).get("_fukuan").toString())) {
				textView3.setText("已支付");
			} else {
				textView3.setText("未支付");
			}
			if ("true".equals(list.get(position).get("_queding").toString())) {
				textView4.setText("等待确认");
			} else {
				textView4.setText("已取消");
			}
			return convertView;

		}
	}
}
