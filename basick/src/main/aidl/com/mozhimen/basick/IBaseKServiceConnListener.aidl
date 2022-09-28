// IBaseKServiceConnListener.aidl
package com.mozhimen.basick;

// Declare any non-default types here with import statements
import com.mozhimen.basick.IBaseKServiceResListener;

interface IBaseKServiceConnListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onServiceStart();
    void registerListener(IBaseKServiceResListener listener);
    void unRegisterListener(IBaseKServiceResListener listener);
    void onServiceStop();
}