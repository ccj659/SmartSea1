package com.ccj.smartsea.bean;

import java.io.Serializable;

/**
 * Created by ccj on 2017/3/15.
 */
public class User implements Serializable {

    public String ip;
    public String conn;


    public User(String ip, String conn) {
        this.ip = ip;
        this.conn = conn;
    }


    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "ip='" + ip + '\'' +
                ", conn='" + conn + '\'' +
                '}';
    }
}
