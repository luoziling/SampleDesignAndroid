package com.wzb.sampledesign.util;

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
	public static final String urlContent = "172.20.10.4";
//	public static final String urlContent = "192.168.1.105";
	public static final String urlHead = "http://"+urlContent+":9091";

	public static final String urlHead1 = "http://"+urlContent+":8091";

	//单位毫秒milliseconds
	public static final int DEFAULT_TIMEOUT_MS = 3000;
	public static final int DEFAULT_MAX_RETRIES = 3;

	public static String PROJECT_NAME = "上海市骨科挂号决策支持";

	// 用于区分要处理的信息
	public static final int GET_CRITERIA_MESSAGE = 1;
	public static final int SAVE_MS_MESSAGE = 2;
	public static final int CALCULATE_MESSAGE = 3;

}
