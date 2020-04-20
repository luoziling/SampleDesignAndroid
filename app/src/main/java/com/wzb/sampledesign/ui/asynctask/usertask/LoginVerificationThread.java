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
 * Date: 2020/4/8
 * Author:Satsuki
 * Description:
 */
public class LoginVerificationThread implements Runnable {
	private Handler handler;
	private EcUser user;

	public LoginVerificationThread() {
	}

	public LoginVerificationThread(Handler handler, EcUser user) {
		this.handler = handler;
		this.user = user;
	}

	@Override
	public void run() {
		Log.e("LoginVerficationThread","1");
		String url = Constant.zuulHead + "/user/user_service/loginVerification";
		Message message = new Message();
		message.what = Constant.LOGGING;
		Bundle msgBundle = new Bundle();

		Log.e("url",url);
		Log.e("user",new Gson().toJson(user).toString());

		OkHttpUtils
				.postString()
				.url(url)
				.content(new Gson().toJson(user))
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
				Log.e("response",response);
				// 处理返回信息
				msgBundle.putBoolean("result",true);
				msgBundle.putString("response",response);
				message.setData(msgBundle);
				handler.sendMessage(message);
			}
		});

	}
}
