package com.wzb.sampledesign.ui.asynctask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.wzb.sampledesign.pojo.TreeNodeContent;
import com.wzb.sampledesign.util.Constant;
import com.wzb.sampledesign.util.FastjsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Date: 2019/10/17
 * Author:Satsuki
 * Description:
 */
public class GetCriteriaThread implements Runnable {

	private Handler handler;

	public GetCriteriaThread() {
	}

	public GetCriteriaThread(Handler handler) {
		this.handler = handler;
	}

	@Override
	public void run() {
		Log.e("GetCriteriaThread","1");

		String url = Constant.urlHead+"/project_service/getNodes/上海市骨科挂号决策支持";

		Message message = new Message();
		Bundle msgBundle = new Bundle();

		Log.e("url",url);
		// 改用okhttp交互
		OkHttpUtils
				.get()
				.url(url)
				.build()
				.connTimeOut(10000)
				.readTimeOut(10000)
				.writeTimeOut(10000)
				.execute(new StringCallback() {
					@Override
					public void onError(Call call, Exception e) {
						Log.e("Exception",e.toString());
					}

					@Override
					public void onResponse(String response) {
						Log.e("criteriaResponse",response);
						// 把json字符串发回主线程，交给主线程解决
						msgBundle.putString("criteriaResponse",response);
						// 设置从什么线程发送的Message
						message.what = Constant.GET_CRITERIA_MESSAGE;
						message.setData(msgBundle);
						handler.sendMessage(message);


					}
				});
//		Log.e("criteriaList",criteriaList.toString());
	}
}