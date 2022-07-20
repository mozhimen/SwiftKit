package com.mozhimen.abilityk.hotfixk.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager$DexModuleRegisterCallback;
import android.os.Build;
import android.os.IBinder;
import android.os.IInterface;
import android.util.Log;

import com.mozhimen.basick.utilk.UtilKReflect;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName DexConverter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/7/20 17:07
 * @Version 1.0
 */
public class DexConverter {
    private static final String TAG = "DexConverter>>>>>";
    private static final PackageManager[] CACHED_SYNC_PM = {null};

    public static void triggerPMDexOptOnDemand(Context context, String dexPath, String oatPath) {
        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.Q) {
            // Only do this trick on Android Q devices.
            return;
        }

        try {
            final File oatFile = new File(oatPath);
            if (oatFile.exists()) {
                return;
            }
            final PackageManager syncPM = getSynchronizedPackageManager(context);
            final Method registerDexModuleMethod = UtilKReflect.findMethod(syncPM.getClass(), "registerDexModule", String.class, PackageManager$DexModuleRegisterCallback.class);
            try {
                registerDexModuleMethod.invoke(syncPM, dexPath, new PackageManager$DexModuleRegisterCallback() {
                    @Override
                    public void onDexModuleRegistered(String dexModulePath, boolean success, String message) {
                        Log.e(TAG, String.format("[+] onDexModuleRegistered, path: %s, is_success: %s, msg: %s", dexModulePath, success, message));
                    }
                });
            } catch (Throwable ignored) {
                // Ignored.
            }
            if (!oatFile.exists()) {
                registerDexModuleMethod.invoke(syncPM, dexPath, new PackageManager$DexModuleRegisterCallback() {
                    @Override
                    public void onDexModuleRegistered(String dexModulePath, boolean success, String message) {
                        Log.e(TAG, String.format("[+] onDexModuleRegistered again, path: %s, is_success: %s, msg: %s", dexModulePath, success, message));
                    }
                });
            }
            if (oatFile.exists()) {
                Log.e(TAG, "[+] Bg-dexopt was triggered successfully.");
            } else {
                throw new IllegalStateException("Bg-dexopt was triggered, but no odex file was generated.");
            }
        } catch (Throwable thr) {
            thr.printStackTrace();
        }
    }

    @SuppressLint("PrivateApi")
    private static PackageManager getSynchronizedPackageManager(Context context) throws Throwable {
        synchronized (CACHED_SYNC_PM) {
            if (CACHED_SYNC_PM[0] != null) {
                return CACHED_SYNC_PM[0];
            }
            final Class<?> serviceManagerClazz = Class.forName("android.os.ServiceManager");
            final Method getServiceMethod = UtilKReflect.findMethod(serviceManagerClazz, "getService", String.class);
            final IBinder pmBinder = (IBinder) getServiceMethod.invoke(null, "package");
            final IBinder syncPMBinder = (IBinder) Proxy.newProxyInstance(context.getClassLoader(), pmBinder.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if ("transact".equals(method.getName())) {
                        // FLAG_ONEWAY => NONE.
                        args[3] = 0;
                    }
                    return method.invoke(pmBinder, args);
                }
            });
            final Class<?> pmStubClazz = Class.forName("android.content.pm.IPackageManager$Stub");
            final Method asInterfaceMethod = UtilKReflect.findMethod(pmStubClazz, "asInterface", IBinder.class);
            final IInterface pmItf = (IInterface) asInterfaceMethod.invoke(null, syncPMBinder);
            final Object contextImpl = (context instanceof ContextWrapper ? ((ContextWrapper) context).getBaseContext() : context);
            final Class<?> appPMClazz = Class.forName("android.app.ApplicationPackageManager");
            final Constructor<?> appPMCtor = appPMClazz.getDeclaredConstructor(contextImpl.getClass(), pmItf.getClass().getInterfaces()[0]);
            final PackageManager res = (PackageManager) appPMCtor.newInstance(contextImpl, pmItf);
            CACHED_SYNC_PM[0] = res;
            return res;
        }
    }
}
