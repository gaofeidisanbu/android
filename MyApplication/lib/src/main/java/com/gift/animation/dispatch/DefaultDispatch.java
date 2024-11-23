package com.gift.animation.dispatch;

import com.gift.animation.task.IAnimationTask;

/**
 * Created by gaofei3 on 2024/11/1
 * Describe:
 */
public class DefaultDispatch implements IDispatch{
    @Override
    public boolean enqueue(IAnimationTask animationTask) {
        return false;
    }
}
