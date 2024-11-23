package com.gift.animation.businiss;

import com.gift.animation.AnimationManager;
import com.gift.animation.IMessage;
import com.gift.animation.message.GiftMessageConvertFactory;
import com.gift.animation.video.VideoTaskFactory;

/**
 * Created by gaofei3 on 2024/11/1
 * Describe:
 */
public class AnimationBusinessManager {
    AnimationManager animationManager;

    public void initialize() {
        animationManager = new AnimationManager();
        animationManager.getGiftMessageConvertManager().register(new GiftMessageConvertFactory());
        animationManager.getAnimationTaskManager().register(new VideoTaskFactory());
    }

    void addToGiftMessage(IMessage giftMessage) {
        animationManager.addGiftMessage(giftMessage);
    }
}
