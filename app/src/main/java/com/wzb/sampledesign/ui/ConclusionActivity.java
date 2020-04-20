package com.wzb.sampledesign.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.qmuiteam.qmui.widget.popup.QMUIListPopup;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.wzb.sampledesign.R;
import com.wzb.sampledesign.adapter.ConclusionAdapater;
import com.wzb.sampledesign.adapter.QDSimpleAdapter;
import com.wzb.sampledesign.comparator.ConComparator;
import com.wzb.sampledesign.pojo.Conclusion;
import com.wzb.sampledesign.pojo.DocInfo2;
import com.wzb.sampledesign.ui.asynctask.ConThread;
import com.wzb.sampledesign.util.Constant;
import com.wzb.sampledesign.util.FastjsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ConclusionActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
	@BindView(R.id.conListView)
	ListView conListView;

//	@BindView(R.id.topbar)
////	QMUITopBarLayout mTopBar;

	@BindView(R.id.pull_to_refresh)
	QMUIPullRefreshLayout mPullRefreshLayout;

//	@BindView(R.id.listview)
//	RecyclerView mListView;

//	@BindView(R.id.listview)
//	RecyclerView conListView;

	private List<Conclusion> conList;
	private List<DocInfo2> docList;


	private QMUIListPopup mListPopup;

	private ConclusionAdapater conclusionAdapater;

	private QDSimpleAdapter qdSimpleAdapter;

	private static Gson gson;

	private Handler mHandler;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conclusion);
		ButterKnife.bind(this);

		// 获取结论信息
//		GetConTask getConTask = new GetConTask();
//		getConTask.execute();

		// 设置item监听事件
		conListView.setOnItemClickListener(this);
		gson = new Gson();
		mHandler = new ConHandler(this);
