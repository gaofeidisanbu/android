package com.gift.animation.dispatch;

import com.gift.animation.task.IAnimationTask;

/**
 * Created by gaofei3 on 2024/11/1
 * Describe:
 */
public interface IDispatch {

    boolean enqueue(IAnimationTask animationTask);
}
