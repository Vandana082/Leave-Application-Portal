package com.example.LeaveApplicationPortal.Entity;


import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class User {

    private String username;
    private String userid;
    private String email;
    private String role;
    private String password;
    private String category;

    public User(String username, String userid, String email, String role, String password, String category) {
        this.username = username;
        this.userid = userid;
        this.email = email;
        this.role = role;
        this.password = password;
        this.category = category;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", userid='" + userid + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
