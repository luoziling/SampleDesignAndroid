package com.wzb.sampledesign.ui.home;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.MediaType;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.qmuiteam.qmui.util.QMUIColorHelper;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.wzb.sampledesign.R;
import com.wzb.sampledesign.pojo.MatrixStorage;
import com.wzb.sampledesign.pojo.TreeNodeContent;
import com.wzb.sampledesign.pojo.result.RootCriData;
import com.wzb.sampledesign.ui.ConclusionActivity;
import com.wzb.sampledesign.ui.asynctask.CalculateThread;
import com.wzb.sampledesign.ui.asynctask.GetCriteriaThread;
import com.wzb.sampledesign.ui.asynctask.SaveMSThread;
import com.wzb.sampledesign.util.Constant;
import com.wzb.sampledesign.util.FastjsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import java.util.ArrayList;
import java.util.List;

//@Widget(name = "RoundButton", iconRes = R.mipmap.icon_grid_button)
public class HomeFragment extends Fragment implements View.OnClickListener {

	private HomeViewModel homeViewModel;

	//绑定UI控件
	@BindView(R.id.ratioSeekBar)
	SeekBar mRatioSeekBar;

	@BindView(R.id.ratioSeekBarWrap)
	LinearLayout mRatioSeekBarWrap;

	@BindView(R.id.criteriaOneTextView)
	TextView criteriaOneTextView;

	@BindView(R.id.criteriaTwoTextView)
	TextView criteriaTwoTextView;

	@BindView(R.id.confirmButton)
	QMUIRoundButton confirmButton;

	@BindView(R.id.conTestButton)
	QMUIRoundButton conTestButton;

	//全局变量

	// 用户移动seekBar产生的判断矩阵数据
	Double judge = 0.0;

	// 保存数据的判断矩阵，当全部数据输入完成后保存到数据库，并进行结论计算
	// 后续可以将必要的数据保存到ViewModel中防止数据丢失
	Double[][] judgmentMatrix;

	//准则总数用于构建判断矩阵
	int criNum;

	// 行列的下标值
	int rowIndex = 0;
	int colIndex = 0;

	// 记录插入的条数
	int insCount = 0;

	// 保存数据
	boolean flag = false;

	//计算结果
	boolean calFlag = false;

	List<String> criteriaList = new ArrayList<>();

	private HomeHandler homeHandler;

	// 异步任务
	GetCriteriaThread getCriteriaThread;
	SaveMSThread saveMSThread;
	CalculateThread calculateThread;
	Thread asyncThread;

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		homeViewModel =
				ViewModelProviders.of(this).get(HomeViewModel.class);
		View root = inflater.inflate(R.layout.fragment_home, container, false);

		ButterKnife.bind(this,root);

		final TextView textView = root.findViewById(R.id.text_home);
		homeViewModel.getText().observe(this, new Observer<String>() {
			@Override
			public void onChanged(@Nullable String s) {
				textView.setText(s);
			}
		});

		//实例化 必须放在init前面，因为init中包含了数据交互的内容需要用到handler
		homeHandler = new HomeHandler();

		init();

		confirmButton.setOnClickListener(this);
		container.setOnClickListener(this);
		conTestButton.setOnClickListener(this);



