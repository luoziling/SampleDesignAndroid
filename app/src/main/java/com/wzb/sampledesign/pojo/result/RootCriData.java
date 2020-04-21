package com.wzb.sampledesign.pojo.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/10/16 14:54
 * @description:
 */
//@AllArgsConstructor //全参构造函数
//@NoArgsConstructor  //无参构造函数
//@Data               //提供类所有属性的get和set
//@Accessors(chain = true) //链式访问setter方法返回this
public class RootCriData {
    private Double[][] data;
    private String projectName;
    private List<String> nextList;

    // 项目和用户id
    private Integer projectID;
    private Integer userID;

    public RootCriData() {
    }

    public RootCriData(Double[][] data, String projectName, List<String> nextList, Integer projectID, Integer userID) {
        this.data = data;
        this.projectName = projectName;
        this.nextList = nextList;
        this.projectID = projectID;
        this.userID = userID;
    }

    public Double[][] getData() {
        return data;
    }

    public void setData(Double[][] data) {
        this.data = data;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<String> getNextList() {
        return nextList;
    }

    public void setNextList(List<String> nextList) {
        this.nextList = nextList;
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
        return "RootCriData{" +
                "data=" + Arrays.toString(data) +
                ", projectName='" + projectName + '\'' +
                ", nextList=" + nextList +
                ", projectID=" + projectID +
                ", userID=" + userID +
                '}';
    }
}
