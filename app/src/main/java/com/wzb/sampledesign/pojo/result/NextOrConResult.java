package com.wzb.sampledesign.pojo.result;


import com.wzb.sampledesign.pojo.AdjacentClosure;
import com.wzb.sampledesign.pojo.Conclusion;
import com.wzb.sampledesign.pojo.TreeNodeContent;

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


public class NextOrConResult {
    boolean flag;
    List<AdjacentClosure> adjacentClosures;
    List<Conclusion> conclusions;
    List<TreeNodeContent> treeNodeContents;
    String reviews;

    public NextOrConResult() {
    }

    public NextOrConResult(boolean flag, List<AdjacentClosure> adjacentClosures, List<Conclusion> conclusions, List<TreeNodeContent> treeNodeContents, String reviews) {
        this.flag = flag;
        this.adjacentClosures = adjacentClosures;
        this.conclusions = conclusions;
        this.treeNodeContents = treeNodeContents;
        this.reviews = reviews;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<AdjacentClosure> getAdjacentClosures() {
        return adjacentClosures;
    }

    public void setAdjacentClosures(List<AdjacentClosure> adjacentClosures) {
        this.adjacentClosures = adjacentClosures;
    }

    public List<Conclusion> getConclusions() {
        return conclusions;
    }

    public void setConclusions(List<Conclusion> conclusions) {
        this.conclusions = conclusions;
    }

    public List<TreeNodeContent> getTreeNodeContents() {
        return treeNodeContents;
    }

    public void setTreeNodeContents(List<TreeNodeContent> treeNodeContents) {
        this.treeNodeContents = treeNodeContents;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "NextOrConResult{" +
                "flag=" + flag +
                ", adjacentClosures=" + adjacentClosures +
                ", conclusions=" + conclusions +
                ", treeNodeContents=" + treeNodeContents +
                ", reviews='" + reviews + '\'' +
                '}';
    }
}
