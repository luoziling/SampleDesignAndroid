package com.wzb.sampledesign.ui.asynctask.expertTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.unnamed.b.atv.model.TreeNode;
import com.wzb.sampledesign.pojo.TreeNodeContent;
import com.wzb.sampledesign.pojo.jsonwrapper.DeleteNodeWrapper;
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
public class DeleteTreeNodeThread implements Runnable {
	private Handler handler;
	// 当前节点的内容。
	private DeleteNodeWrapper deleteNodeWrapper;

	public DeleteTreeNodeThread(Handler handler, DeleteNodeWrapper deleteNodeWrapper) {
		this.handler = handler;
		this.deleteNodeWrapper = deleteNodeWrapper;
	}

	@Override
	public void run() {
		Log.e("DeleteTreeNodeThread","1");
		String url = Constant.zuulDBHead + "/treenode_content/deleteTreeNode";
		Message message = new Message();
		message.what = Constant.DELETETREENODE;
		Bundle msgBundle = new Bundle();

		System.out.println(new Gson().toJson(deleteNodeWrapper));




		OkHttpUtils
				.postString()
				.url(url)
				.content(new Gson().toJson(deleteNodeWrapper))
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
