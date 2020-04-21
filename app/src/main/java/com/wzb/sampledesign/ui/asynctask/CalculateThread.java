package com.wzb.sampledesign.ui.asynctask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.wzb.sampledesign.pojo.jsonwrapper.ConcalWrapper;
import com.wzb.sampledesign.util.Constant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.MediaType;

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

		String url = Constant.zuulbusinessHead+"/calculation_service/concalGeneral";
		Log.e("url",url);
		ConcalWrapper concalWrapper = new ConcalWrapper();
		concalWrapper.setUserID(Constant.loginUser.getId());
		concalWrapper.setProjectName(Constant.PROJECT_NAME);
		concalWrapper.setProjectID(Constant.PROJECTID);

		Message message = new Message();
		Bundle msgBundle = new Bundle();
		message.what = Constant.CALCULATE_MESSAGE;
		// 改用okhttp交互
		OkHttpUtils
				.postString()
				.url(url)
				.content(new Gson().toJson(concalWrapper))
				.mediaType(MediaType.get("application/json; charset=utf-8"))
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
						msgBundle.putString("response",response);
						message.setData(msgBundle);
						handler.sendMessage(message);

					}
				});

	}
}
