package com.example.adminlogin;

public class RTrends {
    private String act,date;

    public RTrends(String act,String date) {
      // this.gname = gname;
        this.act = act;
       this.date = date;
    }

   /* public String getGname() {
        return gname;
    }*/

  /* public void setGname(String gname) {
        this.gname = gname;
    }*/

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
