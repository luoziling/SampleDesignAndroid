package com.wzb.sampledesign.ui.asynctask.usertask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.wzb.sampledesign.pojo.EcUser;
import com.wzb.sampledesign.util.Constant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Date: 2020/4/11
 * Author:Satsuki
 * Description:
 */
public class UpdUserThread implements Runnable {
	private Handler handler;
	// 待更新的用户信息
	private EcUser ecUser;

	public UpdUserThread(Handler handler, EcUser ecUser) {
		this.handler = handler;
		this.ecUser = ecUser;
	}

	@Override
	public void run() {
		Log.e("UpdUserThread","1");
		String url = Constant.zuulUserHead + "/user_service/editUserInfo";
		Message message = new Message();
		message.what = Constant.EDITUSERINFO;
		Bundle msgBundle = new Bundle();




		Log.e("url",url);

		Log.e("data",new Gson().toJson(ecUser));


		OkHttpUtils
				.postString()
				.url(url)
				.content(new Gson().toJson(ecUser))
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
