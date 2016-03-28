package com.sea.qiepartner.listener;

import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.sea.qiepartner.AppConstants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

/**
 * 调用SDK已经封装好的接口时，例如：登录、快速支付登录、应用分享、应用邀请等接口，需传入该回调的实例。
 * @author heshaohua
 *
 */
public class BaseIUiListener implements IUiListener {

	private Context mContext;
	private Handler mHandler;
	
	public BaseIUiListener(Context mContext, Handler mHandler){
		this.mContext = mContext;
		this.mHandler = mHandler;
	}
	
	@Override
	public void onCancel() {
		Toast.makeText(mContext, "by Cancel", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onComplete(Object arg0) {
		Message msg = new Message();
		msg.what = 0;
		msg.obj = arg0;
		
		mHandler.sendMessage(msg);
		Log.i(AppConstants.TAG, arg0.toString());
	}

	@Override
	public void onError(UiError arg0) {
		Toast.makeText(mContext, arg0.errorMessage, Toast.LENGTH_LONG).show();
	}
	
}