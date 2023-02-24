package com.mozhimen.uicorek.popwink.bases.helpers;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mozhimen.basick.utilk.log.UtilKStackTrace;
import com.mozhimen.basick.utilk.log.UtilKLogSmart;
import com.mozhimen.uicorek.popwink.bases.BasePopwinK;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

/**
 * Created by 大灯泡 on 2020/9/9.
 * <p>
 * 这是一个BasePopup的后门，该后门允许你做出对BasePopup不安全的动作，请谨慎使用
 * <p>
 * 该类及其所有方法将会标记为过时，但那只是唬你的哈哈，只是想说尽可能不要用到它
 */
@SuppressLint("All")
@SuppressWarnings("all")
@Deprecated
public enum BasePopupUnsafe {
    @Deprecated
    INSTANCE;

    /**
     * 关闭所有的PopupWindow
     */
    @Deprecated
    public void dismissAllPopup(boolean animateDismiss) {
        HashMap<String, LinkedList<WindowManagerProxy>> allCache = new HashMap<>(WindowManagerProxy.PopupWindowQueueManager.sQueueMap);
        if (!allCache.isEmpty()) {
            for (LinkedList<WindowManagerProxy> value : allCache.values()) {
                for (WindowManagerProxy proxy : value) {
                    if (proxy.mPopupHelper != null && proxy.mPopupHelper.mPopupWindow != null) {
                        proxy.mPopupHelper.mPopupWindow.dismiss(animateDismiss);
                    }
                }
            }
        }
        allCache.clear();
        allCache = null;
    }

    /**
     * 获取显示中Popup的队列
     * 【注意】：该操作会直接返回WindowManagerProxy.PopupWindowQueueManager.sQueueMap，您修改该对象可能会造成不可挽回的bug，请慎重修改。
     */
    @Deprecated
    @Nullable
    public HashMap<String, LinkedList<WindowManagerProxy>> getPopupQueueMap() {
        return WindowManagerProxy.PopupWindowQueueManager.sQueueMap;
    }

    /**
     * 获取WindowManagerProxy中的popupwindow
     */
    @Deprecated
    @Nullable
    public BasePopwinK getPopupFromWindowManagerProxy(WindowManagerProxy proxy) {
        if (proxy == null) return null;
        if (proxy.mPopupHelper != null) {
            return proxy.mPopupHelper.mPopupWindow;
        }
        return null;
    }

    /**
     * 记录堆栈
     */
    @Deprecated
    @Nullable
    public StackDumpInfo dump(BasePopwinK p) {
        if (p == null) return null;
        return StackFetcher.record(p);
    }

    /**
     * 获取用{@link #dump(BasePopwinK)}记录的堆栈信息
     */
    @Deprecated
    @Nullable
    public StackDumpInfo getDump(BasePopwinK p) {
        return StackFetcher.get(p);
    }


    /**
     * 获取BasePopup的WindowManager代理
     */
    @Deprecated
    @Nullable
    public WindowManager getWindowManager(BasePopwinK p) {
        try {
            return Objects.requireNonNull(p.mPopupWindowProxy.getBasePopwinKContextWrapper().getWindowManagerProxy());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取BasePopup最终的DecorView代理
     */
    @Deprecated
    @Nullable
    public View getBasePopupDecorViewProxy(BasePopwinK p) {
        try {
            return Objects.requireNonNull(((WindowManagerProxy) getWindowManager(p)).mPopupDecorViewProxy);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取BasePopup最终DecorView的LayoutParams
     */
    @Deprecated
    @Nullable
    public ViewGroup.LayoutParams getDecorViewLayoutParams(BasePopwinK p) {
        try {
            return getBasePopupDecorViewProxy(p).getLayoutParams();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 设置windowmanagerproxy对WindowManager#LayoutParams处理后的监听，您可以在这里修改LayoutParams，但我并不推荐您这么做
     */
    @Deprecated
    public void setFitWindowManagerLayoutParamsCallback(BasePopwinK p, OnFitWindowManagerLayoutParamsCallback cb) {
        try {
            p.mHelper.mOnFitWindowManagerLayoutParamsCallback = cb;
        } catch (Exception e) {
            UtilKLogSmart.e(e);
        }
    }

    //============================================================= helper
    static class StackFetcher {
        private static final Map<String, StackDumpInfo> STACK_MAP = new HashMap<>();

        private static StackTraceElement getCurrentStackTrace() {
            StackTraceElement[] trace = Thread.currentThread().getStackTrace();
            int stackOffset = UtilKStackTrace.getStackTraceOffset(trace, BasePopupUnsafe.class);
            if (stackOffset == -1) {
                stackOffset = UtilKStackTrace.getStackTraceOffset(trace, StackFetcher.class);
                if (stackOffset == -1) {
                    return null;
                }
            }
            return trace[stackOffset];
        }

        private static StackDumpInfo record(BasePopwinK p) {
            return STACK_MAP.put(key(p), StackDumpInfo.obtain(getCurrentStackTrace()));
        }

        static void remove(BasePopwinK p) {
            StackDumpInfo.CACHE = STACK_MAP.remove(key(p));
        }

        private static StackDumpInfo get(BasePopwinK p) {
            String key = key(p);
            StackDumpInfo info = STACK_MAP.get(key(p));
            if (!TextUtils.isEmpty(key) && info != null) {
                String[] msg = key.split("@");
                if (msg.length == 2) {
                    info.popupClassName = msg[0];
                    info.popupAddress = msg[1];
                }
            }
            return info;
        }

        private static String key(BasePopwinK p) {
            return String.valueOf(p);
        }
    }

    //============================================================= class
    public static class StackDumpInfo {
        static volatile StackDumpInfo CACHE = null;
        public String className;
        public String methodName;
        public String lineNum;
        public String popupClassName;
        public String popupAddress;

        public StackDumpInfo(StackTraceElement element) {
            record(element);
        }

        void record(StackTraceElement element) {
            if (element != null) {
                className = element.getFileName();
                methodName = element.getMethodName();
                lineNum = String.valueOf(element.getLineNumber());
            }
            popupClassName = null;
            popupAddress = null;
        }

        static StackDumpInfo obtain(StackTraceElement element) {
            if (CACHE != null) {
                CACHE.record(element);
                return CACHE;
            }
            return new StackDumpInfo(element);
        }

        @Override
        public String toString() {
            return "StackDumpInfo{" +
                    "className='" + className + '\'' +
                    ", methodName='" + methodName + '\'' +
                    ", lineNum='" + lineNum + '\'' +
                    ", popupClassName='" + popupClassName + '\'' +
                    ", popupAddress='" + popupAddress + '\'' +
                    '}';
        }
    }

    @Deprecated
    public interface OnFitWindowManagerLayoutParamsCallback {
        void onFitLayoutParams(@NonNull WindowManager.LayoutParams p);
    }
}
