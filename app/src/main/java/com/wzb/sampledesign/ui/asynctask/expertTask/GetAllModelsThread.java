package com.wzb.sampledesign.ui.asynctask.expertTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.wzb.sampledesign.pojo.jsonwrapper.DeleteNodeWrapper;
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
public class GetAllModelsThread implements Runnable {
	private Handler handler;

	public GetAllModelsThread(Handler handler) {
		this.handler = handler;
	}

	@Override
	public void run() {
		Log.e("DeleteTreeNodeThread","1");
		String url = Constant.zuulDBHead + "/project_information/selByUserID/" + Constant.loginUser.getId();
		Message message = new Message();
		message.what = Constant.GETALLMODELS;
		Bundle msgBundle = new Bundle();

		Log.e("url:",url);




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
						msgBundle.putString("response",response);
						message.setData(msgBundle);
						handler.sendMessage(message);

					}
				});
	}
}
