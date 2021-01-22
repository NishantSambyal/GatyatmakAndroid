package com.gatyatmakjyotish.pojo;

public class LoginInfo {

    String token;
    String name,place_of_birth,gender,current_place,email,email_verified_at;
    int id,date_of_birth,time_of_birth,mobile,created_at,updated_at;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCurrent_place() {
        return current_place;
    }

    public void setCurrent_place(String current_place) {
        this.current_place = current_place;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail_verified_at() {
        return email_verified_at;
    }

    public void setEmail_verified_at(String email_verified_at) {
        this.email_verified_at = email_verified_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(int date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public int getTime_of_birth() {
        return time_of_birth;
    }

    public void setTime_of_birth(int time_of_birth) {
        this.time_of_birth = time_of_birth;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public int getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(int updated_at) {
        this.updated_at = updated_at;
    }

    public LoginInfo(String token, String name, String place_of_birth, String gender, String current_place, String email, String email_verified_at, int id, int date_of_birth, int time_of_birth, int mobile, int created_at, int updated_at) {
        this.token = token;
        this.name = name;
        this.place_of_birth = place_of_birth;
        this.gender = gender;
        this.current_place = current_place;
        this.email = email;
        this.email_verified_at = email_verified_at;
        this.id = id;
        this.date_of_birth = date_of_birth;
        this.time_of_birth = time_of_birth;
        this.mobile = mobile;
        this.created_at = created_at;
        this.updated_at = updated_at;

    }
}
