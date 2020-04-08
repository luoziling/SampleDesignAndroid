package com.wzb.sampledesign.ui.expertentry.asynctask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.wzb.sampledesign.util.Constant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Date: 2019/10/26
 * Author:Satsuki
 * Description:
 */
public class CreateModelThread implements Runnable {

	private Handler handler;
	private String projectName;

	public CreateModelThread() {
	}

	public CreateModelThread(Handler handler, String projectName) {
		this.handler = handler;
		this.projectName = projectName;
	}

	@Override
	public void run() {
		String url = Constant.urlHead1 + "/project_information/project_creation";

		Message message = new Message();
		Bundle msgBundle = new Bundle();

		message.what = Constant.CREATE_MODEL;

		OkHttpUtils
				.postString()
				.url(url)
				.content(new Gson().toJson(projectName))
				.mediaType(MediaType.get("application/json; charset=utf-8"))
				.build().execute(new StringCallback() {
			@Override
			public void onError(Call call, Exception e) {
				Log.e("Error",e.toString());
				msgBundle.putBoolean("httpResult",false);
				msgBundle.putString("error","网络出错");
				message.setData(msgBundle);
				handler.sendMessage(message);
			}

			@Override
			public void onResponse(String response) {
				Log.e("response",response);
				Boolean res = Boolean.parseBoolean(response);
				msgBundle.putBoolean("httpResult",true);
				message.setData(msgBundle);
				handler.sendMessage(message);
			}
		});
	}
}
