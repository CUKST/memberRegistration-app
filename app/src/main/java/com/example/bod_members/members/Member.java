package com.example.bod_members.members;

public class Member {
    private int id;
    private String naming;
    private String year;
    private byte[] image;
    private  String roll_no;
    private  String phone;
    private String email;

    public Member(String naming, String year, byte[] image,int id,  String roll_no, String phone, String email) {
        this.id = id;
        this.naming = naming;
        this.year = year;
        this.image = image;
        this.roll_no = roll_no;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaming() {
        return naming;
    }

    public void setNaming(String naming) {
        this.naming = naming;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getRoll_no() {
        return roll_no;
    }

    public void setRoll_no(String roll_no) {
        this.roll_no = roll_no;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}