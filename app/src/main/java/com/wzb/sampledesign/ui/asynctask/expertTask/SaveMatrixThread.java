package com.wzb.sampledesign.ui.asynctask.expertTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.wzb.sampledesign.pojo.Conclusion;
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
public class SaveMatrixThread implements Runnable {
	private Handler handler;
	// 矩阵数据
	private Double[][] data;

	// 当前节点
	private String nowNode;

	// 这一层准则
	private List<String> nextList;

	public SaveMatrixThread(Handler handler, Double[][] data, String nowNode, List<String> nextList) {
		this.handler = handler;
		this.data = data;
		this.nowNode = nowNode;
		this.nextList = nextList;
	}

	@Override
	public void run() {
		Log.e("SaveMatrixThread","1");
		String url = Constant.zuulbusinessHead + "/calculation_service/expertMatrixSaVE";
		Message message = new Message();
		message.what = Constant.SAVEMATRIX;
		Bundle msgBundle = new Bundle();

		// 数据封装
		MatrixWrapper matrixWrapper = new MatrixWrapper();
		matrixWrapper.setData(data);
		MatrixStorage matrixStorage = new MatrixStorage();
		matrixStorage.setUserId(Constant.loginUser.getId());
		matrixStorage.setProjectId(Constant.PROJECTID);
		matrixStorage.setProjectName(Constant.PROJECTNAMEEXPERT);
		matrixStorage.setValue(nowNode);
		matrixWrapper.setMatrixStorage(matrixStorage);
		matrixWrapper.setNextList(nextList);



		Log.e("url",url);

		Log.e("data",new Gson().toJson(matrixWrapper));


		OkHttpUtils
				.postString()
				.url(url)
				.content(new Gson().toJson(matrixWrapper))
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
