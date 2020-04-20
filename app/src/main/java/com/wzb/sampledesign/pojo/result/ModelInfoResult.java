package com.wzb.sampledesign.pojo.result;



import com.wzb.sampledesign.pojo.AdjacentClosure;
import com.wzb.sampledesign.pojo.TreeNodeContent;

import java.util.List;

/**
 * @author Satsuki
 * @time 2020/4/12 14:39
 * @description:
 */
public class ModelInfoResult {
    boolean flag;
    List<TreeNodeContent> nodeContents;
    List<AdjacentClosure> adjacentClosures;
    String reviews;

    public ModelInfoResult() {
    }

    public ModelInfoResult(boolean flag, List<TreeNodeContent> nodeContents, List<AdjacentClosure> adjacentClosures, String reviews) {
        this.flag = flag;
        this.nodeContents = nodeContents;
        this.adjacentClosures = adjacentClosures;
        this.reviews = reviews;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<TreeNodeContent> getNodeContents() {
        return nodeContents;
    }

    public void setNodeContents(List<TreeNodeContent> nodeContents) {
        this.nodeContents = nodeContents;
    }

    public List<AdjacentClosure> getAdjacentClosures() {
        return adjacentClosures;
    }

    public void setAdjacentClosures(List<AdjacentClosure> adjacentClosures) {
        this.adjacentClosures = adjacentClosures;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "ModelInfoResult{" +
                "flag=" + flag +
                ", nodeContents=" + nodeContents +
                ", adjacentClosures=" + adjacentClosures +
                ", reviews='" + reviews + '\'' +
                '}';
    }
}
