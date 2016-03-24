package com.example.sun;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.banner.AdViewListener;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class YoumiNormalAdsDemo extends Activity {

	private Button exitButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ad);
		exitButton = (Button) findViewById(R.id.exit);
		exitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(YoumiNormalAdsDemo.this, MainActivity.class);
				startActivity(intent);
				
			}
		});
		
		
		// ��ʼ���ӿڣ�Ӧ��������ʱ�����,ֻ��Ҫ����һ�Σ���Ϊ�Ѿ��ڿ����г�ʼ�������Դ˴������ٳ�ʼ�������û��ʹ�ÿ�������ǵý���ʼ���������롣
		// ������appId, appSecret, ����ģʽ
		// AdManager.getInstance(this).init("85aa56a59eac8b3d",
		// "a14006f66f58d5d7", false);

		// �岥�ӿڵ���
		// �����߿��Ե������ߺ�̨����չʾƵ�ʣ���Ҫ�������ߺ�̨����ҳ�棨��ϸ��Ϣ->ҵ����Ϣ->�޻��ֹ��ҵ��->�߼����ã�
		// ��4.03�汾�����ƿ����Ƿ�������㹦�ܣ���Ҫ�������ߺ�̨����ҳ�棨��ϸ��Ϣ->ҵ����Ϣ->�޻��ֹ��ҵ��->�߼����ã�

		// ���ز岥��Դ
		SpotManager.getInstance(this).loadSpotAds();
		// �������ֶ���Ч����0:ANIM_NONEΪ�޶�����1:ANIM_SIMPLEΪ�򵥶���Ч����2:ANIM_ADVANCEΪ�߼�����Ч��
		SpotManager.getInstance(this).setAnimationType(SpotManager.ANIM_ADVANCE);
		// ���ò��������ĺ�����չʾ��ʽ����������˺����������й����Դ������»�������ʹ�ú���ͼ��
		SpotManager.getInstance(this).setSpotOrientation(
				SpotManager.ORIENTATION_PORTRAIT);
		Button spotBtn = (Button) findViewById(R.id.showSpot);
		spotBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// չʾ�岥��棬���Բ�����loadSpot����ʹ��
				SpotManager.getInstance(YoumiNormalAdsDemo.this).showSpotAds(
						YoumiNormalAdsDemo.this, new SpotDialogListener() {
							@Override
							public void onShowSuccess() {
								Log.i("YoumiAdDemo", "չʾ�ɹ�");
							}

							@Override
							public void onShowFailed() {
								Log.i("YoumiAdDemo", "չʾʧ��");
							}

							@Override
							public void onSpotClosed() {
								Log.i("YoumiAdDemo", "չʾ�ر�");
							}

						}); // //

			}
		});

		Button bannerBtn = (Button) findViewById(R.id.showBanner);
		bannerBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(YoumiNormalAdsDemo.this, "���ڼ���banner��,���Ե�", Toast.LENGTH_SHORT).show();
				showBanner();
			}
		});

	}

	private void showBanner() {

		// ������ӿڵ��ã�������Ӧ�ã�
		// �������adView��ӵ���Ҫչʾ��layout�ؼ���
		// LinearLayout adLayout = (LinearLayout) findViewById(R.id.adLayout);
		// AdView adView = new AdView(this, AdSize.FIT_SCREEN);
		// adLayout.addView(adView);

		// ������ӿڵ��ã���������Ϸ��

		// ʵ����LayoutParams(��Ҫ)
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		// ���ù����������λ��
		layoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT; // ����ʾ��Ϊ���½�
		// ʵ���������
		AdView adView = new AdView(this, AdSize.FIT_SCREEN);
		// ����Activity��addContentView����

		// ����������ӿ�
		adView.setAdListener(new AdViewListener() {

			@Override
			public void onSwitchedAd(AdView arg0) {
				Log.i("YoumiAdDemo", "������л�");
			}

			@Override
			public void onReceivedAd(AdView arg0) {
				Log.i("YoumiAdDemo", "������ɹ�");

			}

			@Override
			public void onFailedToReceivedAd(AdView arg0) {
				Log.i("YoumiAdDemo", "������ʧ��");
			}
		});
		this.addContentView(adView, layoutParams);
	}

	@Override
	public void onBackPressed() {
		// �������Ҫ�����Ե�����˹رղ岥��档
		if (!SpotManager.getInstance(this).disMiss()) {
			// �����˳����ڣ�����ʹ���Զ������������ͻ��˶���,����demo,����ʹ�ö���������-1
			super.onBackPressed();
		}
	}

	@Override
	protected void onStop() {
		// ��������ô˷�������home����ʱ������ͼ���޷���ʾ�������
		SpotManager.getInstance(this).onStop();
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		SpotManager.getInstance(this).onDestroy();
		super.onDestroy();
	}

}
