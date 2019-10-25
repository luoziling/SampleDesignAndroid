package com.wzb.sampledesign.ui.modelselect;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.wzb.sampledesign.R;
import com.wzb.sampledesign.pojo.ProjectInformation;
import com.wzb.sampledesign.util.Constant;
import com.wzb.sampledesign.util.FastjsonUtil;

import java.util.List;

public class ModelSelect extends Fragment implements AdapterView.OnItemClickListener {

	@BindView(R.id.pull_to_refresh_ms)
	QMUIPullRefreshLayout mPullRefreshLayout;

	@BindView(R.id.msListView)
	ListView msListView;

	private List<ProjectInformation> modelList;

	private Handler mHandler;


	private ModelSelectViewModel mViewModel;

	public static ModelSelect newInstance() {
		return new ModelSelect();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.model_select_fragment, container, false);
		ButterKnife.bind(this,root);


		return root;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mViewModel = ViewModelProviders.of(this).get(ModelSelectViewModel.class);
		// TODO: Use the ViewModel
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
				}, 2000);
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


	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		showToast("现在点击的是第"+i+"个item");
		Log.e("onclick","现在点击的是第"+i+"个item");

		// 修改目前模型的常量
		Constant.PROJECT_NAME = modelList.get(i).getProjectName();
		// 开启新的决策界面
		// todo:尝试用fragment替换

//		Bundle bundle = new Bundle();
//		bundle.putString("conDetail",gson.toJson(docList.get(position)));
//		// 直接开启新页面粗暴解决
//		Intent detailIntent = new Intent(this,DetailActivity.class);
//		detailIntent.putExtras(bundle);
//		startActivity(detailIntent);
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
			String modelJson = data.getString("modelList");

			modelList = FastjsonUtil.jsonToList(modelJson,ProjectInformation.class);

			mPullRefreshLayout.finishRefresh();
		}
	}


	public void showToast(String message){
		Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
	}
}
