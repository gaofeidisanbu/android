package com.gift.animation;

/**
 * Created by gaofei3 on 2024/11/1
 * Describe:
 */
public interface IMessageConvertFactory {

    IAnimationInfo convertMessageToAnimation(IMessage giftMessage);
    boolean isHandlerMessage(IMessage giftMessage);
}