		return root;
	}

	private void init(){
		// 根据比例，在两个color值之间计算出一个color值
		final int fromColor = ContextCompat.getColor(getContext(),R.color.colorHelper_square_from_ratio_background);
		final int toColor = ContextCompat.getColor(getContext(),R.color.colorHelper_square_to_ratio_background);
		mRatioSeekBarWrap.setBackgroundColor(QMUIColorHelper.computeColor(fromColor,toColor,(float) 50 / 100));
		// 初始化判断值与seekBar中心位置
		judge = 1.0;
		mRatioSeekBar.setProgress(50);
		// 设置SeekBar监听事件
		mRatioSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				// 进度条改变时
				// 计算颜色中间值
				// i代表progress
				int ratioColor = QMUIColorHelper.computeColor(fromColor,toColor,(float) i / 100);
				// 设置背景linearLayout的颜色
				mRatioSeekBarWrap.setBackgroundColor(ratioColor);
				Log.e("now:",String.valueOf(i));
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// 刚刚触碰进度条
				Toast.makeText(getContext(),"当前的进度：" + seekBar.getProgress(),Toast.LENGTH_SHORT).show();
//				Toast.makeText(getActivity(),"当前的进度：" + seekBar.getProgress(),Toast.LENGTH_LONG).show();



			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				Log.e("当前的进度",String.valueOf(seekBar.getProgress()));
				// 松开进度条

				//以下是业务逻辑
				// 后续可以在这里计算用户在两个准则之间偏好哪个
				int progress = seekBar.getProgress();

				Toast.makeText(getContext(),"当前的进度：" + seekBar.getProgress(),Toast.LENGTH_SHORT).show();
				// 判断矩阵的输入值

				// 因为设置了进度是0-100
				// 初始值是在中间
				// 向左为正，向右为负
				// 其实没有正负
				// 0-10 1为分界线
				if (progress<50){
					// 0-50是偏好左边准则数字越小代表向左滑动越大，此时数字越小
					// 判断矩阵中数值的计算应该是[(50-progress)/50]
					judge = ((50-progress)/50.0);
					//左边准则优先于右边准则，此时需要*10
					judge = judge*10;
				}else if (progress>50){
					// 50-100是偏好右边准则数字越大代表向左滑动越大，此时数字越大
					// 判断矩阵中数值的计算应该是[(progress-50)/50]
					// 右侧准则大于左侧准则此时小于1也要改变
					judge = ((progress-50)/50.0);
					// 小于一是因为倒数
					judge *= 10;
					judge = 1.0/judge;

				}else if (progress==50){
					judge = 1.0;
				}

				Toast.makeText(getContext(),"当前的judge：" + judge,Toast.LENGTH_SHORT).show();


				//之后需要一个button来保存数据存入数据库
			}
		});



		// 与后台交互获取所有的准则
//		GetCriteriaTask getCriteriaTask = new GetCriteriaTask();
//		getCriteriaTask.execute();

		// AsyncTask 替换为 Thread+Handler
		getCriteriaThread = new GetCriteriaThread(homeHandler);
		asyncThread = new Thread(getCriteriaThread);
		asyncThread.start();

