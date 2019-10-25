package com.wzb.sampledesign.callback;

import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Date: 2019/10/15
 * Author:Satsuki
 * Description:
 */
public abstract class SaveCallBack extends Callback<String> {

	@Override
	public String parseNetworkResponse(Response response) throws Exception {
		String res = response.body().toString();
		return res;
	}

	@Override
	public void onError(Call call, Exception e) {

	}

	@Override
	public void onResponse(String response) {

	}
}
