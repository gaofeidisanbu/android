package com.gift.animation.video;

import com.gift.animation.AnimationManager;
import com.gift.animation.IAnimationInfo;
import com.gift.animation.player.IAnimationPlayer;
import com.gift.animation.task.IAnimationTask;
import com.gift.animation.task.IAnimationTaskFactory;

/**
 * Created by gaofei3 on 2024/11/1
 * Describe:
 */
public class VideoTaskFactory implements IAnimationTaskFactory {
    @Override
    public boolean isHandleAnimation(IAnimationInfo animationInfo) {
        return false;
    }

    @Override
    public IAnimationTask createTask(AnimationManager animationManager, IAnimationInfo animationInfo) {
        return new VideoAnimationTask(animationManager);
    }

    @Override
    public IAnimationPlayer getPlayer() {
        return null;
    }
}
