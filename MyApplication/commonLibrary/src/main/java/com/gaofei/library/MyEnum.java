package com.gaofei.library;

/**
 * Created by gaofei on 03/01/2018.
 */

public enum MyEnum {
    IH_TYPE_JACKET("body"),
    IH_TYPE_UNDEREnu_CLOTHING("leg"),
    IH_TYPE_SHOES("foot"),
    IH_TYPE_HAIR("head"),
    IH_TYPE_DECORATE("decorate");
    public String mName;

    MyEnum(String name) {
        this.mName = name;
    }


    @Override
    public String toString() {
        return mName;
    }

}
