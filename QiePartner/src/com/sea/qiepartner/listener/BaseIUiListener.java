package com.sea.qiepartner.listener;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.sea.qiepartner.AppConstants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

/**
 * ����SDK�Ѿ���װ�õĽӿ�ʱ�����磺��¼������֧����¼��Ӧ�÷���Ӧ������Ƚӿڣ��贫��ûص���ʵ����
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