//		conListView.
		initData();




	}

	private void initData() {
//		mListView.setLayoutManager(new LinearLayoutManager(getContext()) {
//			@Override
//			public RecyclerView.LayoutParams generateDefaultLayoutParams() {
//				return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//						ViewGroup.LayoutParams.WRAP_CONTENT);
//			}
//		});
//
//		mAdapter = new BaseRecyclerAdapter<String>(getContext(), null) {
//			@Override
//			public int getItemLayoutId(int viewType) {
//				return android.R.layout.simple_list_item_1;
//			}
//
//			@Override
//			public void bindData(RecyclerViewHolder holder, int position, String item) {
//				holder.setText(android.R.id.text1, item);
//			}
//		};
//		mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
//			@Override
//			public void onItemClick(View itemView, int pos) {
//				Toast.makeText(getContext(), "click position=" + pos, Toast.LENGTH_SHORT).show();
//			}
//		});
//		mListView.setAdapter(mAdapter);
//		onDataLoaded();
		mPullRefreshLayout.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
			@Override
			public void onMoveTarget(int offset) {

			}

			@Override
			public void onMoveRefreshView(int offset) {

			}

			@Override
			public void onRefresh() {
				mPullRefreshLayout.postDelayed(new Runnable() {
					@Override
					public void run() {
						onDataLoaded();
//						mPullRefreshLayout.finishRefresh();
					}
				}, 2000);
			}
		});
	}

	/**
	 * 数据加载
	 */
	private void onDataLoaded() {
		// 测试用
//		List<String> data = new ArrayList<>(Arrays.asList("Helps", "Maintain", "Liver", "Health", "Function", "Supports", "Healthy", "Fat",
//				"Metabolism", "Nuturally", "Bracket", "Refrigerator", "Bathtub", "Wardrobe", "Comb", "Apron", "Carpet", "Bolster", "Pillow", "Cushion"));
//		Collections.shuffle(data);
////		mAdapter.setData(data);
//		qdSimpleAdapter = new QDSimpleAdapter(this,data);
//		conListView.setAdapter(qdSimpleAdapter);
//		mPullRefreshLayout.finishRefresh();

		Log.e("load","data");
		// 实际使用
//		GetConTask getConTask = new GetConTask(this);
//		getConTask.execute();

		ConThread conThread = new ConThread(mHandler);
		Thread getThread = new Thread(conThread);
		getThread.start();


	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		showToast("现在点击的是第"+i+"个item");
		Log.e("onclick","现在点击的是第"+i+"个item");
		initListPopupIfNeed(i);
	}

	private void initListPopupIfNeed(int position) {
		if (mListPopup == null) {

//			String[] listItems = new String[]{
//					"Item 1",
//					"Item 2",
//					"Item 3",
//					"Item 4",
//					"Item 5",
//			};
//			List<String> data = new ArrayList<>();
//
//			Collections.addAll(data, listItems);

//			List<>

//			ArrayAdapter adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_list_item_1, data);

//			DetailAdapter detailAdapter = new DetailAdapter(this,docList,position);
			Bundle bundle = new Bundle();
			bundle.putString("conDetail",gson.toJson(docList.get(position)));
			// 直接开启新页面粗暴解决
			Intent detailIntent = new Intent(this,DetailActivity.class);
			detailIntent.putExtras(bundle);
			startActivity(detailIntent);

//			mListPopup = new QMUIListPopup(getApplicationContext(), QMUIPopup.DIRECTION_NONE, detailAdapter);
//			mListPopup.create(QMUIDisplayHelper.dp2px(getApplicationContext(), 250), QMUIDisplayHelper.dp2px(this, 200), new AdapterView.OnItemClickListener() {
//				@Override
//				public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
////					Toast.makeText(getApplicationContext(), "Item " + (i + 1), Toast.LENGTH_SHORT).show();
////					mListPopup.dismiss();
//					Log.e("Item",String.valueOf(i+1));
//					showToast("Item " + (i + 1));
//
//				}
//			});
//			mListPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
//				@Override
//				public void onDismiss() {
////					mActionButton2.setText(getContext().getResources().getString(R.string.popup_list_action_button_text_show));
//				}
//			});
		}
	}

	public class GetConTask extends AsyncTask<String,Integer,Boolean>{
		boolean flag1 = false;
		boolean flag2 = false;

		private Context context;

		final CountDownLatch latch = new CountDownLatch(2);

		public GetConTask(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
//			super.onPreExecute();
//		List<String> data = new ArrayList<>(Arrays.asList("Helps", "Maintain", "Liver", "Health", "Function", "Supports", "Healthy", "Fat",
//		"Metabolism", "Nuturally", "Bracket", "Refrigerator", "Bathtub", "Wardrobe", "Comb", "Apron", "Carpet", "Bolster", "Pillow", "Cushion"));
//		Collections.shuffle(data);
////		mAdapter.setData(data);
//		qdSimpleAdapter = new QDSimpleAdapter(this.context,data);
//		conListView.setAdapter(qdSimpleAdapter);
		}

		@Override
		protected Boolean doInBackground(String... strings) {
//			boolean flag = false;
			// 根据项目名查询所有结论
			// 编写请求队列
			RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
			// 使用volley模拟浏览器与后台API交互
			String url = Constant.urlHead1+"/conclusion/selByModel";
			String url1 = Constant.urlHead1+"/doc_info2/findCountDocInfo/10";
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
//					conList = FastjsonUtil.jsonToList(response,Conclusion.class);
//				}
//			}, new Response.ErrorListener() {
//				@Override
//				public void onErrorResponse(VolleyError error) {
//					Toast.makeText(getApplicationContext(),"后台请求失败",Toast.LENGTH_LONG).show();
//					// 重试
//
//				}
//			}
//			);
//
//			StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
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
//					docList = FastjsonUtil.jsonToList(response,DocInfo2.class);
//				}
//			}, new Response.ErrorListener() {
//				@Override
//				public void onErrorResponse(VolleyError error) {
//					Toast.makeText(getApplicationContext(),"后台请求失败",Toast.LENGTH_LONG).show();
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
//			// 查询结论
//			mQueue.add(stringRequest);
//			// 查询医生信息
//			mQueue.add(stringRequest1);

			OkHttpUtils
					.get()
					.url(url)
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
							conList = FastjsonUtil.jsonToList(response,Conclusion.class);
							flag1 = true;
							latch.countDown();

							Log.e("latch",String.valueOf(latch.getCount()));
							Log.e("conList",conList.toString());


							if (latch.getCount()==0){
								for (int i = 0; i < 10; i++) {
									conList.get(i).setPlan(docList.get(i).getDocname());
								}
							}


						}
					});

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
							docList = FastjsonUtil.jsonToList(response,DocInfo2.class);
