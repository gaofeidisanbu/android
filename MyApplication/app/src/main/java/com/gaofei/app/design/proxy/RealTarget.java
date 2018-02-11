package com.gaofei.app.design.proxy;

import com.gaofei.app.design.Component;

/**
 * Created by gaofei on 11/02/2018.
 */

public class RealTarget implements Component{
    public RealTarget(){
    }
    @Override
    public void sample() {
        System.out.println(this.getClass().getSimpleName());
    }
}
