package com.wzb.sampledesign.pojo.jsonwrapper;

import com.wzb.sampledesign.pojo.TreeNodeContent;

import java.util.List;

/**
 * Date: 2020/4/11
 * Author:Satsuki
 * Description:
 * 保存节点时封装json数据的包装类
 */
public class DeleteNodeWrapper {
	// 项目id
	private int projectID;
	// 节点内容
	private String nodeValue;

	public DeleteNodeWrapper() {
	}

	public DeleteNodeWrapper(int projectID, String nodeValue) {
		this.projectID = projectID;
		this.nodeValue = nodeValue;
	}



	public int getProjectID() {
		return projectID;
	}

	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}

	public String getNodeValue() {
		return nodeValue;
	}

	public void setNodeValue(String nodeValue) {
		this.nodeValue = nodeValue;
	}

	@Override
	public String toString() {
		return "DeleteNodeWrapper{" +
				"projectID=" + projectID +
				", nodeValue='" + nodeValue + '\'' +
				'}';
	}

}
