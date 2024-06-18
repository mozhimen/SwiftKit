// IBaseServiceConnListener.aidl
package com.mozhimen.servicek.commons;

// Declare any non-default types here with import statements
import com.mozhimen.servicek.commons.IBaseServiceResListener;

interface IBaseServiceConnListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onServiceStart();
    void registerListener(IBaseServiceResListener listener);
    String launchCommand(String cmd);
    void unRegisterListener(IBaseServiceResListener listener);
    void onServiceStop();
}