package com.wzb.sampledesign.pojo;

public class MatrixStorage {
    private Integer id;

    private String value;

    private Integer i;

    private Integer j;

    private Double matrixValue;

    private String projectName;

    private Integer userId;

    private Integer projectId;

    @Override
    public String toString() {
        return "MatrixStorage{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", i=" + i +
                ", j=" + j +
                ", matrixValue=" + matrixValue +
                ", projectName='" + projectName + '\'' +
                ", userId=" + userId +
                ", projectId=" + projectId +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public Integer getI() {
        return i;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    public Integer getJ() {
        return j;
    }

    public void setJ(Integer j) {
        this.j = j;
    }

    public Double getMatrixValue() {
        return matrixValue;
    }

    public void setMatrixValue(Double matrixValue) {
        this.matrixValue = matrixValue;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}