package com.wzb.sampledesign.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.wzb.sampledesign.R;
import com.wzb.sampledesign.ui.modelselect.ModelSelectActivity;

public class DashboardFragment extends Fragment implements View.OnClickListener {

	private DashboardViewModel dashboardViewModel;

	@BindView(R.id.expertButton)
	QMUIRoundButton expertButton;

	// 决策支持按钮 挂号决策支持系统
	@BindView(R.id.dsButton)
	QMUIRoundButton dsButton;

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		dashboardViewModel =
				ViewModelProviders.of(this).get(DashboardViewModel.class);
		View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
		final TextView textView = root.findViewById(R.id.text_dashboard);

		ButterKnife.bind(this,root);

		dashboardViewModel.getText().observe(this, new Observer<String>() {
			@Override
			public void onChanged(@Nullable String s) {
				textView.setText(s);
			}
		});

		init();
		return root;
	}

	private void init(){
		expertButton.setOnClickListener(this);
		dsButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.expertButton:
				// 跳转到专家系统
				showToast("前方施工中...");
//				Intent intent = new Intent(this,)
				break;
			case R.id.dsButton:
				// 跳转到 挂号决策支持系统
				Intent intent = new Intent(getContext(), ModelSelectActivity.class);
				startActivity(intent);
				break;
		}
	}

	public void showToast(String message){
		Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
	}
}