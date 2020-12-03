package com.example.adminlogin;

public class StudentProfile {
    public String sclass,firstname,middlename,lastname,emailID,phoneNo,division,dept,registerationID,password;

    public StudentProfile(){}

    public StudentProfile(String registerationID,String sclass, String firstname, String middlename, String lastname, String emailID, String phoneNo, String division, String dept,  String password) {
        this.registerationID = registerationID;
        this.sclass = sclass;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.emailID = emailID;
        this.phoneNo = phoneNo;
        this.division = division;
        this.dept = dept;
        this.password = password;
    }

    public String getSclass() {
        return sclass;
    }

    public void setSclass(String sclass) {
        this.sclass = sclass;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getRegisterationID() {
        return registerationID;
    }

    public void setRegisterationID(String registerationID) {
        this.registerationID = registerationID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
