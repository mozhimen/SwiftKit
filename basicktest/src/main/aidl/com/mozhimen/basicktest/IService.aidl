// IService.aidl
package com.mozhimen.basicktest;

// Declare any non-default types here with import statements

interface IService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onStart();
    void onRegesterCallback(ICallback callback);
    void onUnRegesterCallback(ICallback callback);
}