package com.wzb.sampledesign.pojo;

public class NormalizationWeight {
    private Integer id;

    private String projectName;

    private String value;

    private String nextValue;

    private Double weight;

    private Integer userId;

    private Integer projectId;

    @Override
    public String toString() {
        return "NormalizationWeight{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", value='" + value + '\'' +
                ", nextValue='" + nextValue + '\'' +
                ", weight=" + weight +
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getNextValue() {
        return nextValue;
    }

    public void setNextValue(String nextValue) {
        this.nextValue = nextValue == null ? null : nextValue.trim();
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
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