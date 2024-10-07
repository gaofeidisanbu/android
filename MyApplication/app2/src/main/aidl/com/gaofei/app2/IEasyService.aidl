// IEasyService.aidl
package com.gaofei.app2;
import android.graphics.Bitmap;
// Declare any non-default types here with import statements

interface IEasyService {
     int connect(String mes);
     void disConnect(String mes);
     Bitmap getBitmap();
     byte[] getByteArray(in Bitmap bitmap);
      void attachBinder(in IEasyService binder);
}
