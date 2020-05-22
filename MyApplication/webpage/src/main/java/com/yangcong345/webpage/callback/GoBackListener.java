package com.yangcong345.webpage.callback;

/**
 * @author xianchaohua
 */

public interface GoBackListener {

    /**
     * 用于监听消费 返回事件的方法
     * @return true：表示监听器消费了返回事件； false：监听起没有消费返回事件，需要执行默认处理
     */
    boolean goBack();

}
