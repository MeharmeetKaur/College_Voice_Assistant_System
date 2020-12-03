package com.example.adminlogin;

public class course_1 {
    private int cid;
    private String detail;

    public course_1(int cid, String detail) {
        this.cid = cid;
      //  this.name = name;
        this.detail = detail;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

   /* public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }*/

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
