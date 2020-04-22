package com.wzb.sampledesign.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.wzb.sampledesign.R;
import com.wzb.sampledesign.pojo.EcUser;
import com.wzb.sampledesign.pojo.result.CommonResult;
import com.wzb.sampledesign.ui.asynctask.usertask.RegisterUserThread;
import com.wzb.sampledesign.ui.login.LoginActivity;
import com.wzb.sampledesign.util.Constant;
import com.wzb.sampledesign.util.FastjsonUtil;
import com.wzb.sampledesign.util.RegexUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {

	@BindView(R.id.verifyButton)
	QMUIRoundButton verifyButton;

	@BindView(R.id.usernameEdit)
	EditText usernameEdit;
	@BindView(R.id.passwordEdit)
	EditText passwordEdit;
	@BindView(R.id.emailEdit3)
	EditText emailEdit;
	@BindView(R.id.positionEdit4)
	EditText positionEdit;
	@BindView(R.id.sexGroup)
	RadioGroup sexGroup;
	@BindView(R.id.ageEdit6)
	EditText ageEdit;

	Integer sex = -1;

	Pattern p = null;
	Matcher m = null;

	RegisterHandler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		ButterKnife.bind(this);

		verifyButton.setOnClickListener(this);
		handler = new RegisterHandler();
		sexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int i) {
				switch (i){
					case R.id.boyRadio:
						sex = 0;
						break;
					case R.id.girlRadio:
						sex = 1;
						break;
				}
			}
		});
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.verifyButton:
				EcUser user = new EcUser();
				if (usernameEdit.getText().toString().length()>0){
					user.setUsername(usernameEdit.getText().toString());
				}else {
					showToast("请输入用户名");
					break;
				}

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
				// 异步请求注册用户
				RegisterUserThread registerUserThread = new RegisterUserThread(handler,user);
				Thread asyncThread = new Thread(registerUserThread);
				asyncThread.start();

				break;


		}
	}

	public void showToast(String message){
		Toast.makeText(this,message,Toast.LENGTH_LONG).show();
	}

	private class RegisterHandler extends Handler{
		@Override
		public void handleMessage(@NonNull Message msg) {
			Bundle msgBundle;
			switch (msg.what){
				case Constant.REGISTER:
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
							Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
							startActivity(intent);
						}
					}
					break;
			}

		}
	}
}
