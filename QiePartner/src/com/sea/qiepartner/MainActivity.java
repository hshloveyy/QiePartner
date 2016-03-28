package com.sea.qiepartner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.tencent.tauth.Tencent;

public class MainActivity extends Activity {

	private static final String APP_ID = "1105287282";
//	private static final String APP_ID = "tencent222222";
	private Tencent mTencent;
	
	//登录手机QQ按钮
	private Button loginQQBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
		// 其中APP_ID是分配给第三方应用的appid，类型为String。
		mTencent = Tencent.createInstance(APP_ID, this.getApplicationContext());
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
		Tencent.onActivityResultData(requestCode, resultCode, data, null);
	}

	/**
	 * 跳转到QQ登录界面
	 */
	public void login() {
		Toast.makeText(this, mTencent.getAppId(), Toast.LENGTH_LONG).show();
		if (!mTencent.isSessionValid()) {
			mTencent.login(this, "all", new MyIUiListener(this));
//			mTencent.checkLogin(new MyIUiListener(this));
		}
	}
}
