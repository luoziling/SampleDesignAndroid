package com.wzb.sampledesign.util;

import com.wzb.sampledesign.data.model.EcUser;

/**
 * Date: 2019/10/12
 * Author:Satsuki
 * Description:
 */
public class Constant {
//	public static final String urlHead = "http://localhost:9091";
//
//	public static final String urlHead1 = "http://localhost:8091";
//	public static final String urlContent = "192.168.1.104";
//	public static final String urlContent = "172.20.10.4";
	// 不同IP的统一，负载均衡
//	public static final String urlContent = "192.168.1.105";
//	public static final String urlContent = "192.168.1.114";
	public static final String urlContent = "192.168.101.14";
	public static final String urlHead = "http://"+urlContent+":9091";

	public static final String urlHead1 = "http://"+urlContent+":8091";

	// 更改为统一的网关zuul的端口，由zuul去做负载均衡
	public static final String zuulHead = "http://"+urlContent+":9527";

	//单位毫秒milliseconds
	public static final int DEFAULT_TIMEOUT_MS = 3000;
	public static final int DEFAULT_MAX_RETRIES = 3;

	// 初始的默认值，后面可以随着用户选择改变
	public static String PROJECT_NAME = "上海市骨科挂号决策支持";

	public static Boolean IS_Save = false;

	// 用于区分要处理的信息
	public static final int GET_CRITERIA_MESSAGE = 1;
	public static final int SAVE_MS_MESSAGE = 2;
	public static final int CALCULATE_MESSAGE = 3;

	// 用于登录
	public static final int LOGGING = 4;

	// 记录用户登陆信息
	public static EcUser loginUser;

	// FolderStructureFragment
	public static final int CREATE_VERIFICATION = 1;
	public static final int CREATE_MODEL = 2;

}
