package com.wzb.sampledesign.ui.asynctask.expertTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.wzb.sampledesign.pojo.ProjectInformation;
import com.wzb.sampledesign.util.Constant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Date: 2020/4/9
 * Author:Satsuki
 * Description:
 */
public class CreateProjectThread implements Runnable {
	private Handler handler;
	private ProjectInformation projectInformation;

	public CreateProjectThread() {
	}

	public CreateProjectThread(Handler handler, ProjectInformation projectInformation) {
		this.handler = handler;
		this.projectInformation = projectInformation;
	}

	@Override
	public void run() {
		Log.e("CreateProjectThread","1");
		String url = Constant.zuulHead + "/business/project_service/insInitialExpert";
		Message message = new Message();
		message.what = Constant.PROJECTCREATION;
		Bundle msgBundle = new Bundle();

		Log.e("url",url);

		Log.e("projectInformation",new Gson().toJson(projectInformation));


		OkHttpUtils
				.postString()
				.url(url)
				.content(new Gson().toJson(projectInformation))
				.mediaType(MediaType.get("application/json; charset=utf-8"))
				.build().execute(new StringCallback() {
			@Override
			public void onError(Call call, Exception e) {
				Log.e("Error",e.toString());
				msgBundle.putBoolean("result",false);
				message.setData(msgBundle);
				handler.sendMessage(message);
			}

			@Override
			public void onResponse(String response) {
				// 处理返回信息
				msgBundle.putBoolean("result",true);
				msgBundle.putString("response",response);
				message.setData(msgBundle);
				handler.sendMessage(message);
			}
		});
	}
}
