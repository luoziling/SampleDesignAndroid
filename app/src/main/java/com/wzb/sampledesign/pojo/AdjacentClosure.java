package com.wzb.sampledesign.pojo;

public class AdjacentClosure {
    private Integer id;

    private String projectName;

    private Integer ancestor;

    private Integer descendant;

    private Integer depth;

    private Integer projectId;

    @Override
    public String toString() {
        return "AdjacentClosure{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", ancestor=" + ancestor +
                ", descendant=" + descendant +
                ", depth=" + depth +
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

    public Integer getAncestor() {
        return ancestor;
    }

    public void setAncestor(Integer ancestor) {
        this.ancestor = ancestor;
    }

    public Integer getDescendant() {
        return descendant;
    }

    public void setDescendant(Integer descendant) {
        this.descendant = descendant;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

}