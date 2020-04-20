package com.wzb.sampledesign.pojo.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Satsuki
 * @time 2020/4/10 16:11
 * @description:
 */


public class CreateResult {
    boolean flag;
    Integer projectID;
    String reviews;

    public CreateResult() {
    }

    public CreateResult(boolean flag, Integer projectID, String reviews) {
        this.flag = flag;
        this.projectID = projectID;
        this.reviews = reviews;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Integer getProjectID() {
        return projectID;
    }

    public void setProjectID(Integer projectID) {
        this.projectID = projectID;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "CreateResult{" +
                "flag=" + flag +
                ", projectID=" + projectID +
                ", reviews='" + reviews + '\'' +
                '}';
    }
}
