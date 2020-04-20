package com.wzb.sampledesign.pojo.result;


import com.wzb.sampledesign.pojo.MatrixStorage;

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


public class MatrixResult {
    boolean flag;
    List<MatrixStorage> matrixStorages;
    String reviews;

    public MatrixResult() {
    }

    public MatrixResult(boolean flag, List<MatrixStorage> matrixStorages, String reviews) {
        this.flag = flag;
        this.matrixStorages = matrixStorages;
        this.reviews = reviews;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<MatrixStorage> getMatrixStorages() {
        return matrixStorages;
    }

    public void setMatrixStorages(List<MatrixStorage> matrixStorages) {
        this.matrixStorages = matrixStorages;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "MatrixResult{" +
                "flag=" + flag +
                ", matrixStorages=" + matrixStorages +
                ", reviews='" + reviews + '\'' +
                '}';
    }
}
