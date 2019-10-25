package com.wzb.sampledesign.pojo;

public class DocInfo1 {
    private Integer id;

    private String docname;

    private String docid;

    private String dochospital;

    private String 科室;

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

    public String getDochospital() {
        return dochospital;
    }

    public void setDochospital(String dochospital) {
        this.dochospital = dochospital == null ? null : dochospital.trim();
    }

    public String get科室() {
        return 科室;
    }

    public void set科室(String 科室) {
        this.科室 = 科室 == null ? null : 科室.trim();
    }
}