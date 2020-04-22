package com.wzb.sampledesign.util;

import android.content.Intent;

import com.wzb.sampledesign.pojo.AdjacentClosure;
import com.wzb.sampledesign.pojo.Conclusion;
import com.wzb.sampledesign.pojo.EcUser;
import com.wzb.sampledesign.pojo.TreeNodeContent;

import java.util.List;


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
	public static final String zuulDBHead = "http://"+urlContent+":9527/db";
	public static final String zuulUserHead = "http://"+urlContent+":9527/user";
	public static final String zuulbusinessHead = "http://"+urlContent+":9527/business";

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

	// 决策项目
	public static final int PROJECTCREATION = 1;
	public static final int SAVETREENODE = 2;
	public static final int DELETETREENODE = 3;
	public static final int GETALLMODELS = 4;
	public static final int GETMODELINFO = 5;
	public static final int GETCONCLUSIONS = 5;
	public static final int SAVECONCLUSIONS = 6;
	public static final int DELETECONCLUSIONS = 7;
	public static final int DATAENTRY = 8;
	public static final int GETMATRIX = 9;
	public static final int SAVEMATRIX = 10;
	public static final int CONCAL = 11;
	public static final int REGISTER = 12;
	public static final int EDITUSERINFO = 13;


	// 记录下一层节点/方案信息 用于创建判断矩阵
	public static List<Conclusion> expertCons;
	public static List<AdjacentClosure> expertClos;
	public static List<TreeNodeContent> expertNodes;


	// 记录用户登陆信息
	public static EcUser loginUser;

	// 记录模型id
	public static Integer PROJECTID;
	public static String PROJECTNAMEEXPERT;

	// FolderStructureFragment
	public static final int CREATE_VERIFICATION = 1;
	public static final int CREATE_MODEL = 2;

}
