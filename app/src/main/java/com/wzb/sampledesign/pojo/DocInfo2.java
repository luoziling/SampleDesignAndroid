package com.wzb.sampledesign.pojo;

public class DocInfo2 {
    private Integer id;

    private String docname;

    private String docid;

    private String departments;

    private String occ;

    private String napm;

    private String nacc;

    private String erate;

    private String tuweiprice;

    private String shihuaprice;

    private String ncomment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname == null ? null : docname.trim();
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid == null ? null : docid.trim();
    }

    public String getDepartments() {
        return departments;
    }

    public void setDepartments(String departments) {
        this.departments = departments == null ? null : departments.trim();
    }

    public String getOcc() {
        return occ;
    }

    public void setOcc(String occ) {
        this.occ = occ == null ? null : occ.trim();
    }

    public String getNapm() {
        return napm;
    }

    public void setNapm(String napm) {
        this.napm = napm == null ? null : napm.trim();
    }

    public String getNacc() {
        return nacc;
    }

    public void setNacc(String nacc) {
        this.nacc = nacc == null ? null : nacc.trim();
    }

    public String getErate() {
        return erate;
    }

    public void setErate(String erate) {
        this.erate = erate == null ? null : erate.trim();
    }

    public String getTuweiprice() {
        return tuweiprice;
    }

    public void setTuweiprice(String tuweiprice) {
        this.tuweiprice = tuweiprice == null ? null : tuweiprice.trim();
    }

    public String getShihuaprice() {
        return shihuaprice;
    }

    public void setShihuaprice(String shihuaprice) {
        this.shihuaprice = shihuaprice == null ? null : shihuaprice.trim();
    }

    public String getNcomment() {
        return ncomment;
    }

    public void setNcomment(String ncomment) {
        this.ncomment = ncomment == null ? null : ncomment.trim();
    }

    @Override
    public String toString() {
        return "DocInfo2{" +
                "id=" + id +
                ", docname='" + docname + '\'' +
                ", docid='" + docid + '\'' +
                ", departments='" + departments + '\'' +
                ", occ='" + occ + '\'' +
                ", napm='" + napm + '\'' +
                ", nacc='" + nacc + '\'' +
                ", erate='" + erate + '\'' +
                ", tuweiprice='" + tuweiprice + '\'' +
                ", shihuaprice='" + shihuaprice + '\'' +
                ", ncomment='" + ncomment + '\'' +
                '}';
    }
}