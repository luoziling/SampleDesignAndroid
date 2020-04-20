package com.wzb.sampledesign.ui.asynctask.expertTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.unnamed.b.atv.model.TreeNode;
import com.wzb.sampledesign.pojo.Conclusion;
import com.wzb.sampledesign.pojo.TreeNodeContent;
import com.wzb.sampledesign.pojo.jsonwrapper.SaveNodeWrapper;
import com.wzb.sampledesign.ui.expertentry.TreeView.IconTreeItemHolder;
import com.wzb.sampledesign.util.Constant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Date: 2020/4/11
 * Author:Satsuki
 * Description:
 */
public class SaveConclusionThread implements Runnable {
	private Handler handler;
	// 当前深度的节点
	private Conclusion conclusion;

	public SaveConclusionThread(Handler handler, Conclusion conclusion) {
		this.handler = handler;
		this.conclusion = conclusion;
	}

	@Override
	public void run() {
		Log.e("SaveTreeNode","1");
		String url = Constant.zuulDBHead + "/conclusion/insConByEx";
		Message message = new Message();
		message.what = Constant.SAVECONCLUSIONS;
		Bundle msgBundle = new Bundle();



		Log.e("url",url);

		Log.e("saveNodeWrapper",new Gson().toJson(conclusion));


		OkHttpUtils
				.postString()
				.url(url)
				.content(new Gson().toJson(conclusion))
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
