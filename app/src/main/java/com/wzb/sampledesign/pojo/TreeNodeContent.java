package com.wzb.sampledesign.pojo;

public class TreeNodeContent {
    private Integer id;

    private String value;

    private String projectName;

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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    @Override
    public String toString() {
        return "TreeNodeContent{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", projectName='" + projectName + '\'' +
                '}';
    }
}