package com.orellana.androidinstagram;

public class Users {
    String userid, email, userName;

    public Users(String userid, String email, String userName) {
        this.userid = userid;
        this.email = email;
        this.userName = userName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
