package com.example.adminlogin;

public class AdminD {
    private String name,phone,emailID;

    public  AdminD(){}

    public AdminD(String name, String phone, String emailID) {
        this.name = name;
        this.phone = phone;
        this.emailID = emailID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }
}
