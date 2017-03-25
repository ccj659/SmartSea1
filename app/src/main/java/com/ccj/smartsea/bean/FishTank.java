package com.ccj.smartsea.bean;

import java.io.Serializable;

/**
 * Created by ccj on 2017/3/15.
 */

public class FishTank implements Serializable {

    public String id; //温度

    public String depth; //深度
    public String turbidness; //浊度
    public String temp; //温度
    public String name;

    public FishTank(String id, String depth, String turbidness, String temp, String name) {
        this.id = id;
        this.depth = depth;
        this.turbidness = turbidness;
        this.temp = temp;
        this.name = name;
    }


    public FishTank() {


    }
}
