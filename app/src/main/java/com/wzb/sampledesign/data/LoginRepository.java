package com.wzb.sampledesign.data;

import com.wzb.sampledesign.data.model.EcUser;
import com.wzb.sampledesign.data.model.LoggedInUser;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

	private static volatile LoginRepository instance;

	private LoginDataSource dataSource;

	// If user credentials will be cached in local storage, it is recommended it be encrypted
	// @see https://developer.android.com/training/articles/keystore
//	private LoggedInUser user = null;
	private EcUser user = null;

	// private constructor : singleton access
	private LoginRepository(LoginDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public static LoginRepository getInstance(LoginDataSource dataSource) {
		if (instance == null) {
			instance = new LoginRepository(dataSource);
		}
		return instance;
	}

	public boolean isLoggedIn() {
		return user != null;
	}

	public void logout() {
		user = null;
		dataSource.logout();
	}

	private void setLoggedInUser(EcUser user) {
		this.user = user;
		// If user credentials will be cached in local storage, it is recommended it be encrypted
		// @see https://developer.android.com/training/articles/keystore
	}

	// 重写变为自己的数据类型
//	public Result<LoggedInUser> login(String username, String password) {
//		// handle login
//		Result<LoggedInUser> result = dataSource.login(username, password);
//		if (result instanceof Result.Success) {
//			setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
//		}
//		return result;
//	}

	public Result<EcUser> login(String username, String password) {
		// handle login
		Result<EcUser> result = dataSource.login(username, password);
		if (result instanceof Result.Success) {
			// todo:保存用户登陆信息
			setLoggedInUser(((Result.Success<EcUser>) result).getData());
		}
		return result;
	}
}
