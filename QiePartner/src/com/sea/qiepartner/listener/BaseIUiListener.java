package com.sea.qiepartner.listener;

import org.json.JSONObject;

import android.content.Context;
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
	
	public BaseIUiListener(Context mContext){
		this.mContext = mContext;
	}
	
	@Override
	public void onCancel() {
		Toast.makeText(mContext, "by Cancel", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onComplete(Object arg0) {
		JSONObject json = (JSONObject)arg0;
		
		Log.i(AppConstants.TAG, json.toString());
	}

	@Override
	public void onError(UiError arg0) {
		Toast.makeText(mContext, arg0.errorMessage, Toast.LENGTH_LONG).show();
	}
	
}