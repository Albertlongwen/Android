package com.example.tina;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.util.UsersDBManager;
import com.zdp.aseo.content.AseoZdpAseo;

public class RegisterActivity extends Activity {

	public UsersDBManager UsersDBManager1;
	// 控件
	public EditText EditText1;// 用户名
	public EditText EditText2;// 密码
	public EditText EditText3;// qq
	public EditText EditText4;// email
	public Button Button1;// 注册

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		AseoZdpAseo.initType(this, AseoZdpAseo.INSERT_TYPE);;

		UsersDBManager1 = new UsersDBManager(this);
		EditText1 = (EditText) findViewById(R.id.activity_register_editText1);
		EditText2 = (EditText) findViewById(R.id.activity_register_editText2);
		EditText3 = (EditText) findViewById(R.id.activity_register_editText3);
		EditText4 = (EditText) findViewById(R.id.activity_register_editText4);
		Button1 = (Button) findViewById(R.id.activity_register_button1);
		// 注册操作
		Button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String username = EditText1.getText().toString();
				String password = EditText2.getText().toString();
				String qq = EditText3.getText().toString();
				String email = EditText4.getText().toString();
				if (UsersDBManager1.queryuser(username)) {
					Toast.makeText(RegisterActivity.this, "您好像已经注册了",
							Toast.LENGTH_SHORT).show();
				} else {
					UsersDBManager1.createuser(username, password, qq, email);
					Toast.makeText(RegisterActivity.this, "注册成功",
							Toast.LENGTH_SHORT).show();
					UsersDBManager1.dbclose();
					finish();
				}
			}
		});
	}

}
