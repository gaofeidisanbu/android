package com.gift.animation.task;

import com.gift.animation.AnimationManager;
import com.gift.animation.IAnimationInfo;
import com.gift.animation.player.IAnimationPlayer;

/**
 * Created by gaofei3 on 2024/11/1
 * Describe:
 */
public interface IAnimationTaskFactory {

    boolean isHandleAnimation(IAnimationInfo animationInfo);

    IAnimationTask createTask(AnimationManager animationManager, IAnimationInfo animationInfo);

    IAnimationPlayer getPlayer();
}
