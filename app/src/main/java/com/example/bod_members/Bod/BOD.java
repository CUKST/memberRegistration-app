package com.example.bod_members.Bod;

public class BOD {
    private int id;
    private String name;
    private String developer;
    private byte[] photo;
    private  String position;
    private  String year;
    private String contact;

    public BOD(String name, String developer, byte[] photo, int id, String position, String year, String contact) {
        this.id = id;
        this.name = name;
        this.developer = developer;
        this.photo = photo;
        this.position = position;
        this.year = year;
        this.contact = contact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}