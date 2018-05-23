package com.main.dao.comm.entity;

import java.util.HashMap;

public class Account {
    /**用户名*/
    private String username;

    /**用户密码*/
    private String password;

    /**用户等级*/
    private String userlevel;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getUserlevel() {
        return userlevel;
    }

    public void setUserlevel(String userlevel) {
        this.userlevel = userlevel == null ? null : userlevel.trim();
    }

    public HashMap getPrimaryKey() {
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("username",this.username);
        return map;
    }
}