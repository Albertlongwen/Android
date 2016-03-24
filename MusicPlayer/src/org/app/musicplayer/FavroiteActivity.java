package org.app.musicplayer;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FavroiteActivity extends BaseActivity {
	WebView webview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favorite);
		LoadData();
	}

	private void LoadData() {
		webview = (WebView) findViewById(R.id.music_webview);
		webview.loadUrl("http://shop116644118.taobao.com/");
		webview.getSettings().setJavaScriptEnabled(true);
		webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                    view.loadUrl(url);
                    return false;
                    
            }
    });

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (webview.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK) {
			webview.goBack(); // goBack()��ʾ����webView����һҳ��

			return true;
		}
		return false;
	}

}
