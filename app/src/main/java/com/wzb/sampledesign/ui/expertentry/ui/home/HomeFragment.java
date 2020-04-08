package com.wzb.sampledesign.ui.expertentry.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.wzb.sampledesign.R;
import com.wzb.sampledesign.ui.expertentry.TreeView.TreeViewActivity;
import com.wzb.sampledesign.ui.expertentry.asynctask.CreateModelThread;
import com.wzb.sampledesign.ui.expertentry.asynctask.CreateVerificationThread;
import com.wzb.sampledesign.util.Constant;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends Fragment implements View.OnClickListener {

	private HomeViewModel homeViewModel;

	@BindView(R.id.projectNameEditText)
	EditText projectNameEditText;

	@BindView(R.id.createProjectButton)
	QMUIRoundButton createProjectButton;

	private Handler mHandler;

	String projectName;

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		homeViewModel =
				ViewModelProviders.of(this).get(HomeViewModel.class);
		View root = inflater.inflate(R.layout.fragment_home_expert, container, false);

		ButterKnife.bind(this,root);

		createProjectButton.setOnClickListener(this);

		final TextView textView = root.findViewById(R.id.text_home);
		homeViewModel.getText().observe(this, new Observer<String>() {
			@Override
			public void onChanged(@Nullable String s) {
				textView.setText(s);
			}
		});

		mHandler = new MyHandler(getContext());
		return root;
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.createProjectButton:
				//todo:进入第二层数据页面
				//添加数据
				projectName = projectNameEditText.getText().toString();

				// 创建验证
				CreateVerificationThread createVerificationThread = new CreateVerificationThread(mHandler,projectName);
				Thread thread = new Thread(createVerificationThread);
				thread.start();
				createProjectButton.setText("验证中...");

//				//不经过activity直接进入fragment（待测试）
//				Intent myIntent = new Intent(getContext(), TreeViewActivity.class);
////                Intent myIntent = new Intent(getContext(), FolderStructureFragment.class);
//				//这是真实的情况 先使用测试
////                myIntent.putExtra("ProjectName",projectName);
////				myIntent.putExtra("ProjectName","myProject");
//				myIntent.putExtra("ProjectName",projectName);
//				Log.e("createProjectButton","Hello");
//				Toast.makeText(getContext(),"hello",Toast.LENGTH_LONG).show();
//				//开始activity
//				getActivity().startActivity(myIntent);
				break;
		}

	}

	private class MyHandler extends Handler {
		private Context context;

		public MyHandler(Context context) {
			this.context = context;
		}

		@Override
		public void handleMessage(@NonNull Message msg) {
			Bundle msgBundle;
			Bundle data;
			boolean httpResult;
			switch (msg.what){
				case Constant.CREATE_VERIFICATION:
					data = msg.getData();
					httpResult = data.getBoolean("httpResult");
					if (httpResult){
						boolean verification = data.getBoolean("verification");
						if (verification){
							// 验证完成可以创建
							createProjectButton.setText("创建模型中...");
							CreateModelThread createModelThread = new CreateModelThread(mHandler,projectName);
							Thread thread = new Thread(createModelThread);
							thread.start();
						}else {
							showToast("已有同名模型，请换个模型名");
							createProjectButton.setText("确认");
						}
					}else {
						createProjectButton.setText("网络出错请重试...");
					}
					break;
				case Constant.CREATE_MODEL:
					data = msg.getData();
					httpResult = data.getBoolean("httpResult");
					if (httpResult){
						showToast("模型初始化成功");
						// 开启新界面
						Intent myIntent = new Intent(getContext(), TreeViewActivity.class);
						//这是真实的情况 先使用测试
						myIntent.putExtra("ProjectName",projectName);
						Log.e("createProjectButton","Hello");
						Toast.makeText(getContext(),"hello",Toast.LENGTH_LONG).show();
						//开始activity
						getActivity().startActivity(myIntent);
					}

					break;
			}
		}
	}

	public void showToast(String message){
		Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
	}
}