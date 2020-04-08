package com.wzb.sampledesign.ui.login;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wzb.sampledesign.R;
import com.wzb.sampledesign.pojo.EcUser;
import com.wzb.sampledesign.pojo.UserResult;
import com.wzb.sampledesign.ui.ModuleSelection;
import com.wzb.sampledesign.ui.asynctask.usertask.LoginVerificationThread;
import com.wzb.sampledesign.ui.login.LoginViewModel;
import com.wzb.sampledesign.ui.login.LoginViewModelFactory;
import com.wzb.sampledesign.util.Constant;
import com.wzb.sampledesign.util.FastjsonUtil;

public class LoginActivity extends AppCompatActivity {

	private LoginViewModel loginViewModel;

	private LoginHandler loginHandler;

	// 异步任务
	private LoginVerificationThread loginVerificationThread;
	private Thread asyncThread;

	ProgressBar loadingProgressBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
				.get(LoginViewModel.class);

		final EditText usernameEditText = findViewById(R.id.username);
		final EditText passwordEditText = findViewById(R.id.password);
		final Button loginButton = findViewById(R.id.loginButton);
		loadingProgressBar = findViewById(R.id.loading);
		loginHandler = new LoginHandler();

		loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
			@Override
			public void onChanged(@Nullable LoginFormState loginFormState) {
				if (loginFormState == null) {
					return;
				}
				loginButton.setEnabled(loginFormState.isDataValid());
				if (loginFormState.getUsernameError() != null) {
					usernameEditText.setError(getString(loginFormState.getUsernameError()));
				}
				if (loginFormState.getPasswordError() != null) {
					passwordEditText.setError(getString(loginFormState.getPasswordError()));
				}
			}
		});

		loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
			@Override
			public void onChanged(@Nullable LoginResult loginResult) {
				if (loginResult == null) {
					return;
				}
				loadingProgressBar.setVisibility(View.GONE);
				if (loginResult.getError() != null) {
					showLoginFailed(loginResult.getError());
				}
				if (loginResult.getSuccess() != null) {
					updateUiWithUser(loginResult.getSuccess());
				}
				setResult(Activity.RESULT_OK);

				//Complete and destroy login activity once successful
				finish();
			}
		});

		TextWatcher afterTextChangedListener = new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// ignore
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// ignore
			}

			@Override
			public void afterTextChanged(Editable s) {
				loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
						passwordEditText.getText().toString());
			}
		};
		usernameEditText.addTextChangedListener(afterTextChangedListener);
		passwordEditText.addTextChangedListener(afterTextChangedListener);
		passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					loginViewModel.login(usernameEditText.getText().toString(),
							passwordEditText.getText().toString());
				}
				return false;
			}
		});

		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 监听点击事件
				loadingProgressBar.setVisibility(View.VISIBLE);
//				loginViewModel.login(usernameEditText.getText().toString(),
//						passwordEditText.getText().toString());
				// 登陆验证
				// 封装信息
				EcUser user = new EcUser();
				user.setUsername(usernameEditText.getText().toString());
				user.setPassword(passwordEditText.getText().toString());
				// 开启子线程通过网络去验证用户信息
				loginVerificationThread  = new LoginVerificationThread(loginHandler,user);
				asyncThread = new Thread(loginVerificationThread);
				asyncThread.start();

			}
		});
	}

	private void updateUiWithUser(LoggedInUserView model) {
		String welcome = getString(R.string.welcome) + model.getDisplayName();
		// TODO : initiate successful logged in experience
		Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
	}

	private void showLoginFailed(@StringRes Integer errorString) {
		Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
	}

	public void showToast(String message){
		Toast.makeText(this,message,Toast.LENGTH_LONG).show();
	}


	// 处理子线程处理延时任务后的返回信息
	private class LoginHandler extends Handler {
		@Override
		public void handleMessage(@NonNull Message msg) {
			Bundle msgBundle;
			switch (msg.what){
				case Constant.LOGGING:
					loadingProgressBar.setVisibility(View.GONE);
					Log.e("msg","LOGGING");
					msgBundle = msg.getData();
					// 处理逻辑
					// 看看http请求是否成功
					if (!msgBundle.getBoolean("result")){
						showToast("网络出错请重试");
					}else {
						// http请求成功
						// 回复的json转为对象
						UserResult userResult = FastjsonUtil.from(msgBundle.getString("response"),UserResult.class);
						Log.e("userResult",userResult.toString());
						// 显示验证信息
						showToast(userResult.getReviews());
						// 判断验证是否成功
						if (userResult.getFlag()){
							Intent intent = new Intent(getApplicationContext(),ModuleSelection.class);
							startActivity(intent);
						}
					}
					break;
			}
		}
	}
}
