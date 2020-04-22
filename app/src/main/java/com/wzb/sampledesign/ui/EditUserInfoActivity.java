package com.wzb.sampledesign.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.wzb.sampledesign.R;
import com.wzb.sampledesign.pojo.EcUser;
import com.wzb.sampledesign.pojo.result.CommonResult;
import com.wzb.sampledesign.ui.asynctask.usertask.RegisterUserThread;
import com.wzb.sampledesign.ui.asynctask.usertask.UpdUserThread;
import com.wzb.sampledesign.ui.login.LoginActivity;
import com.wzb.sampledesign.util.Constant;
import com.wzb.sampledesign.util.FastjsonUtil;
import com.wzb.sampledesign.util.RegexUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class EditUserInfoActivity extends AppCompatActivity implements View.OnClickListener {

	@BindView(R.id.verifyButton1)
	QMUIRoundButton verifyButton;

	@BindView(R.id.usernameEdit1)
	EditText usernameEdit;
	@BindView(R.id.passwordEdit1)
	EditText passwordEdit;
	@BindView(R.id.emailEdit31)
	EditText emailEdit;
	@BindView(R.id.positionEdit41)
	EditText positionEdit;
	@BindView(R.id.sexGroup1)
	RadioGroup sexGroup;
	@BindView(R.id.ageEdit61)
	EditText ageEdit;

	Integer sex = -1;

	Pattern p = null;
	Matcher m = null;

	EditHandler handler;

	@BindView(R.id.boyRadio1)
	RadioButton boyButton;
	@BindView(R.id.girlRadio1)
	RadioButton girlButton;

	EcUser user;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info1);
		ButterKnife.bind(this);

		verifyButton.setOnClickListener(this);
		handler = new EditHandler();
		// 用户名不可编辑
		usernameEdit.setFocusable(false);
		usernameEdit.setFocusableInTouchMode(false);
		sexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int i) {
				switch (i){
					case R.id.boyRadio1:
						sex = 0;
						break;
					case R.id.girlRadio1:
						sex = 1;
						break;
				}
			}
		});

		Log.e("userInfo:",Constant.loginUser.toString());

		usernameEdit.setText(Constant.loginUser.getUsername());
		passwordEdit.setText(Constant.loginUser.getPassword());

		if (Constant.loginUser.getEmail()!=null&&Constant.loginUser.getEmail().length()>0){
			emailEdit.setText(Constant.loginUser.getEmail());
		}

		if (Constant.loginUser.getSex()!=null){
			if (Constant.loginUser.getSex()==0){
				boyButton.setChecked(true);
			}else if (Constant.loginUser.getSex()==1){
				girlButton.setChecked(true);
			}
		}

		if (Constant.loginUser.getPosition()!=null&&Constant.loginUser.getPosition().length()>0){
			positionEdit.setText(Constant.loginUser.getPosition());
		}

		if (Constant.loginUser.getAge()!=null){
			ageEdit.setText(String.valueOf(Constant.loginUser.getAge()));
		}

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.verifyButton1:
				user = new EcUser();
				user.setId(Constant.loginUser.getId());
				user.setUsername(Constant.loginUser.getUsername());
//				if (usernameEdit.getText().toString().length()>0){
//					user.setUsername(usernameEdit.getText().toString());
//				}else {
//					showToast("请输入用户名");
//					break;
//				}


				if (passwordEdit.getText().toString().length()>0){
					user.setPassword(passwordEdit.getText().toString());
				}else {
					showToast("请输入密码");
					break;
				}



				if (emailEdit.getText().toString().length()>0){
					user.setEmail(emailEdit.getText().toString());
				}

				if (positionEdit.getText().toString().length()>0){
					user.setPosition(positionEdit.getText().toString());
				}

//				if (sex!=-1){
//					user.setSex(sex);
//				}
				user.setSex(sex);

				if (ageEdit.getText().toString().length()>0){
					user.setAge(Integer.parseInt(ageEdit.getText().toString()));
				}

				// 用户名与密码不能为空
				if (user.getUsername().length()==0){
					showToast("请输入用户名");
					break;
				}
				if (user.getPassword().length()==0){
					showToast("请输入密码");
					break;
				}

				Log.e("user:" , user.toString());
				// 验证用户名密码是否符合要求
				//匹配用户名
//				System.out.println("用户名：   ---（由字母数字下划线组成且开头必须是字母，不能超过16位）");


				if (RegexUtil.usernameVerify(user.getUsername())){
					// 用户名验证通过
					// 验证密码
					if (RegexUtil.passwordVerify(user.getPassword())){
						// 密码验证通过
						// 验证邮箱
						if (user.getEmail()==null||user.getEmail().length()<=0||RegexUtil.emailVerify(user.getEmail())){
							// 验证年龄
							if (user.getAge()==null||user.getAge()==-1||RegexUtil.ageVerify(user.getAge().toString())){
								// 什么都不做继续执行
							}else {
								showToast("年龄（1岁--120岁）");
								break;
							}
						}else {
							showToast("邮箱：   ---（必须包含@符号；必须包含点；点和@之间必须有字符）");
							break;
						}
					}else {
						showToast("密码：---（字母和数字构成，不能超过16位）");
						break;
					}
				}else {
					showToast("用户名：   ---（由字母数字下划线组成且开头必须是字母，不能超过16位）");
					break;
				}
				// 异步请求保存用户信息
				UpdUserThread updUserThread = new UpdUserThread(handler,user);
				Thread asyncThread = new Thread(updUserThread);
				asyncThread.start();

				break;


		}
	}

	public void showToast(String message){
		Toast.makeText(this,message,Toast.LENGTH_LONG).show();
	}

	private class EditHandler extends Handler{
		@Override
		public void handleMessage(@NonNull Message msg) {
			Bundle msgBundle;
			switch (msg.what){
				case Constant.EDITUSERINFO:
					msgBundle = msg.getData();
					// 处理逻辑
					if (!msgBundle.getBoolean("result")){
						showToast("网络出错请重试");
					}else {
						// 请求成功
						CommonResult result = FastjsonUtil.from(msgBundle.getString("response"),CommonResult.class);
						// 显示返回信息
						showToast(result.getReviews());
						// 判断是否成功
						if (result.isFlag()){
//							Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//							startActivity(intent);
							Constant.loginUser = user;
						}
					}
					break;
			}

		}
	}
}
