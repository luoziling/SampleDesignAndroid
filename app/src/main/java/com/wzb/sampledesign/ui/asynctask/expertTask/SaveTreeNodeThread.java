package com.wzb.sampledesign.ui.asynctask.expertTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.unnamed.b.atv.model.TreeNode;
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
public class SaveTreeNodeThread implements Runnable {
	private Handler handler;
	// 当前深度的节点
	private TreeNode myNode;
	private String inputValue;

	public SaveTreeNodeThread(Handler handler, TreeNode myNode, String inputValue) {
		this.handler = handler;
		this.myNode = myNode;
		this.inputValue = inputValue;
	}

	@Override
	public void run() {
		Log.e("SaveTreeNode","1");
		String url = Constant.zuulDBHead + "/treenode_content/saveTreeNode";
		Message message = new Message();
		message.what = Constant.SAVETREENODE;
		Bundle msgBundle = new Bundle();

		//深度置零
		int mydepth = 0;
		//当前节点ID（后裔ID
		Integer myID = 0;

		TreeNodeContent treeNodeContent = new TreeNodeContent();
		treeNodeContent.setValue(inputValue);
		treeNodeContent.setProjectName(Constant.PROJECTNAMEEXPERT);
		treeNodeContent.setProjectId(Constant.PROJECTID);

		// 上述是新节点要存储，存储时顺便在闭包表中存储自己的信息

		List<String> nodeList = new ArrayList<>();

		// 深度无用，在保存时候起作用，现在获取要保存的数据
		// 顺序获取该节点的所有父节点
		// 封装数据后传值保存

		// 由于直接从父节点开始的所以首先添加一个节点进去
		nodeList.add(((IconTreeItemHolder.IconTreeItem) myNode.getValue()).text);
		// root是最初的节点，新建的模型节点在root下面一个。root相当于头指针 模型节点相当于第一个指针
		while (!myNode.getParent().isRoot()){
			//若存在父节点则要保存邻接表深度+1
			//深度在每次开启任务时候需要置零
			mydepth++;
			//当前节点ID（后裔ID)就是myID
			//获取父节点
			myNode = myNode.getParent();
			//获取父节点内容
			//获取value
			// 在后端根据节点内容与项目id查询节点id
			nodeList.add(((IconTreeItemHolder.IconTreeItem) myNode.getValue()).text);

		}
		SaveNodeWrapper saveNodeWrapper = new SaveNodeWrapper(treeNodeContent,nodeList);

		Log.e("url",url);

		Log.e("saveNodeWrapper",new Gson().toJson(saveNodeWrapper));


		OkHttpUtils
				.postString()
				.url(url)
				.content(new Gson().toJson(saveNodeWrapper))
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
