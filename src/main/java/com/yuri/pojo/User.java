package com.yuri.pojo;

import java.util.Date;

public class User {
    private String userId;
    private String username;
    private String password;
    private String userSex;
    private String phoneNumber;
    private String email;
    private Date hireDate;
    private String role;

    public User() {
    }


    public User(String userId, String username, String userSex, String phoneNumber, String email, String role) {
        this.userId = userId;
        this.username = username;
        this.userSex = userSex;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
    }

    public User(String username, String userSex, String phoneNumber, String email, String role) {
        this.username = username;
        this.userSex = userSex;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
    }

    public User(String userId, String username, String password, String userSex, String phoneNumber, String email, Date hireDate, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.userSex = userSex;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.hireDate = hireDate;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userSex='" + userSex + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", hireDate=" + hireDate +
                ", role='" + role + '\'' +
                '}';
    }
}
