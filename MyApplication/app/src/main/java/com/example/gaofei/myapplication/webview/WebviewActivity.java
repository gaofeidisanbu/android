package com.example.gaofei.myapplication.webview;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.gaofei.myapplication.BaseAct;
import com.example.gaofei.myapplication.R;
import com.example.gaofei.myapplication.act.ExceptionAct;


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
        webView.loadUrl("file:///android_asset/ExampleApp.html");

        webViewClient = new MyWebViewClient(webView);
        webViewClient.enableLogging();        
        webView.setWebViewClient(webViewClient);

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
			super(webView, new WVJBWebViewClient.WVJBHandler() {
			
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
			
	        registerHandler("testObjcCallback", new WVJBWebViewClient.WVJBHandler() {
				
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
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return super.shouldOverrideUrlLoading(view, url);
		}

	}

}
