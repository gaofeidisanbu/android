package com.gaofei.app.webview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.gaofei.app.R;
import com.gaofei.app.act.ExceptionAct;
import com.gaofei.library.base.BaseAct;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

import org.json.JSONException;
import org.json.JSONObject;


@SuppressLint("SetJavaScriptEnabled") 
public class WebviewActivity extends BaseAct {

    private WebView webView;
    private WVJBWebViewClient webViewClient;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webView=(WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("file:///android_asset/ExampleAppNew.html");
        webViewClient = new MyWebViewClient(webView);
        webViewClient.enableLogging();
        webView.setWebViewClient(webViewClient);
		webView.getSettings().setDomStorageEnabled(true);

        findViewById(R.id.button1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				webViewClient.send("A string sent from ObjC to JS", new WVJBWebViewClient.WVJBResponseCallback() {

					@Override
					public void callback(Object data) {
						Toast.makeText(WebviewActivity.this, "sendMessage got response: " + data, Toast.LENGTH_LONG).show();
						Intent intent = new Intent(WebviewActivity.this,ExceptionAct.class);
						startActivity(intent);
					}
				});
			}

        });

        findViewById(R.id.button2).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					webViewClient.callHandler("testJavascriptHandler", new JSONObject("{\"greetingFromObjC\": \"Hi there, JS!\" }"), new WVJBWebViewClient.WVJBResponseCallback() {

						@Override
						public void callback(Object data) {
							Toast.makeText(WebviewActivity.this, "testJavascriptHandler responded: " + data, Toast.LENGTH_LONG).show();
						}
					});
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
        });

    }

	class MyWebViewClient extends WVJBWebViewClient {
		public MyWebViewClient(WebView webView) {

			// support js send
			super(webView, new WVJBHandler() {

				@Override
				public void request(Object data, WVJBResponseCallback callback) {
					Toast.makeText(WebviewActivity.this, "ObjC Received message from JS:" + data, Toast.LENGTH_LONG).show();
					callback.callback("Response for message from ObjC!");
				}
			});

			/*
			// not support js send
			super(webView);
			*/

			enableLogging();

	        registerHandler("testObjcCallback", new WVJBHandler() {
				
				@Override
				public void request(Object data, WVJBResponseCallback callback) {		
					Toast.makeText(WebviewActivity.this, "testObjcCallback called:" + data, Toast.LENGTH_LONG).show();
					callback.callback("Response from testObjcCallback!");
				}
	        });
	        
	        send("A string sent from ObjC before Webview has loaded.", new WVJBResponseCallback() {

				@Override
				public void callback(Object data) {
					Toast.makeText(WebviewActivity.this, "ObjC got response! :" + data, Toast.LENGTH_LONG).show();
				}
	        });
	        
	        try {
				callHandler("testJavascriptHandler", new JSONObject("{\"foo\":\"before ready\" }"),new WVJBResponseCallback() {

					@Override
					public void callback(Object data) {
						Toast.makeText(WebviewActivity.this, "ObjC call testJavascriptHandler got response! :" + data, Toast.LENGTH_LONG).show();
					}
		        });
			} catch (JSONException e) {
				e.printStackTrace();
			}
	        	        
		}

		@Override
		public void onPageFinished(WebView view, String url) {

			super.onPageFinished(view, url);
		}

		@Override
		public void onReceivedError(WebView webView, int i, String s, String s1) {
			super.onReceivedError(webView, i, s, s1);
			Log.d(TAG,"onReceivedError s =  "+s  +" s1 = "+s1);
		}

		@Override
		public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
			super.onReceivedError(webView, webResourceRequest, webResourceError);
			Log.d(TAG,"url = "+webResourceRequest.getUrl()  +" onReceivedError = "+webResourceError.getErrorCode()+" code = "+webResourceError.getErrorCode());
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return super.shouldOverrideUrlLoading(view, url);
		}

		@Override
		public void onReceivedHttpError(WebView webView, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
			super.onReceivedHttpError(webView, webResourceRequest, webResourceResponse);
			Log.d(TAG,"onReceivedHttpError url = "+webResourceRequest.getUrl() );
		}
	}

}
