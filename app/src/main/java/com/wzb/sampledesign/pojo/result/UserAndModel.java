package com.wzb.sampledesign.pojo.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Satsuki
 * @time 2019/10/30 20:52
 * @description:
 *
 * 封装与redis交互的当前用户名和模型
 */
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//@Accessors(chain = true)
public class UserAndModel {
    private String username;
    private String pronectName;

    public UserAndModel() {
    }

    public UserAndModel(String username, String pronectName) {
        this.username = username;
        this.pronectName = pronectName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPronectName() {
        return pronectName;
    }

    public void setPronectName(String pronectName) {
        this.pronectName = pronectName;
    }

    @Override
    public String toString() {
        return "UserAndModel{" +
                "username='" + username + '\'' +
                ", pronectName='" + pronectName + '\'' +
                '}';
    }
}
