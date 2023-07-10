// IBaseServiceResListener.aidl
package com.mozhimen.basick.elemk.android.app.commons;

// Declare any non-default types here with import statements

interface IBaseServiceResListener {
    void onResInt(int resInt);
    void onResLong(long resLong);
    void onResBoolean(boolean resBoolean);
    void onResFloat(float resFloat);
    void onResDouble(double resDouble);
    void onResString(String resString);
}