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
 * Date: 2019/10/17
 * Author:Satsuki
 * Description:
 */
public class CalculateThread implements Runnable {

	private Handler handler;

	public CalculateThread() {
	}

	public CalculateThread(Handler handler) {
		this.handler = handler;
	}

	@Override
	public void run() {
		Log.e("CalculateThread","1");

		String url = Constant.urlHead+"/calculation_service/conclusion_calculation";
		Log.e("url",url);

		Message message = new Message();
		Bundle msgBundle = new Bundle();
		message.what = Constant.CALCULATE_MESSAGE;
		// 改用okhttp交互
		OkHttpUtils
				.get()
				.url(url)
				.build()
				.execute(new StringCallback() {
					@Override
					public void onError(Call call, Exception e) {
						Log.e("Error",e.toString());
						// 计算失败
						msgBundle.putBoolean("result",false);
						message.setData(msgBundle);
						handler.sendMessage(message);
					}

					@Override
					public void onResponse(String response) {
						Log.e("response",response);

//						message.what = Constant.CALCULATE_MESSAGE;
						msgBundle.putBoolean("result",true);
						message.setData(msgBundle);
						handler.sendMessage(message);

					}
				});

	}
}
