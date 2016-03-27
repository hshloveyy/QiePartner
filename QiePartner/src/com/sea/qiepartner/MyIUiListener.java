package com.sea.qiepartner;

import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

/**
 * 调用SDK已经封装好的接口时，例如：登录、快速支付登录、应用分享、应用邀请等接口，需传入该回调的实例。
 * @author heshaohua
 *
 */
class MyIUiListener implements IUiListener {

	private Context mContext;
	
	public MyIUiListener(Context mContext){
		this.mContext = mContext;
	}
	
	@Override
	public void onCancel() {
		Toast.makeText(mContext, "by Cancel", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onComplete(Object arg0) {
		JSONObject json = (JSONObject)arg0;
		Toast.makeText(mContext, json.toString(), Toast.LENGTH_LONG).show();
	}

	@Override
	public void onError(UiError arg0) {
		Toast.makeText(mContext, arg0.errorMessage, Toast.LENGTH_LONG).show();
	}
	
}