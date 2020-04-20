package com.wzb.sampledesign.pojo.result;

import com.wzb.sampledesign.pojo.EcUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Satsuki
 * @time 2020/4/8 18:36
 * @description: 整合用户登陆信息
 */
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//@Accessors(chain = true)
public class UserResult {
    Boolean flag;
    EcUser user;
    String reviews;

    public UserResult() {
    }

    public UserResult(Boolean flag, EcUser user, String reviews) {
        this.flag = flag;
        this.user = user;
        this.reviews = reviews;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public EcUser getUser() {
        return user;
    }

    public void setUser(EcUser user) {
        this.user = user;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "UserResult{" +
                "flag=" + flag +
                ", user=" + user +
                ", reviews='" + reviews + '\'' +
                '}';
    }
}
