package com.wzb.sampledesign.ui.asynctask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.wzb.sampledesign.util.Constant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import okhttp3.Call;

/**
 * Date: 2019/10/25
 * Author:Satsuki
 * Description:
 */
public class GetAllModelThread implements Runnable {

	private Handler handler;

	public GetAllModelThread() {
	}

	public GetAllModelThread(Handler handler) {
		this.handler = handler;
	}

	@Override
	public void run() {
		Log.e("GetAllModelThread","1");

		String url = Constant.zuulDBHead+"/project_information/selAll";
		Message message = new Message();
		Bundle msgBundle = new Bundle();
		Log.e("url",url);

		OkHttpUtils
				.get()
				.url(url)
				.build()
				.execute(new StringCallback() {
					@Override
					public void onError(Call call, Exception e) {
						Log.e("Exception:",e.toString());
						msgBundle.putString("Exception",e.toString());
						message.setData(msgBundle);
						handler.sendMessage(message);
					}

					@Override
					public void onResponse(String response) {
						Log.e("allModelResponse:",response);
						msgBundle.putString("modelList",response);
						message.setData(msgBundle);
						handler.sendMessage(message);
					}
				});
	}
}
