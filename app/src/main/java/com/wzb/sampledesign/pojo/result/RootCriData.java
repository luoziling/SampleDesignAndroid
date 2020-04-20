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

    public RootCriData() {
    }

    public RootCriData(Double[][] data, String projectName, List<String> nextList) {
        this.data = data;
        this.projectName = projectName;
        this.nextList = nextList;
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

    @Override
    public String toString() {
        return "RootCriData{" +
                "data=" + Arrays.toString(data) +
                ", projectName='" + projectName + '\'' +
                ", nextList=" + nextList +
                '}';
    }
}
