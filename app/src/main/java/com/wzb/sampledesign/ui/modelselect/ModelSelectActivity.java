package com.wzb.sampledesign.ui.modelselect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.wzb.sampledesign.R;
import com.wzb.sampledesign.adapter.ModelAdapter;
import com.wzb.sampledesign.pojo.ProjectInformation;
import com.wzb.sampledesign.ui.asynctask.GetAllModelThread;
import com.wzb.sampledesign.ui.modeldetail.ModelDetailActivity;
import com.wzb.sampledesign.util.Constant;
import com.wzb.sampledesign.util.FastjsonUtil;

import java.util.List;

public class ModelSelectActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
	@BindView(R.id.pull_to_refresh_ms)
	QMUIPullRefreshLayout mPullRefreshLayout;

	@BindView(R.id.msListView)
	ListView msListView;

	private List<ProjectInformation> modelList;

	private Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_model_select);
		ButterKnife.bind(this);

		mHandler = new MSHandler(this);

		msListView.setOnItemClickListener(this);

		initData();
	}

	private void initData() {

//		mListView.setAdapter(mAdapter);
		// 进入就刷新数据
		onDataLoaded();
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
				}, 1000);
			}
		});
	}

	/**
	 * 数据加载
	 */
	private void onDataLoaded() {

		Log.e("load","data");

		// todo: 新建线程获取模型列表
//		ConThread conThread = new ConThread(mHandler);
//		Thread getThread = new Thread(conThread);
//		getThread.start();
		GetAllModelThread getAllModelThread = new GetAllModelThread(mHandler);
		Thread getAllModel = new Thread(getAllModelThread);
		getAllModel.start();


	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		showToast("现在点击的是第"+i+"个item");
		Log.e("onclick","现在点击的是第"+i+"个item");

		// 修改目前模型的常量
		Constant.PROJECT_NAME = modelList.get(i).getProjectName();
		Constant.PROJECTID = modelList.get(i).getId();
		// 开启新的决策界面
		// todo:尝试用fragment替换
		// 算了。。老老实实Activity
		// fragment适合一个主界面（Activity）中包含多个fragment

		// 通过保存在方法区的static常量来判断当前模型不需要传值
		Intent mdIntent = new Intent(this, ModelDetailActivity.class);
		startActivity(mdIntent);

//		Bundle bundle = new Bundle();
//		bundle.putString("conDetail",gson.toJson(docList.get(position)));
//		// 直接开启新页面粗暴解决
//		Intent detailIntent = new Intent(this,DetailActivity.class);
//		detailIntent.putExtras(bundle);
//		startActivity(detailIntent);
	}


	class MSHandler extends Handler{
		private Context context;
		MSHandler(Context context){
			this.context = context;
		}
		@Override
		public void handleMessage(@NonNull Message msg) {
			Log.e("receive","message");

			// 更新UI
			Bundle data = msg.getData();
			String modelJson = data.getString("modelList");

			if (null!=modelJson){
				modelList = FastjsonUtil.jsonToList(modelJson,ProjectInformation.class);

				ModelAdapter modelAdapter = new ModelAdapter(context,modelList);
				msListView.setAdapter(modelAdapter);

				mPullRefreshLayout.finishRefresh();
			}else {
				// 出错
				String exception = data.getString("Exception");
				showToast(exception + "  请重试");
				mPullRefreshLayout.finishRefresh();
			}


		}
	}


	public void showToast(String message){
		Toast.makeText(this,message,Toast.LENGTH_LONG).show();
	}
}
