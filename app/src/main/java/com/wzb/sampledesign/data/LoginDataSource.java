package com.wzb.sampledesign.data;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.wzb.sampledesign.data.model.EcUser;
import com.wzb.sampledesign.data.model.LoggedInUser;
import com.wzb.sampledesign.util.Constant;

import java.io.IOException;

import androidx.annotation.NonNull;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

	private LoginHandler loginHandler;

//	public Result<LoggedInUser> login(String username, String password) {
//
//		try {
//			// TODO: handle loggedInUser authentication
//			LoggedInUser fakeUser =
//					new LoggedInUser(
//							java.util.UUID.randomUUID().toString(),
//							"Jane Doe");
//			return new Result.Success<>(fakeUser);
//		} catch (Exception e) {
//			return new Result.Error(new IOException("Error logging in", e));
//		}
//	}

	// 重写验证逻辑
	public Result<EcUser> login(String username, String password) {

		try {
			// TODO: handle loggedInUser authentication

			LoggedInUser fakeUser =
					new LoggedInUser(
							java.util.UUID.randomUUID().toString(),
							"Jane Doe");
			EcUser user;


			return new Result.Success<>(fakeUser);
		} catch (Exception e) {
			return new Result.Error(new IOException("Error logging in", e));
		}
	}

	public void logout() {
		// TODO: revoke authentication
	}


	// 处理子线程处理延时任务后的返回信息
	private class LoginHandler extends Handler{
		@Override
		public void handleMessage(@NonNull Message msg) {
			Bundle msgBundle;
			switch (msg.what){
				case Constant.LOGGING:
					msgBundle = msg.getData();
					// 处理逻辑
			}
		}
	}
}
