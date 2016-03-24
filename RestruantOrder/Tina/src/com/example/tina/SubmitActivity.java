package com.example.tina;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.model.submit;
import com.example.util.SubmitDBManager;
import com.example.util.TinaApplication;
import com.example.util.dingnumDBManager;
import com.zdp.aseo.content.AseoZdpAseo;

public class SubmitActivity extends Activity {
	// 配置初始化
	public TinaApplication TinaApplication1;
	public SubmitDBManager SubmitDBManager1;
	public dingnumDBManager dingnumDBManager1;
	public submit submit1;
	// 控件初始化
	public EditText EditText1;
	public EditText EditText2;
	public EditText EditText3;
	public Button Button1;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submit);
		// 标题栏添加按钮和背景
		getActionBar().setBackgroundDrawable(
				this.getResources().getDrawable(R.drawable.up));
		// 全局变量
		TinaApplication1 = (TinaApplication) getApplication();
		TinaApplication1.getInstance().addActivity(this);
		dingnumDBManager1 = new dingnumDBManager(this);
		SubmitDBManager1 = new SubmitDBManager(this);
		SubmitDBManager1.createding(TinaApplication1.get_dingdanstring()); // 创建订单
		EditText1 = (EditText) findViewById(R.id.activity_submit_editText1);// 总金额
		EditText2 = (EditText) findViewById(R.id.activity_submit_editText2);// 用餐人数
		EditText3 = (EditText) findViewById(R.id.activity_submit_editText3);// 备注
		Button1 = (Button) findViewById(R.id.activity_submit_button1);// 提交

		EditText1.setText(dingnumDBManager1.gettotalmoney("postdep", "5588"));
		AseoZdpAseo.initType(this, AseoZdpAseo.INSERT_TYPE);;

		Button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(SubmitActivity.this)
						.setTitle("提示")
						.setMessage("确认提交吗?")
						.setPositiveButton("取消", null)
						.setNegativeButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										if ("".equals(TinaApplication1
												.getusername())) {
											Intent Intent1 = new Intent();
											Intent1.setClass(
													SubmitActivity.this,
													LoginActivity.class);
											startActivity(Intent1);
											Toast.makeText(SubmitActivity.this,
													"请先登录!", Toast.LENGTH_SHORT)
													.show();
										} else {
											// 保存本地数据库
											submit1 = new submit(
													SubmitActivity.this);
											submit1.set_submitnum(TinaApplication1
													.get_dingdanstring());
											submit1.set_username(TinaApplication1
													.getusername());
											submit1.set_totalmoney(Double
													.parseDouble(EditText1
															.getText()
															.toString()));
											if ("".equals(EditText2.getText()
													.toString())) {
												submit1.set_renshu(2);
											} else {
												submit1.set_renshu(Integer
														.parseInt(EditText2
																.getText()
																.toString()));
											}
											if ("".equals(EditText2.getText()
													.toString())) {
												submit1.set_contract("无附加条件");
											} else {
												submit1.set_contract(EditText3
														.getText().toString());
											}
											submit1.set_fukuan(false);
											submit1.set_queding(true);
											submit1.set_submitnum(TinaApplication1
													.get_dingdanstring());
											SubmitDBManager1
													.updateding(submit1);
											dingnumDBManager1.update(
													TinaApplication1
															.getusername(),
													TinaApplication1
															.get_dingdanstring());
											Toast.makeText(SubmitActivity.this,
													"下单成功!", Toast.LENGTH_SHORT)
													.show();
											TinaApplication1.set_dingdansring();
											setResult(0x719);
											finish();
										}
									}

								}).show();
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& KeyEvent.KEYCODE_BACK == keyCode) {
			SubmitDBManager1.deleteding(TinaApplication1.get_dingdanstring());
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);

	}
}
