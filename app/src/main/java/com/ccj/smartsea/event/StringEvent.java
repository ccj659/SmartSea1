package com.ccj.smartsea.event;

/**
 * Created by chenchangjun on 17/3/25.
 */


/**
 * 传递String类型的event
 */
public  class StringEvent {
    private String mMsg;

    public StringEvent(String msg) {
        // TODO Auto-generated constructor stub
        this.mMsg = msg;
    }

    public String getMsg() {
        return mMsg;
    }
}