//		if(criNum<=0){
//			confirmButton.setText("重新获得数据");
//		}

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.confirmButton:
				if(criNum>0){
					// 按下确认后，保存之前选择的数据（保存judge
					// 还是先将数据保存，当数据全部输入完成形成一个矩阵之后再保存到数据库
					// 上三角数值未输入完成
//				if((rowIndex < criNum) && (colIndex<criNum)){
					// 最后一行不需要输入
					Log.e("criNum",String.valueOf(criNum));
					Log.e("rowIndex",String.valueOf(rowIndex));
					Log.e("colIndex",String.valueOf(colIndex));
					// 行号需要-2 如果-1那么会多点击一次，上三角到n-1行就结束了
					// -1是必要的
//					if((rowIndex < criNum-1) && (colIndex<criNum)){
					if((rowIndex < criNum-1) && (colIndex<criNum)){
						judgmentMatrix[rowIndex][colIndex] = judge;

						Log.e("judge",String.valueOf(judge));

						// 下三角为上三角的倒数
						judgmentMatrix[colIndex][rowIndex] = 1.0/judge;

						Log.e("judge logarithm",String.valueOf(1.0/judge));

						// 列下标+1
						colIndex++;
						if (colIndex>=criNum){
							// 列下标越界
							// 行下标+1
							// 列下标置为行下标+1
							rowIndex++;
							colIndex = rowIndex+1;
						}


						// 如果不进行判断在最后一次数值输入会出现下标越界的情况
						if (rowIndex<criNum&&colIndex<criNum){
							// 更改显示的准则
							criteriaOneTextView.setText(criteriaList.get(rowIndex));
							criteriaTwoTextView.setText(criteriaList.get(colIndex));
						}else {
							// 一旦列赋初值也超过下标则表明矩阵输入完毕
							// 将按钮设置为结论计算
							confirmButton.setText("结果保存");

							// 数值全部输入完成
							System.out.println("完整矩阵：");
							for (int i = 0; i < criNum; i++) {
								for (int j = 0; j < criNum; j++) {
									System.out.print(judgmentMatrix[i][j] + " ");
								}
								System.out.println();
							}
						}

					}else if((rowIndex == criNum-1)&&(colIndex == criNum)&&(flag == false)) {

						// 这里应该进行结果保存
						// 之后再一次按钮才是结论计算
						Log.e("rowIndex1",String.valueOf(rowIndex));
						Log.e("colIndex1",String.valueOf(colIndex));
						Log.e("flag",String.valueOf(flag));
						// 数值全部输入完成
						System.out.println("完整矩阵：");


//						SaveTask saveTask = new SaveTask();
//						saveTask.execute();

						// AsyncTask 替换为 Thread+Handler
						saveMSThread = new SaveMSThread(homeHandler,criNum,judgmentMatrix,criteriaList);
						asyncThread = new Thread(saveMSThread);
						asyncThread.start();


//						MatrixStorage matrixStorage = new MatrixStorage();
//
//						// 此Demo中只有一层准则层那么value就是根节点内容就是项目名
//						matrixStorage.setValue(Constant.PROJECT_NAME);
//						matrixStorage.setProjectName(Constant.PROJECT_NAME);
//						for (int i = 0; i < criNum; i++) {
//							for (int j = 0; j < criNum; j++) {
//								System.out.print(judgmentMatrix[i][j] + " ");
//
//								//存入数据库
//								matrixStorage.setI(i);
//								matrixStorage.setJ(j);
//								matrixStorage.setMatrixValue(judgmentMatrix[i][j]);
//
//								Log.e("matrixStorage",matrixStorage.toString());
//
//								saveTask = new SaveTask();
//								saveTask.execute(matrixStorage);
//							}
//							System.out.println();
//						}

						confirmButton.setText("保存中...");
//						confirmButton.setText("结论计算");

						// 将flag的处理移到handler中
//						flag = true;



						//当计算为false时才可以
					}else if (flag&&!calFlag){
						// 将按钮设置为结论计算
//						confirmButton.setText("结论计算");

//						CalculationTask calculationTask = new CalculationTask();
//						calculationTask.execute();

						// AsyncTask 替换为 Thread+Handler
						calculateThread = new CalculateThread(homeHandler);
						asyncThread = new Thread(calculateThread);
						asyncThread.start();

//						calFlag = true;
						// 不需要重置
//						flag = false;
						confirmButton.setText("计算中...");
//						confirmButton.setText("查看结论");
					}else if (calFlag){
						// 查看结论
						Intent intent = new Intent(getContext(), ConclusionActivity.class);
						startActivity(intent);
					}


					// 还需要一步计算CR值确认数据是否可用（这一步先省略
					// 当数据全部输入完成，按钮内容需要修改变为，计算结论

					// 确认按钮一定要等准则加载完毕之后再按
				}else{
					// 数据获取失败
					// 重新发送请求获取数据
					confirmButton.setText("重新获得数据");
//					GetCriteriaTask getCriteriaTask = new GetCriteriaTask();
//					getCriteriaTask.execute();
					// AsyncTask 替换为 Thread+Handler
					getCriteriaThread = new GetCriteriaThread(homeHandler);
					asyncThread = new Thread(getCriteriaThread);
					asyncThread.start();
				}



				break;
			case R.id.conTestButton:
				Log.e("conTestButton","click");
				// 这边是结论计算测试
