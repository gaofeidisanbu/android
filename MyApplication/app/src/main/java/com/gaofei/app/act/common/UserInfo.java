package com.gaofei.app.act.common;

/**
 * Created by gaofei on 30/01/2018.
 */

public class UserInfo {
    public   String mFirstName;
    public  String mLastName;

    public UserInfo(String firstName,String lastName){
        this.mFirstName = firstName;
        this.mLastName = lastName;
    }

    public String getLastName(){
        return mLastName;
    }
}
