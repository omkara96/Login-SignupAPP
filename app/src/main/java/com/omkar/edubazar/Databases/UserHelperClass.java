package com.omkar.edubazar.Databases;

public class UserHelperClass {
    String fullName ,phoneNo,eMail,userName ,passWord ,date ,gender ,college ,course ,city;

   public  UserHelperClass(){}

    public UserHelperClass(String fullName, String phoneNo, String eMail, String userName, String passWord, String date, String gender, String college, String course, String city) {
        this.fullName = fullName;
        this.phoneNo = phoneNo;
        this.eMail = eMail;
        this.userName = userName;
        this.passWord = passWord;
        this.date = date;
        this.gender = gender;
        this.college = college;
        this.course = course;
        this.city = city;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
