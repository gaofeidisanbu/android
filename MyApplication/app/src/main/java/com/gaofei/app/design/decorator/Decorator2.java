package com.gaofei.app.design.decorator;

import com.gaofei.app.design.Component;

/**
 * Created by gaofei on 11/02/2018.
 */

public class Decorator2 extends Decorator{
    public Decorator2(Component component) {
        super(component);
    }

    @Override
    public void sample() {
        System.out.println(this.getClass().getSimpleName());
        super.sample();
    }
}
