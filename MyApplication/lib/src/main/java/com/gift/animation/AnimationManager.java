package com.gift.animation;

import com.gift.animation.dispatch.DefaultDispatch;
import com.gift.animation.dispatch.IDispatch;
import com.gift.animation.task.AnimationTaskManager;
import com.gift.animation.task.IAnimationTask;

public class AnimationManager {
    private MessageConvertManager giftMessageConvertManager;
    private AnimationTaskManager animationTaskManager;

    private IDispatch dispatch;
    public void initialize(IDispatch dispatch) {
        giftMessageConvertManager = new MessageConvertManager();
        animationTaskManager = new AnimationTaskManager(this);
        if (dispatch == null) {
            dispatch = new DefaultDispatch();
        }
    }
    public void addGiftMessage(IMessage giftMessage) {
        IAnimationInfo animationInfo = giftMessageConvertManager.convertMessageToAnimation(giftMessage);
        if (animationInfo == null) {
            return;
        }
        IAnimationTask animationTask = animationTaskManager.createTask(animationInfo);
        animationTask.enqueue();
    }

    public MessageConvertManager getGiftMessageConvertManager() {
        return giftMessageConvertManager;
    }

    public AnimationTaskManager getAnimationTaskManager() {
        return animationTaskManager;
    }

    public IDispatch getDispatch() {
        return dispatch;
    }
}
