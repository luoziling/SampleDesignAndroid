package com.wzb.sampledesign.ui.asynctask.expertTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.wzb.sampledesign.pojo.TreeNodeContent;
import com.wzb.sampledesign.util.Constant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Date: 2020/4/11
 * Author:Satsuki
 * Description:
 * 获取下一层节点信息或结论信息用于矩阵构建和数据录入
 */
public class GetNextOrConThread implements Runnable {
	private Handler handler;
	private TreeNodeContent treeNodeContent;

	public GetNextOrConThread(Handler handler, TreeNodeContent treeNodeContent) {
		this.handler = handler;
		this.treeNodeContent = treeNodeContent;
	}

	@Override
	public void run() {
		Log.e("GetNextOrConThread","1");
		String url = Constant.zuulDBHead + "/treenode_content/selNCByTN";
		Message message = new Message();
		message.what = Constant.DATAENTRY;
		Bundle msgBundle = new Bundle();

		Log.e("url:",url);
		Log.e("treeNodeContent:",new Gson().toJson(treeNodeContent));




		OkHttpUtils
				.postString()
				.url(url)
				.content(new Gson().toJson(treeNodeContent))
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
