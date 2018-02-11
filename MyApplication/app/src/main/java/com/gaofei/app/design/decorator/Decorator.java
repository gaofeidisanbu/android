package com.gaofei.app.design.decorator;

import com.gaofei.app.design.Component;

/**
 * Created by gaofei on 11/02/2018.
 */

public class Decorator implements Component {
    private Component mConcreteComponent;

    public Decorator(Component component) {
        this.mConcreteComponent = component;
    }

    @Override
    public void sample() {
        mConcreteComponent.sample();
    }
}
