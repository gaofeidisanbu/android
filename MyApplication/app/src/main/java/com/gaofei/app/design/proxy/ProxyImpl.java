package com.gaofei.app.design.proxy;

import com.gaofei.app.design.Component;

/**
 * Created by gaofei on 11/02/2018.
 */

public class ProxyImpl implements Component {
    private Component mComponent ;

    public ProxyImpl(){
        mComponent = new RealTarget();
    }


    @Override
    public void sample() {
        this.mComponent.sample();
    }
}
