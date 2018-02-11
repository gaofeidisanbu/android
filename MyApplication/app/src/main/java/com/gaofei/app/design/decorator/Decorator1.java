package com.gaofei.app.design.decorator;

import com.gaofei.app.design.Component;

/**
 * Created by gaofei on 11/02/2018.
 */

public class Decorator1 extends Decorator{
    public Decorator1(Component component) {
        super(component);
    }

    @Override
    public void sample() {
        System.out.println(this.getClass().getSimpleName());

        super.sample();
    }
}
