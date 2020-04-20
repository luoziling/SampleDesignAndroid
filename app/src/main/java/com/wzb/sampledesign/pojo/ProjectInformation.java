package com.wzb.sampledesign.pojo;

public class ProjectInformation {
    private Integer id;

    private String projectName;

    private Integer layer;

    private Integer userId;

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

    public Integer getLayer() {
        return layer;
    }

    public void setLayer(Integer layer) {
        this.layer = layer;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ProjectInformation{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", layer=" + layer +
                ", userId=" + userId +
                '}';
    }
}