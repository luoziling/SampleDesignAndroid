package com.wzb.sampledesign.pojo.jsonwrapper;

/**
 * Date: 2020/4/16
 * Author:Satsuki
 * Description:
 */
public class ConcalWrapper {
	private Integer projectID;
	private String projectName;
	private Integer userID;

	public ConcalWrapper() {
	}

	public ConcalWrapper(Integer projectID, String projectName, Integer userID) {
		this.projectID = projectID;
		this.projectName = projectName;
		this.userID = userID;
	}

	public Integer getProjectID() {
		return projectID;
	}

	public void setProjectID(Integer projectID) {
		this.projectID = projectID;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	@Override
	public String toString() {
		return "ConcalWrapper{" +
				"projectID=" + projectID +
				", projectName='" + projectName + '\'' +
				", userID=" + userID +
				'}';
	}
}