//				CalculationTask calculationTask = new CalculationTask();
//				calculationTask.execute();
				// 这边是查看列论
				Intent intent = new Intent(getContext(), ConclusionActivity.class);
				startActivity(intent);
				break;
		}
	}

	public class GetCriteriaTask extends AsyncTask<Void,Integer,Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}



		@Override
		protected Boolean doInBackground(Void... voids) {
			Log.e("GetCriteriaTask","1");

			String url = Constant.urlHead+"/project_service/getNodes/上海市骨科挂号决策支持";

//			// 编写请求队列
//			RequestQueue mQueue = Volley.newRequestQueue(getContext());
//			// 使用volley模拟浏览器与后台API交互
//
//			Log.e("url",url);
//			//第一步的请求方式是GET还是POST最好加上
//			// Request a string response from the provided URL.
//			StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//				@Override
//				public void onResponse(String response) {
//					// 请求成功
//					// Display the first 500 characters of the response string.
//					Log.e("response",response);
//					if (response.length()>400){
//						showToast("Response is: "+ response.substring(0,500));
//					}else {
//						showToast("Response is: "+ response);
//					}
//
//					// 把json字符串转成pojo
//					Log.e("response",response);
//					List<TreeNodeContent> mList = FastjsonUtil.jsonToList(response,TreeNodeContent.class);
////					for (TreeNodeContent tnContent:mList) {
////						criteriaList.add(tnContent.getValue());
////					}
//					// 由于取出了所有节点包括root节点也就是项目名节点
//					// 真正的准则要从第二个开始取
//					for (int i = 1; i < mList.size(); i++) {
//						criteriaList.add(mList.get(i).getValue());
//					}
//				}
//			}, new Response.ErrorListener() {
//						@Override
//						public void onErrorResponse(VolleyError error) {
//							Log.e("error",error.toString());
//							Toast.makeText(getContext(),"后台请求失败",Toast.LENGTH_LONG).show();
//							Log.e("后台请求失败",error.toString());
//							// 重试
//
//						}
//					}
//			);
//
//			// 设置Volley超时重试策略
//			stringRequest.setRetryPolicy(
//					new DefaultRetryPolicy
//							(Constant.DEFAULT_TIMEOUT_MS,
//									Constant.DEFAULT_MAX_RETRIES,
//									DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//
//			// Add the request to the RequestQueue.
//			mQueue.add(stringRequest);
//			Log.e("criteriaList:",criteriaList.toString());



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
							Log.e("response",response);
							// 把json字符串转成pojo
							Log.e("response",response);
							List<TreeNodeContent> mList = FastjsonUtil.jsonToList(response,TreeNodeContent.class);
		//					for (TreeNodeContent tnContent:mList) {
		//						criteriaList.add(tnContent.getValue());
		//					}
							// 由于取出了所有节点包括root节点也就是项目名节点
							// 真正的准则要从第二个开始取
							// 第一次获取才可以取值
							if(criteriaList.size()<=0){
								for (int i = 1; i < mList.size(); i++) {
									criteriaList.add(mList.get(i).getValue());
								}
							}

						}
					});

			try {
				Log.e("criteriaList",criteriaList.toString());
				Thread.sleep(2000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Boolean aBoolean) {
//			super.onPostExecute(aBoolean);
			// 这里属于主线程了
			// 在这边更新UI
			// 初始化第一个页面使用前两个准则构建
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			// 从后台获取到数据后再加载UI

//			while (!(criteriaList.size()>0)){
//				try {
//					Log.e("criteriaList",criteriaList.toString());
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}

			if (!(criteriaList.size()>0)){
				try {
					Log.e("criteriaList",criteriaList.toString());
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (criteriaList.size()>0){
				criteriaOneTextView.setText(criteriaList.get(0));
				criteriaTwoTextView.setText(criteriaList.get(1));

				Log.e("criteriaList",criteriaList.toString());

				// 初始化判断矩阵
				criNum = criteriaList.size();
				judgmentMatrix = new Double[criNum][criNum];
				// 将全部值初始化为1
				for (int i = 0; i < criNum; i++) {
					for (int j = 0; j < criNum; j++) {
						judgmentMatrix[i][j] = 1.0;
					}
				}

				// 初始化行列下标
				// 对角线为1
				// 从0 1开始
				// 输入上三角值
				rowIndex = 0;
				colIndex = 1;
			}



		}
	}

	public class SaveTask extends AsyncTask<MatrixStorage,Integer,Boolean>{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(MatrixStorage... matrixStorages) {
//			MatrixStorage saveMS = matrixStorages[0];
//			Log.e("saveMS",saveMS.toString());
			Log.e("saveMS","SaveTask");
			// 编写请求队列
//			RequestQueue mQueue = Volley.newRequestQueue(getContext());
			// 使用volley模拟浏览器与后台API交互
			String url = Constant.urlHead+"/project_service/save_ms";
			url = Constant.urlHead1+"/matrix_storage/insOrUpdByMS";
			//第一步的请求方式是GET还是POST最好加上
			// Request a string response from the provided URL.
//			StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//				@Override
//				public void onResponse(String response) {
//					//请求成功
//					// Display the first 500 characters of the response string.
//					System.out.println("response:" + response);
//					if (response.length()>400){
//						showToast("Response is: "+ response.substring(0,500));
//					}else {
//						showToast("Response is: "+ response);
//					}
//
//				}
//			}, new Response.ErrorListener() {
//				@Override
//				public void onErrorResponse(VolleyError error) {
//					Toast.makeText(getContext(),"后台请求失败",Toast.LENGTH_LONG).show();
//					// 重试
//
//				}
//			}
//			){
//				@Override
//				protected Map<String, String> getParams() throws AuthFailureError {
////					return super.getParams();
//					Map<String, String> postMap= new HashMap<>();
//					postMap.put("i",String.valueOf(saveMS.getI()));
//					postMap.put("j",String.valueOf(saveMS.getJ()));
//					postMap.put("matrixValue",String.valueOf(saveMS.getMatrixValue()));
//					postMap.put("projectName",String.valueOf(saveMS.getProjectName()));
//					postMap.put("value",String.valueOf(saveMS.getValue()));
//					return postMap;
//				}
//			};
//
//			// 设置Volley超时重试策略
//			stringRequest.setRetryPolicy(
//					new DefaultRetryPolicy
//							(Constant.DEFAULT_TIMEOUT_MS,
//									Constant.DEFAULT_MAX_RETRIES,
//									DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//
//			// Add the request to the RequestQueue.
//			mQueue.add(stringRequest);

//			Log.e("")


			// 数据交互改为使用okhttp
			MatrixStorage matrixStorage = new MatrixStorage();

			// 此Demo中只有一层准则层那么value就是根节点内容就是项目名
			matrixStorage.setValue(Constant.PROJECT_NAME);
			matrixStorage.setProjectName(Constant.PROJECT_NAME);
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
						}

						@Override
						public void onResponse(String response) {
							Log.e("response",response);
							insCount += Integer.parseInt(response);

						}
					});

				}
				System.out.println();
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
				}

				@Override
				public void onResponse(String response) {
					Log.e("response", response);
//					insCount += Integer.parseInt(response);

				}
			});


