package com.gaofei.app.design.decorator;

import com.gaofei.app.design.Component;

/**
 * Created by gaofei on 11/02/2018.
 */

public class ConcreteComponent implements Component {
    @Override
    public void sample() {
        System.out.println(this.getClass().getSimpleName());
    }
}
