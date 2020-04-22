package com.wzb.sampledesign.ui;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wzb.sampledesign.R;
import com.wzb.sampledesign.ui.expertentry.ExpertEntryActivity;
import com.wzb.sampledesign.ui.modelselect.ModelSelectActivity;

public class ModuleSelection extends AppCompatActivity implements View.OnClickListener {
	@BindView(R.id.expertButton)
	Button expertButton;
	@BindView(R.id.generalButton)
	Button generalButton;
//	@BindView(R.id.groupButton)
//	Button groupButton;
	@BindView(R.id.personalInformationButton)
	Button editPIButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_module_selection);
		ButterKnife.bind(this);

		expertButton.setOnClickListener(this);
		generalButton.setOnClickListener(this);
		editPIButton.setOnClickListener(this);


	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.expertButton:
				// 点击专家模块按钮进入相应模块
				Intent intent = new Intent(this,ExpertEntryActivity.class);
				startActivity(intent);
				break;
			case R.id.generalButton:
				// 跳转到 挂号决策支持系统
				Intent intent1 = new Intent(this, ModelSelectActivity.class);
				startActivity(intent1);
				break;
//			case R.id.groupButton:
//				break;
			case R.id.personalInformationButton:
				// 编辑个人信息
				Intent intent2 = new Intent(this, EditUserInfoActivity.class);
				startActivity(intent2);
				break;
		}
	}
}