//			new Callback() {
//				@Override
//				public Object parseNetworkResponse(okhttp3.Response response) throws Exception {
//					String res = response.body().toString();
//					return res;
//				}
//
//				@Override
//				public void onError(Call call, Exception e) {
//					Log.e("error",e.toString());
//				}
//
//				@Override
//				public void onResponse(Object response) {
//					Log.e("response",response.toString());
//				}
//			}
			return null;
		}

		@Override
		protected void onPostExecute(Boolean aBoolean) {
			super.onPostExecute(aBoolean);
		}
	}

	public class CalculationTask extends AsyncTask<Void,Integer,Boolean>{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(Void... voids) {
			//结论计算
			// 编写请求队列
			RequestQueue mQueue = Volley.newRequestQueue(getContext());
			// 使用volley模拟浏览器与后台API交互
			String url = Constant.urlHead+"/calculation_service/conclusion_calculation";
			Log.e("url",url);
//			//第一步的请求方式是GET还是POST最好加上
//			// Request a string response from the provided URL.
//			StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//				@Override
//				public void onResponse(String response) {
//					//请求成功
//					// Display the first 500 characters of the response string.
//					System.out.println("response:" + response);
//					if (response.length()>400){
//						showToast("Response is: "+ response.substring(0,500));
//					}else {
//						showToast("Response is: "+ response);
//					}
//
//					// 把json字符串转成pojo
//					List<TreeNodeContent> mList = FastjsonUtil.jsonToList(response,TreeNodeContent.class);
////					for (TreeNodeContent tnContent:mList) {
////						criteriaList.add(tnContent.getValue());
////					}
//					// 由于取出了所有节点包括root节点也就是项目名节点
//					// 真正的准则要从第二个开始取
//					for (int i = 1; i < mList.size(); i++) {
//						criteriaList.add(mList.get(i).getValue());
//					}
//				}
//			}, new Response.ErrorListener() {
//				@Override
//				public void onErrorResponse(VolleyError error) {
//					Toast.makeText(getContext(),"后台请求失败",Toast.LENGTH_LONG).show();
//					// 重试
//
//				}
//			}
//			);
//
//			// 设置Volley超时重试策略
//			stringRequest.setRetryPolicy(
//					new DefaultRetryPolicy
//							(Constant.DEFAULT_TIMEOUT_MS,
//									Constant.DEFAULT_MAX_RETRIES,
//									DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//
//			// Add the request to the RequestQueue.
//			mQueue.add(stringRequest);

			OkHttpUtils
					.get()
					.url(url)
					.build()
					.execute(new StringCallback() {
						@Override
						public void onError(Call call, Exception e) {
							Log.e("Error",e.toString());
						}

						@Override
						public void onResponse(String response) {
							Log.e("response",response);

							// 每次请求成功应该会返回1
							// 每次都加一直至结束看看最后的计数是否和矩阵相同，若相同则计算结论不同则将计数清零并重新发送
						}
					});
			return null;
		}

		@Override
		protected void onPostExecute(Boolean aBoolean) {
//			super.onPostExecute(aBoolean);
			//跳转到结论页面
//			Intent intent = new Intent(getContext(), ConclusionActivity.class);
//			startActivity(intent);
		}
	}

	public void showToast(String message){
		Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
	}


	// 先写n个Handler处理N个数据
	class HomeHandler extends Handler{
		@Override
		public void handleMessage(@NonNull Message msg) {
			Bundle msgBundle;
			switch (msg.what){
				case Constant.GET_CRITERIA_MESSAGE:
					Log.e("msg","GET_CRITERIA_MESSAGE");
					msgBundle = msg.getData();
					String criteriaResponse = msgBundle.getString("criteriaResponse");
					// 把json字符串转成pojo
					List<TreeNodeContent> mList = FastjsonUtil.jsonToList(criteriaResponse,TreeNodeContent.class);
					// 由于取出了所有节点包括root节点也就是项目名节点
					// 真正的准则要从第二个开始取
					// 第一次获取才可以取值
					if(criteriaList.size()<=0){
						for (int i = 1; i < mList.size(); i++) {
							criteriaList.add(mList.get(i).getValue());
						}
						Log.e("数据获取","成功");
						showToast("数据获取成功");
						confirmButton.setText("数据录入");
					}else {
						Log.e("数据已获取","请勿重复获取");
						showToast("数据已获取请勿重复获取");
					}

					if (criteriaList.size()>0){
						Log.e("criteriaList",criteriaList.toString());

						criteriaOneTextView.setText(criteriaList.get(0));
						criteriaTwoTextView.setText(criteriaList.get(1));

						// 初始化判断矩阵
						criNum = criteriaList.size();
						judgmentMatrix = new Double[criNum][criNum];
						// 将全部值初始化为1
						for (int i = 0; i < criNum; i++) {
							for (int j = 0; j < criNum; j++) {
								judgmentMatrix[i][j] = 1.0;
							}
						}

						// 初始化行列下标
						// 对角线为1
						// 从0 1开始
						// 输入上三角值
						rowIndex = 0;
						colIndex = 1;
					}
					break;
				case Constant.SAVE_MS_MESSAGE:
					Log.e("msg","SAVE_MS_MESSAGE");
					msgBundle = msg.getData();
					if (msgBundle.getBoolean("result")){
						Log.e("数据保存","成功");
						showToast("数据保存成功");
						flag = true;
						confirmButton.setText("结论计算");
					}else{
						Log.e("数据保存","失败");
						showToast("数据保存失败");
						// 在这里可以重试重新保存
						showToast("数据保存失败..请重试");
						showToast("aya，服务器开小差了");
						// 将保存数据的flag设置为false
						// 点击按钮重新保存数据
						flag = false;
						confirmButton.setText("重试");
					}

					break;
				case Constant.CALCULATE_MESSAGE:
					Log.e("msg","CALCULATE_MESSAGE");
					msgBundle = msg.getData();
					if (msgBundle.getBoolean("result")){
						showToast("数据计算成功");
						calFlag = true;
						confirmButton.setText("查看结论");
					}else{
						showToast("数据计算失败");
						// 在这里可以重试重新计算
						showToast("数据计算失败..请重试");
						showToast("aya，服务器开小差了");
						// 将数据计算的flag设置为false
						// 点击按钮重新保存数据
						calFlag = false;
						confirmButton.setText("重试");
					}
					break;
			}

		}
	}

}