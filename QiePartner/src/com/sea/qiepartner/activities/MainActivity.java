package com.sea.qiepartner.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.sea.qiepartner.AppConstants;
import com.sea.qiepartner.R;
import com.sea.qiepartner.listener.BaseIUiListener;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

public class MainActivity extends Activity {
	
	private Tencent mTencent;
	
	//登录手机QQ按钮
	private Button loginQQBtn;
	
	private IUiListener uiListener;
	
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				JSONObject response = (JSONObject) msg.obj;
//				 UserInfo info = new UserInfo(this, MainActivity.mQQAuth.getQQToken());
			}else if(msg.what == 1){
				
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
		// 其中APP_ID是分配给第三方应用的appid，类型为String。
		mTencent = Tencent.createInstance(AppConstants.APP_ID, this.getApplicationContext());
		// 1.4版本:此处需新增参数，传入应用程序的全局context，可通过activity的getApplicationContext方法获取
		// 初始化视图
		initViews();
	}

	private void initViews() {
		loginQQBtn = (Button)findViewById(R.id.loginQQ);
		loginQQBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				login();
			}
		});
		
		uiListener = new BaseIUiListener(this, mHandler);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(AppConstants.TAG, "requestCode -> " + requestCode + "\nresultCode -> " + resultCode);
		Log.d(AppConstants.TAG, "data -> " + data.getDataString());
		Tencent.onActivityResultData(requestCode, resultCode, data, uiListener);
		
	    super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 跳转到QQ登录界面
	 */
	public void login() {
		if (!mTencent.isSessionValid()) {
			mTencent.login(this, "all", uiListener);
		}
	}
	
	public void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch(Exception e) {
        }
    }
}
