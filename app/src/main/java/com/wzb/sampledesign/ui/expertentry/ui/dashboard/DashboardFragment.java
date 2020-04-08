package com.wzb.sampledesign.ui.expertentry.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.wzb.sampledesign.R;
import com.wzb.sampledesign.adapter.ModelAdapter;
import com.wzb.sampledesign.pojo.ProjectInformation;
import com.wzb.sampledesign.ui.asynctask.GetAllModelThread;
import com.wzb.sampledesign.ui.expertentry.TreeView.TreeViewActivity;
import com.wzb.sampledesign.ui.modeldetail.ModelDetailActivity;
import com.wzb.sampledesign.ui.modelselect.ModelSelectActivity;
import com.wzb.sampledesign.util.Constant;
import com.wzb.sampledesign.util.FastjsonUtil;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;


public class DashboardFragment extends Fragment implements AdapterView.OnItemClickListener {
	@BindView(R.id.modelListView)
	ListView modelListView;

//	@BindView(R.id.topbar)
////	QMUITopBarLayout mTopBar;

	@BindView(R.id.pull_to_refresh)
	QMUIPullRefreshLayout mPullRefreshLayout;

	private List<ProjectInformation> modelList;

	private Handler mHandler;

	private DashboardViewModel dashboardViewModel;

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		dashboardViewModel =
				ViewModelProviders.of(this).get(DashboardViewModel.class);
		View root = inflater.inflate(R.layout.fragment_dashboard_expert, container, false);
		ButterKnife.bind(this,root);
		final TextView textView = root.findViewById(R.id.text_dashboard);
		dashboardViewModel.getText().observe(this, new Observer<String>() {
			@Override
			public void onChanged(@Nullable String s) {
				textView.setText(s);
			}
		});

		mHandler = new MSHandler(getContext());

		modelListView.setOnItemClickListener(this);

		initData();
		return root;
	}

	private void initData() {

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

		// 开启模型详情界面

		// 通过Origin来判断数据来源如果是从此界面选择模型那么需要在后台构建模型
		Intent mdIntent = new Intent(getContext(), TreeViewActivity.class);
		Bundle b = new Bundle();
		b.putString("ProjectName",Constant.PROJECT_NAME);
		b.putString("Origin","Save");
		mdIntent.putExtras(b);
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
				modelListView.setAdapter(modelAdapter);

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
		Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
	}
}