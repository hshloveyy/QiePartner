package com.sea.qiepartner.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sea.qiepartner.AppConstants;
import com.sea.qiepartner.R;
import com.sea.qiepartner.listener.BaseIUiListener;
import com.sea.qiepartner.utils.Util;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class MainActivity extends Activity {
	
	private Tencent mTencent;
	private UserInfo mInfo;
	
	//登录手机QQ按钮
	private Button loginQQBtn;
	private Button joinGroup;
	private TextView nickNameTxt;
	private ImageView userImage;
	
	private IUiListener uiListener;
	
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			try {
				if (msg.what == 0) {//显示用户信息
					JSONObject info = (JSONObject)msg.obj;
					nickNameTxt.setVisibility(View.VISIBLE);
					nickNameTxt.setText(info.getString("nickname"));
				}else if(msg.what == 1){//显示用户头像
					Bitmap bitmap = (Bitmap) msg.obj;
					userImage.setVisibility(View.VISIBLE);
					userImage.setImageBitmap(bitmap);
				}
			} catch (JSONException e) {
				e.printStackTrace();
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
		
		nickNameTxt = (TextView)findViewById(R.id.nickNameTxt);
		userImage = (ImageView)findViewById(R.id.userImage);
		
		joinGroup = (Button)findViewById(R.id.joinGroup);
		joinGroup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				joinQQGroup("Y70u2PkcjVNu9xESwL18_h0bYVsfBCNo");
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
			mTencent.login(this, "all", new IUiListener() {
				
				@Override
				public void onCancel() {
					Toast.makeText(MainActivity.this, "by Cancel", Toast.LENGTH_LONG).show();
				}

				@Override
				public void onComplete(Object arg0) {
					initOpenidAndToken((JSONObject)arg0);
					updateUserInfo();
				}

				@Override
				public void onError(UiError arg0) {
					Toast.makeText(MainActivity.this, arg0.errorMessage, Toast.LENGTH_LONG).show();
				}
			});
		}
	}
	
	private void updateUserInfo() {
		if (mTencent != null && mTencent.isSessionValid()) {
			mInfo = new UserInfo(this, mTencent.getQQToken());
			mInfo.getUserInfo(new IUiListener() {

				@Override
				public void onError(UiError e) {

				}

				@Override
				public void onComplete(final Object response) {
					Message msg = new Message();
					msg.obj = response;
					msg.what = 0;
					mHandler.sendMessage(msg);
					new Thread(){

						@Override
						public void run() {
							JSONObject json = (JSONObject)response;
							if(json.has("figureurl")){
								Bitmap bitmap = null;
								try {
									bitmap = Util.getbitmap(json.getString("figureurl_qq_2"));
								} catch (JSONException e) {

								}
								Message msg = new Message();
								msg.obj = bitmap;
								msg.what = 1;
								mHandler.sendMessage(msg);
							}
						}

					}.start();
				}

				@Override
				public void onCancel() {

				}
			});

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
	
	/****************
	*
	* 发起添加群流程。群号：Swift殿堂(389267899) 的 key 为： Y70u2PkcjVNu9xESwL18_h0bYVsfBCNo
	* 调用 joinQQGroup(Y70u2PkcjVNu9xESwL18_h0bYVsfBCNo) 即可发起手Q客户端申请加群 Swift殿堂(389267899)
	*
	* @param key 由官网生成的key
	* @return 返回true表示呼起手Q成功，返回fals表示呼起失败
	******************/
	public boolean joinQQGroup(String key) {
	    Intent intent = new Intent();
	    intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
	   // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
	    try {
	        startActivity(intent);
	        return true;
	    } catch (Exception e) {
	        // 未安装手Q或安装的版本不支持
	        return false;
	    }
	}

}
