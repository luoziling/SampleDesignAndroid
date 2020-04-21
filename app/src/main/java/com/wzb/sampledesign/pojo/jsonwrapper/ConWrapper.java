package com.wzb.sampledesign.pojo.jsonwrapper;

/**
 * Date: 2020/4/21
 * Author:Satsuki
 * Description:
 */
public class ConWrapper {
	private Integer projectID;
	private Integer userID;

	public ConWrapper() {
	}

	public ConWrapper(Integer projectID, Integer userID) {
		this.projectID = projectID;
		this.userID = userID;
	}

	public Integer getProjectID() {
		return projectID;
	}

	public void setProjectID(Integer projectID) {
		this.projectID = projectID;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	@Override
	public String toString() {
		return "ConWrapper{" +
				"projectID=" + projectID +
				", userID=" + userID +
				'}';
	}
}
