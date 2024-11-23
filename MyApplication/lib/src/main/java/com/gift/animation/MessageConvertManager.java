package com.gift.animation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaofei3 on 2024/11/1
 * Describe:
 */
public class MessageConvertManager {
    final private List<IMessageConvertFactory> mList = new ArrayList<>();

    public void register(IMessageConvertFactory giftMessage) {
        if (!mList.contains(giftMessage)) {
            mList.add(giftMessage);
        }
    }

    public void unRegister(IMessageConvertFactory giftMessage) {
        if (mList.contains(giftMessage)) {
            mList.remove(giftMessage);
        }
    }

    public IAnimationInfo convertMessageToAnimation(IMessage giftMessage) {
        for (IMessageConvertFactory giftMessageConvertFactory : mList) {
            if (giftMessageConvertFactory.isHandlerMessage(giftMessage)) {
                giftMessageConvertFactory.convertMessageToAnimation(giftMessage);
            }
        }
        return null;
    }
}
