package com.yangcong345.webpage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;


/**
 * Author: xumin
 * Date: 2018/2/24
 */
public class Module {
    public interface Contract {
        Context getContext();

        boolean disableYinLianPay();

        boolean needInterceptUrl(String url);

        boolean isWhiteListUseful();

        void onBaseBridgeWebViewActivityResult(Activity activity, int requestCode, int resultCode, Intent data);

        void onBaseBridgeWebViewFragmentResult(Activity activity, int requestCode, int resultCode, Intent data);

        boolean isSupportHardwareAcceleratedForAll();

        String getVersionName();

        String getChannel();

        String getUserId();

        String getToken();

        String getRole();

    }

    private static Contract sContract;

    public static void setup(Contract contract) {
        if (contract == null || sContract != null) {
            throw new IllegalStateException();
        }
        sContract = contract;
        onSetup();
    }

    public static Contract getContract() {
        if (sContract == null) {
            throw new IllegalStateException();
        }

        return sContract;
    }

    private static void onSetup() {
    }
}
