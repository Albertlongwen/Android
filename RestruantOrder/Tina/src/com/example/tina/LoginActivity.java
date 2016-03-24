package com.example.tina;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.util.TinaApplication;
import com.example.util.UsersDBManager;
import com.zdp.aseo.content.AseoZdpAseo;

public class LoginActivity extends Activity {
	// 页面初始化
	public UsersDBManager UsersDBManager1;
	public TinaApplication TinaApplication1;
	// 控件
	private EditText EditText1;
	private EditText EditText2;
	private Button Button1;
	private Button Button2;

	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 标题栏添加按钮和背景
		getActionBar().setBackgroundDrawable(
				this.getResources().getDrawable(R.drawable.up));
		setContentView(R.layout.activity_login);
		UsersDBManager1 = new UsersDBManager(this);
		AseoZdpAseo.initType(this, AseoZdpAseo.INSERT_TYPE);
		// 全局变量
		TinaApplication1 = (TinaApplication) getApplication();
		TinaApplication1.getInstance().addActivity(this);
		EditText1 = (EditText) findViewById(R.id.activity_login_editText1);
		EditText2 = (EditText) findViewById(R.id.activity_login_editText2);
		Button1 = (Button) findViewById(R.id.activity_login_button1);
		Button2 = (Button) findViewById(R.id.activity_login_button2);
		// 登录
		Button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String susername = EditText1.getText().toString();
				String spassword = EditText2.getText().toString();

				if (UsersDBManager1.queryuser(susername)) {
					if (UsersDBManager1.loginuser(susername, spassword)) {
						UsersDBManager1.loginpass(susername, 1);
						TinaApplication1.setifpass(true);//全局变量已经登录
						TinaApplication1.setusername(susername);//设置当前用户名
						setResult(0x718);
						Toast.makeText(LoginActivity.this, susername + "欢迎回来！",
								Toast.LENGTH_SHORT).show();
						finish();
					} else {
						Toast.makeText(LoginActivity.this, "用户名或密码错误！",
								Toast.LENGTH_SHORT).show();
					}
				} else {

					Toast.makeText(LoginActivity.this, "没有当前帐号，请注册！",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		// 注册
		Button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent Intent1 = new Intent();
				Intent1.setClass(LoginActivity.this, RegisterActivity.class);
				startActivity(Intent1);
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& KeyEvent.KEYCODE_BACK == keyCode) {
			UsersDBManager1.dbclose();
			// setResult(0x717);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);

	}

}
