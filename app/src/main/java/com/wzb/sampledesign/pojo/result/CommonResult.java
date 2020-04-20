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

public class CommonResult {
    boolean flag;
    String reviews;

    public CommonResult() {
    }

    public CommonResult(boolean flag, String reviews) {
        this.flag = flag;
        this.reviews = reviews;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "CommonResult{" +
                "flag=" + flag +
                ", reviews='" + reviews + '\'' +
                '}';
    }
}
