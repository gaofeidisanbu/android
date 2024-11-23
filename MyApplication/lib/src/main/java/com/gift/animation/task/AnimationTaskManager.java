package com.gift.animation.task;

import com.gift.animation.AnimationManager;
import com.gift.animation.IAnimationInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaofei3 on 2024/11/1
 * Describe:
 */
public class AnimationTaskManager {
    private AnimationManager animationManager;
    final private List<IAnimationTaskFactory> mList = new ArrayList<>();

    public AnimationTaskManager(AnimationManager animationManager) {
        this.animationManager = animationManager;
    }

    public void register(IAnimationTaskFactory giftMessage) {
        if (!mList.contains(giftMessage)) {
            mList.add(giftMessage);
        }
    }

    public void unRegister(IAnimationTaskFactory giftMessage) {
        if (mList.contains(giftMessage)) {
            mList.remove(giftMessage);
        }
    }

    public IAnimationTask createTask(IAnimationInfo animationInfo) {
        for (IAnimationTaskFactory animationTaskFactory : mList) {
            if (animationTaskFactory.isHandleAnimation(animationInfo)) {
                return animationTaskFactory.createTask(animationManager, animationInfo);
            }
        }
        return null;
    }
}
