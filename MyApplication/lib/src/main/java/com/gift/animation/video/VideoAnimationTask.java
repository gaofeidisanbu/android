package com.gift.animation.video;

import com.gift.animation.AnimationManager;
import com.gift.animation.task.IAnimationTask;

/**
 * Created by gaofei3 on 2024/11/1
 * Describe:
 */
public class VideoAnimationTask implements IAnimationTask {

    private final AnimationManager animationManager;

    public VideoAnimationTask(AnimationManager animationManager) {
        this.animationManager = animationManager;
    }

    @Override
    public void run() {

    }

    @Override
    public void enqueue() {
        animationManager.getDispatch().enqueue(this);
    }
}
