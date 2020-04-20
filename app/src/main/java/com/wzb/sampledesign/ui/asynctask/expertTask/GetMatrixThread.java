package com.wzb.sampledesign.ui.asynctask.expertTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.wzb.sampledesign.pojo.MatrixStorage;
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
public class GetMatrixThread implements Runnable {
	private Handler handler;
	private MatrixStorage matrixStorage;

	public GetMatrixThread(Handler handler, MatrixStorage matrixStorage) {
		this.handler = handler;
		this.matrixStorage = matrixStorage;
	}

	@Override
	public void run() {
		Log.e("GetMatrixThread","1");
		String url = Constant.zuulDBHead + "/matrix_storage/selByIV";
		Message message = new Message();
		message.what = Constant.GETMATRIX;
		Bundle msgBundle = new Bundle();

		Log.e("url:",url);
		Log.e("matrixStorage:",new Gson().toJson(matrixStorage));




		OkHttpUtils
				.postString()
				.url(url)
				.content(new Gson().toJson(matrixStorage))
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
