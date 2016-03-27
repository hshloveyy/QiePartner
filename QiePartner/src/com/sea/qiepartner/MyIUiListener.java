package com.sea.qiepartner;

import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

/**
 * ����SDK�Ѿ���װ�õĽӿ�ʱ�����磺��¼������֧����¼��Ӧ�÷���Ӧ������Ƚӿڣ��贫��ûص���ʵ����
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