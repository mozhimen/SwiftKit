// IBaseKServiceResListener.aidl
package com.mozhimen.basick;

// Declare any non-default types here with import statements

interface IBaseKServiceResListener {
    void onResInt(int resInt);
    void onResLong(long resLong);
    void onResBoolean(boolean resBoolean);
    void onResFloat(float resFloat);
    void onResDouble(double resDouble);
    void onResString(String resString);
}