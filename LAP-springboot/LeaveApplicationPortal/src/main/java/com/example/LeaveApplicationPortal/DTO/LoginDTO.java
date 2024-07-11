package com.example.LeaveApplicationPortal.DTO;

public class LoginDTO {

    private String userid;
    private String password;

    public LoginDTO(String userid, String password) {
        this.userid = userid;
        this.password = password;
    }

    public LoginDTO() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginDto{" +
                "userid='" + userid + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
