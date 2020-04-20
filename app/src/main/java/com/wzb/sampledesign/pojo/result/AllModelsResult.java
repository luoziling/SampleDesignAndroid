package com.wzb.sampledesign.pojo.result;


import com.wzb.sampledesign.pojo.ProjectInformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Satsuki
 * @time 2020/4/10 16:11
 * @description:
 */


public class AllModelsResult {
    boolean flag;
    List<ProjectInformation> projectList;
    String reviews;

    public AllModelsResult() {
    }

    public AllModelsResult(boolean flag, List<ProjectInformation> projectList, String reviews) {
        this.flag = flag;
        this.projectList = projectList;
        this.reviews = reviews;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<ProjectInformation> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ProjectInformation> projectList) {
        this.projectList = projectList;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "AllModelsResult{" +
                "flag=" + flag +
                ", projectList=" + projectList +
                ", reviews='" + reviews + '\'' +
                '}';
    }
}
