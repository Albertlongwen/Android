package com.example.tina;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.model.goods;
import com.example.util.TinaApplication;
import com.example.util.dingDBManager;
import com.example.util.dingnumDBManager;
import com.zdp.aseo.content.AseoZdpAseo;

@SuppressLint("NewApi")
public class ViewActivity extends Activity {
	// 页面配置
	private ProgressDialog ProgressDialog1; // 加载对话框
	private Thread Thread1;
	private TinaApplication TinaApplication1; // 全局应用层
	private Bundle Bundle1; // intent传递值
	private dingDBManager dingDBManager1; // 数据操作
	private List<goods> list1; // 获得数据库全记录的泛型
	public String strwhere = ""; // 查询条件

	// 控件声明
	public TextView TextView1;
	public ImageView ImageView1;
	public Button Button1;

	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view);
		TinaApplication1 = (TinaApplication) getApplication();
		TinaApplication1.getInstance().addActivity(this);// 这个是为了退出时清除所有打开的activity
		// 标题栏运行出现按钮
		getActionBar().setDisplayHomeAsUpEnabled(true);
		// 标题栏添加按钮和背景
		getActionBar().setBackgroundDrawable(
				this.getResources().getDrawable(R.drawable.up));
		Bundle1 = this.getIntent().getExtras();
		AseoZdpAseo.initType(this, AseoZdpAseo.INSERT_TYPE);;

		dingDBManager1 = new dingDBManager(this);	
		strwhere = " id = " + String.valueOf(Bundle1.getInt("id"));
		TextView1 = (TextView) findViewById(R.id.activity_view_textView1);
		ImageView1 = (ImageView) findViewById(R.id.activity_view_imageView1);
		Button1 = (Button) findViewById(R.id.activity_view_button1);
		threadstart();
	}

	/**
	 * 第一次线程加载
	 */
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

	/**
	 * 第一次线程加载事件
	 */
	private Handler Handler1 = new Handler() {
		public void handleMessage(Message msg) {
			try {
				readdata();
				ProgressDialog1.dismiss();
			} catch (Exception e) {
				e.printStackTrace();
				ProgressDialog1.dismiss();
			}
		}
	};

	/**
	 * 加载数据
	 */
	public void readdata() {
		list1 = dingDBManager1.query(0, strwhere);
		for (goods dt_goods1 : list1) {
			int i = Integer.valueOf(dt_goods1.get_img_url());
			switch (i) {
			case 0:
				ImageView1.setImageResource(R.drawable.img0);
				break;
			case 1:
				ImageView1.setImageResource(R.drawable.img1);
				break;
			case 2:
				ImageView1.setImageResource(R.drawable.img2);
				break;
			case 3:
				ImageView1.setImageResource(R.drawable.img3);
				break;
			case 4:
				ImageView1.setImageResource(R.drawable.img4);
				break;
			case 5:
				ImageView1.setImageResource(R.drawable.img5);
				break;
			case 6:
				ImageView1.setImageResource(R.drawable.img6);
				break;
			case 7:
				ImageView1.setImageResource(R.drawable.img7);
				break;
			case 8:
				ImageView1.setImageResource(R.drawable.img8);
				break;
			default:
				ImageView1.setImageResource(R.drawable.img0);
				break;
			}
			TextView1.setText(dt_goods1.get_content());

		}

		if (dingDBManager1.queryid(Bundle1.getInt("id"))) {
			Button1.setText("已点");
			Button1.setEnabled(false);
		} else {
			Button1.setText("点菜");
			Button1.setEnabled(true);

		}
		Button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int id = Bundle1.getInt("id");
				dingDBManager1.add(id, "5588", "postdep");
				Message mes = Handler1.obtainMessage(1);
				Handler1.sendMessage(mes);
			}
		});
	}

	/**
	 * 标题栏上的按钮打开抽屉菜单
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		case R.id.titlebar_item1:
			Intent Intent1 = new Intent();
			Intent1.setClass(ViewActivity.this, MenuActivity.class);
			startActivity(Intent1);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.titlebar, menu);
		return true;
	}
}
