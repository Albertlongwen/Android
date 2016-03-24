package com.example.sun;

import net.youmi.android.AdManager;
import net.youmi.android.spot.SplashView;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class SplashSpotActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// ��ʼ���ӿڣ�Ӧ��������ʱ�����
		// ������appId, appSecret, ����ģʽ
		AdManager.getInstance(this).init("76970fcbfa687f4a", "80ca38c53b4fcca0", false);

		// �������ʹ�ÿ�������Ҫȡ��ע������ע�ͣ����ʹ���˿����Ͳ���������Ҫ��
		SpotManager.getInstance(this).loadSplashSpotAds();

		// ���������ֵ��÷�ʽ�������ʹ�����ѡ������һ�ֵ��÷�ʽ��
		// 1.���Զ��廯���ã�
		// �˷�ʽ�ܹ���������ӦһЩӦ�õ����ⳡ������ʹ�á�
		// ������Ҫ��ת��activity
		SplashView splashView = new SplashView(this, YoumiNormalAdsDemo.class);

		// ����Ҳ������Ϊ�ؼ����뵽�����С�
		setContentView(splashView.getSplashView());

		SpotManager.getInstance(this).showSplashSpotAds(this, splashView,
				new SpotDialogListener() {

					@Override
					public void onShowSuccess() {
						Log.i("YoumiAdDemo", "����չʾ�ɹ�");
					}

					@Override
					public void onShowFailed() {
						Log.i("YoumiAdDemo", "����չʾʧ�ܡ�");
					}

					@Override
					public void onSpotClosed() {
						Log.i("YoumiAdDemo", "�����رա�");
					}
				});

		// 2.�򵥵��÷�ʽ
		// ���û������Ҫ�󣬼�ʹ�ô˾伴��ʵ�ֲ�����չʾ
		// SpotManager.getInstance(this).showSplashSpotAds(this,
		// MainActivity.class);

	}

	// ����ؼ��ϴʾ䣬���������ҳ�����޷���ȥԭsdk
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == 10045) {
			Intent intent = new Intent(SplashSpotActivity.this, YoumiNormalAdsDemo.class);
			startActivity(intent);
			finish();
		}
	}

	@Override
	public void onBackPressed() {
		// ȡ�����˼�
	}

	@Override
	protected void onResume() {

		/**
		 * ����Ϊ����
		 */
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		super.onResume();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			// land
		} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			// port
		}
	}

}
