package com.ccj.smartsea.bean;

import java.io.Serializable;

/**
 * Created by ccj on 2017/3/15.
 */

public class OutEnvironment implements Serializable {

    public String id;
    public String name;
    public String temp; //温度
    public String pm25; //PM2.5
    public String pm10; //PM2.5
    public String tempIn; //湿度
    public String smoke; //浊度

    public OutEnvironment(String id, String name, String temp, String pm25, String smoke) {
        this.id = id;
        this.name = name;
        this.temp = temp;
        this.pm25 = pm25;
        this.smoke = smoke;
    }


    public OutEnvironment() {


    }

    @Override
    public String toString() {
        return "OutEnvironment{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", temp='" + temp + '\'' +
                ", pm25='" + pm25 + '\'' +
                ", pm10='" + pm10 + '\'' +
                ", tempIn='" + tempIn + '\'' +
                ", smoke='" + smoke + '\'' +
                '}';
    }
}
