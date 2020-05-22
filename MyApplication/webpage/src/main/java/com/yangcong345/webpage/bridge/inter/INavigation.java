package com.yangcong345.webpage.bridge.inter;

import java.util.Map;

/**
 * Created by gaofei on 2017/7/11.
 */

public interface INavigation {

    void browserForward();

    void browserBack();

    void browserClose();

    void browserTitle(Map<String, String> data);

    void browserHideLoading();
}
