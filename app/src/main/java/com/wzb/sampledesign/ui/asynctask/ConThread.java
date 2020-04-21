package com.wzb.sampledesign.ui.asynctask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.wzb.sampledesign.pojo.jsonwrapper.ConWrapper;
import com.wzb.sampledesign.util.Constant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Date: 2019/10/16
 * Author:Satsuki
 * Description:
 */
public class ConThread implements Runnable {

	private Handler handler;

	private CountDownLatch latch;

	String conJson="";
	String docJson="";


	public ConThread() {
	}

	public ConThread(Handler handler) {
		this.handler = handler;
		// 初始化countDownLatch
		latch = new CountDownLatch(2);
	}

	@Override
	public void run() {
		// 获取数据
		// 使用volley模拟浏览器与后台API交互
		String url = Constant.zuulDBHead+"/conclusion/selByMU";
		String url1 = Constant.zuulDBHead+"/doc_info2/findCountDocInfo/10";
		Message message = new Message();
		Bundle bundle = new Bundle();
		ConWrapper conWrapper = new ConWrapper();
		conWrapper.setProjectID(Constant.PROJECTID);
		conWrapper.setUserID(Constant.loginUser.getId());

		OkHttpUtils
				.postString()
				.url(url)
				.content(new Gson().toJson(conWrapper))
				.mediaType(MediaType.get("application/json; charset=utf-8"))
				.build().execute(new StringCallback() {
			@Override
			public void onError(Call call, Exception e) {
				Log.e("Error",e.toString());
				bundle.putBoolean("result",false);
				message.setData(bundle);
				handler.sendMessage(message);
			}

			@Override
			public void onResponse(String response) {
				Log.e("response",response);
				// 把json字符串转成pojo
				conJson = response;
				latch.countDown();

				Log.e("latch",String.valueOf(latch.getCount()));


				if (latch.getCount()==0){
					bundle.putString("conList",conJson);
					bundle.putString("docList",docJson);
					message.setData(bundle);
					handler.sendMessage(message);
				}

			}
		});


//		OkHttpUtils
//				.get()
//				.url(url)
//				.build()
//				.execute(new StringCallback() {
//					@Override
//					public void onError(Call call, Exception e) {
//						Log.e("error",e.toString());
//					}
//
//					@Override
//					public void onResponse(String response) {
//						Log.e("response",response);
//						// 把json字符串转成pojo
//						conJson = response;
//						latch.countDown();
//
//						Log.e("latch",String.valueOf(latch.getCount()));
//
//
//						if (latch.getCount()==0){
//							bundle.putString("conList",conJson);
//							bundle.putString("docList",docJson);
//							message.setData(bundle);
//							handler.sendMessage(message);
//						}
//
//
//					}
//				});

		OkHttpUtils
				.get()
				.url(url1)
				.build()
				.execute(new StringCallback() {
					@Override
					public void onError(Call call, Exception e) {
						Log.e("error",e.toString());
					}

					@Override
					public void onResponse(String response) {
						Log.e("response",response);
						// 把json字符串转成pojo
//							docList = gson.from
//							new Gson().
						docJson = response;
						latch.countDown();
						Log.e("latch",String.valueOf(latch.getCount()));

						if (latch.getCount()==0){
							bundle.putString("conList",conJson);
							bundle.putString("docList",docJson);
							message.setData(bundle);
							handler.sendMessage(message);
						}

					}
				});
	}
}
