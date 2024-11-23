package com.gift.animation.message;

import com.gift.animation.IAnimationInfo;
import com.gift.animation.IMessage;
import com.gift.animation.IMessageConvertFactory;

/**
 * Created by gaofei3 on 2024/11/1
 * Describe:
 */
public class GiftMessageConvertFactory implements IMessageConvertFactory {
    @Override
    public IAnimationInfo convertMessageToAnimation(IMessage giftMessage) {
        return null;
    }

    @Override
    public boolean isHandlerMessage(IMessage giftMessage) {
        return false;
    }
}