//							docList = gson.from
//							new Gson().
							Log.e("conList",conList.toString());
							flag2 = true;
							latch.countDown();
							Log.e("latch",String.valueOf(latch.getCount()));

							if (latch.getCount()==0){
								for (int i = 0; i < 10; i++) {
									conList.get(i).setPlan(docList.get(i).getDocname());
								}
							}

						}
					});

			if (flag1&&flag2){
				return true;
			}
			return false;

		}

		@Override
		protected void onPostExecute(Boolean aBoolean) {

//			List<String> data = new ArrayList<>(Arrays.asList("Helps", "Maintain", "Liver", "Health", "Function", "Supports", "Healthy", "Fat",
//					"Metabolism", "Nuturally", "Bracket", "Refrigerator", "Bathtub", "Wardrobe", "Comb", "Apron", "Carpet", "Bolster", "Pillow", "Cushion"));
//			Collections.shuffle(data);
////		mAdapter.setData(data);
//			qdSimpleAdapter = new QDSimpleAdapter(this.context,data);
//			conListView.setAdapter(qdSimpleAdapter);




//			super.onPostExecute(aBoolean);
			// 将结论中的plan替换为对应的医生名
//			try {
//				// 延迟等待
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}

//			if (aBoolean){
//				if ((flag1 == true)&&(flag2 == true)){
//					for (int i = 0; i < 10; i++) {
//						conList.get(i).setPlan(docList.get(i).getDocname());
//					}
//					flag1=flag2=false;
//				}
//			}

			try {
				latch.await(3, TimeUnit.SECONDS);
				Log.e("await latch",String.valueOf(latch.getCount()));
//				latch.await(5, TimeUnit.SECONDS);
				if (latch.getCount()==0){
					for (int i = 0; i < 10; i++) {
						conList.get(i).setPlan(docList.get(i).getDocname());
					}

					ConclusionAdapater conclusionAdapater = new ConclusionAdapater(this.context,conList);
					conListView.setAdapter(conclusionAdapater);
					mPullRefreshLayout.finishRefresh();

				}else {
					showToast("请输入新重试");
				}


			} catch (InterruptedException e) {
				e.printStackTrace();
			}




		}
	}


	class ConHandler extends Handler{
		private Context context;
		ConHandler(Context context){
			this.context = context;
		}
		@Override
		public void handleMessage(@NonNull Message msg) {
			Log.e("receive","message");

			// 更新UI
			Bundle data = msg.getData();
			String conJson = data.getString("conList");
			String docJson = data.getString("docList");
			conList = FastjsonUtil.jsonToList(conJson,Conclusion.class);
			docList = FastjsonUtil.jsonToList(docJson,DocInfo2.class);
			for (int i = 0; i < 10; i++) {
				conList.get(i).setPlan(docList.get(i).getDocname());
			}



			System.out.println("排序前:" + conList.toString());
			Collections.sort(conList,new ConComparator());
			System.out.println("排序后:" + conList.toString());
			ConclusionAdapater adapater = new ConclusionAdapater(context,conList);
			conListView.setAdapter(adapater);


			mPullRefreshLayout.finishRefresh();




		}
	}


	public void showToast(String message){
		Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
	}
}
