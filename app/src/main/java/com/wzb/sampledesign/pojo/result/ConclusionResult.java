package com.wzb.sampledesign.pojo.result;


import com.wzb.sampledesign.pojo.Conclusion;

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


public class ConclusionResult {
    boolean flag;
    List<Conclusion> conclusions;
    String reviews;

    public ConclusionResult() {
    }

    public ConclusionResult(boolean flag, List<Conclusion> conclusions, String reviews) {
        this.flag = flag;
        this.conclusions = conclusions;
        this.reviews = reviews;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<Conclusion> getConclusions() {
        return conclusions;
    }

    public void setConclusions(List<Conclusion> conclusions) {
        this.conclusions = conclusions;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "ConclusionResult{" +
                "flag=" + flag +
                ", conclusions=" + conclusions +
                ", reviews='" + reviews + '\'' +
                '}';
    }
}
