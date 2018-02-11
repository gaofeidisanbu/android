package com.gaofei.app.design.adpter;

import com.gaofei.app.design.Component;

/**
 * Created by gaofei on 11/02/2018.
 */

public class Adapter extends Adaptee implements Component {
    @Override
    public void sample() {
        before();
    }
}
