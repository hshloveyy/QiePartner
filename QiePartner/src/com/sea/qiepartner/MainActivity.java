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
	
	//��¼�ֻ�QQ��ť
	private Button loginQQBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Tencent����SDK����Ҫʵ���࣬�����߿�ͨ��Tencent�������Ѷ���ŵ�OpenAPI��
		// ����APP_ID�Ƿ����������Ӧ�õ�appid������ΪString��
		mTencent = Tencent.createInstance(APP_ID, this.getApplicationContext());
		// 1.4�汾:�˴�����������������Ӧ�ó����ȫ��context����ͨ��activity��getApplicationContext������ȡ
		// ��ʼ����ͼ
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
	 * ��ת��QQ��¼����
	 */
	public void login() {
		Toast.makeText(this, mTencent.getAppId(), Toast.LENGTH_LONG).show();
		if (!mTencent.isSessionValid()) {
			mTencent.login(this, "all", new MyIUiListener(this));
//			mTencent.checkLogin(new MyIUiListener(this));
		}
	}
}
