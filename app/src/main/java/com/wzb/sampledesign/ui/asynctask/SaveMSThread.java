package com.wzb.sampledesign.ui.asynctask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.wzb.sampledesign.pojo.MatrixStorage;
import com.wzb.sampledesign.pojo.result.RootCriData;
import com.wzb.sampledesign.util.Constant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Date: 2019/10/17
 * Author:Satsuki
 * Description:
 */
public class SaveMSThread implements Runnable {

	private Handler handler;
	private int criNum;
	private Double[][] judgmentMatrix;
	private List<String> criteriaList;
	// 插入的完整数据
	int insCount = 0;
	// 用于保持线程同步，当一个线程执行完毕后再执行另一个线程
	private CountDownLatch latch;

	public SaveMSThread() {
	}

	// 初始化参数较多，后期采用建造者设计模式优化
	public SaveMSThread(Handler handler, int criNum, Double[][] judgmentMatrix, List<String> criteriaList) {
		this.handler = handler;
		this.criNum = criNum;
		this.judgmentMatrix = judgmentMatrix;
		this.criteriaList = criteriaList;
		latch = new CountDownLatch(1);
	}

	@Override
	public void run() {
		Log.e("saveMS","SaveThread");

		String url = Constant.zuulDBHead+"/matrix_storage/insOrUpdByMS";

		Message message = new Message();
		Bundle msgBundle = new Bundle();

		message.what = Constant.SAVE_MS_MESSAGE;



		Log.e("url",url);

		new Thread(()->{
			// 在子线程中保存数据，等待数据全部保存完毕，父线程再继续运行，对保存的矩阵进行归一化处理
			// 数据交互改为使用okhttp
			MatrixStorage matrixStorage = new MatrixStorage();

			// 此Demo中只有一层准则层那么value就是根节点内容就是项目名
			matrixStorage.setValue(Constant.PROJECT_NAME);
			matrixStorage.setProjectName(Constant.PROJECT_NAME);
			// 设置projectID和userID
			matrixStorage.setProjectId(Constant.PROJECTID);
			matrixStorage.setUserId(Constant.loginUser.getId());
			for (int i = 0; i < criNum; i++) {
				for (int j = 0; j < criNum; j++) {
					System.out.print(judgmentMatrix[i][j] + " ");

					//存入数据库
					matrixStorage.setI(i);
					matrixStorage.setJ(j);
					matrixStorage.setMatrixValue(judgmentMatrix[i][j]);

					Log.e("matrixStorage",matrixStorage.toString());

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
							Log.e("response",response);
							insCount += Integer.parseInt(response);
							Log.e("insCount",String.valueOf(insCount));

							if (insCount == (criNum*criNum)){
								// 如果保存的个数等于矩阵数据的个数则保存完毕
								// 门闩计数-1说明主线程可以继续执行
								latch.countDown();
								Log.e("全部保存完毕，保存个数",String.valueOf(insCount));
							}

						}
					});
				}
				System.out.println();
			}
		}).start();


		try {
			Log.e("等待",String.valueOf(latch.getCount()));
			latch.await();
			Log.e("等待结束",String.valueOf(latch.getCount()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


		RootCriData rootCriData = new RootCriData(judgmentMatrix,"上海市骨科挂号决策支持",criteriaList,Constant.PROJECTID,Constant.loginUser.getId());

		String norUrl = Constant.urlHead+"/calculation_service/norcal_andsave";
		OkHttpUtils
				.postString()
				.url(norUrl)
				.content(new Gson().toJson(rootCriData))
				.mediaType(MediaType.get("application/json; charset=utf-8"))
				.build().execute(new StringCallback() {
			@Override
			public void onError(Call call, Exception e) {
				Log.e("Error", e.toString());
				msgBundle.putBoolean("result",false);
				message.setData(msgBundle);
				handler.sendMessage(message);
			}

			@Override
			public void onResponse(String response) {
				Log.e("response", response);
				Log.e("norcal_andsave","success");
//												 insCount += Integer.parseInt(response);

				msgBundle.putBoolean("result",true);
				message.setData(msgBundle);
				handler.sendMessage(message);

			}
		});
//		Log.e("criteriaList",criteriaList.toString());
	}
}
