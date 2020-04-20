package com.wzb.sampledesign.pojo.jsonwrapper;

import com.wzb.sampledesign.pojo.TreeNodeContent;

import java.util.List;

/**
 * Date: 2020/4/11
 * Author:Satsuki
 * Description:
 * 保存节点时封装json数据的包装类
 */
public class SaveNodeWrapper {
	// 新建的节点信息
	TreeNodeContent treeNodeContent;
	// 以新建节点为基准的所有祖先节点内容
	// 用于配合projectID查询祖先节点id
	List<String> nodeList;

	public SaveNodeWrapper() {
	}

	public SaveNodeWrapper(TreeNodeContent treeNodeContent, List<String> nodeList) {
		this.treeNodeContent = treeNodeContent;
		this.nodeList = nodeList;
	}

	public TreeNodeContent getTreeNodeContent() {
		return treeNodeContent;
	}

	public void setTreeNodeContent(TreeNodeContent treeNodeContent) {
		this.treeNodeContent = treeNodeContent;
	}

	public List<String> getNodeList() {
		return nodeList;
	}

	public void setNodeList(List<String> nodeList) {
		this.nodeList = nodeList;
	}

	@Override
	public String toString() {
		return "SaveNodeWrapper{" +
				"treeNodeContent=" + treeNodeContent +
				", nodeList=" + nodeList +
				'}';
	}
}
