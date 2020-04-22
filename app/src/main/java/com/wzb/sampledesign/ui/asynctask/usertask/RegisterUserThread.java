package com.wzb.sampledesign.ui.asynctask.usertask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.wzb.sampledesign.pojo.EcUser;
import com.wzb.sampledesign.pojo.MatrixStorage;
import com.wzb.sampledesign.pojo.jsonwrapper.MatrixWrapper;
import com.wzb.sampledesign.util.Constant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Date: 2020/4/11
 * Author:Satsuki
 * Description:
 */
public class RegisterUserThread implements Runnable {
	private Handler handler;
	// 带注册的用户信息
	private EcUser ecUser;

	public RegisterUserThread(Handler handler, EcUser ecUser) {
		this.handler = handler;
		this.ecUser = ecUser;
	}

	@Override
	public void run() {
		Log.e("SaveMatrixThread","1");
		String url = Constant.zuulUserHead + "/user_service/userRegistration";
		Message message = new Message();
		message.what = Constant.REGISTER;
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
