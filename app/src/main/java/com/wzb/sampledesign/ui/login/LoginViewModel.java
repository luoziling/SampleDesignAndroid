package com.wzb.sampledesign.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.wzb.sampledesign.data.LoginRepository;
import com.wzb.sampledesign.data.Result;
import com.wzb.sampledesign.data.model.EcUser;
import com.wzb.sampledesign.data.model.LoggedInUser;
import com.wzb.sampledesign.R;

public class LoginViewModel extends ViewModel {

	private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
	private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
	private LoginRepository loginRepository;

	LoginViewModel(LoginRepository loginRepository) {
		this.loginRepository = loginRepository;
	}

	LiveData<LoginFormState> getLoginFormState() {
		return loginFormState;
	}

	LiveData<LoginResult> getLoginResult() {
		return loginResult;
	}

	public void login(String username, String password) {
		// can be launched in a separate asynchronous job
//		Result<LoggedInUser> result = loginRepository.login(username, password);
		Result<EcUser> result = loginRepository.login(username, password);

		if (result instanceof Result.Success) {
			LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
			// 存储前台数据
			// 可以自己写一个static的类来保存登陆用户的信息。
			loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
		} else {
			loginResult.setValue(new LoginResult(R.string.login_failed));
		}
	}

	public void loginDataChanged(String username, String password) {
		// todo: 如果为空跳转到注册（后续在做）
		if (!isUserNameValid(username)) {
			loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
		} else if (!isPasswordValid(password)) {
			loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
		} else {
			loginFormState.setValue(new LoginFormState(true));
		}
	}

	// A placeholder username validation check
	private boolean isUserNameValid(String username) {
		if (username == null) {
			// 这边只是格式验证并不是点击按钮
			return false;
		}
		return true;
//		if (username.contains("@")) {
//			return Patterns.EMAIL_ADDRESS.matcher(username).matches();
//		} else {
//			return !username.trim().isEmpty();
//		}
	}

	// A placeholder password validation check
	private boolean isPasswordValid(String password) {
		return password != null && password.trim().length() > 5;
	}
}
