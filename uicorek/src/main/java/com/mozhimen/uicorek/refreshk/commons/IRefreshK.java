package com.mozhimen.uicorek.refreshk.commons;

public interface IRefreshK {
    /**
     * 刷新时是否禁止滚动
     *
     * @param disableRefreshScroll
     */
    void setDisableRefreshScroll(boolean disableRefreshScroll);

    /**
     * 刷新完成
     */
    void refreshFinished();

    /**
     * 设置下拉刷新的监听器
     *
     * @param refreshListener
     */
    void setRefreshListener(RefreshKListener refreshListener);

    /**
     * 设置下拉刷新的视图
     *
     * @param refreshOverView
     */
    void setRefreshOverView(RefreshKOverView refreshOverView);

    interface RefreshKListener {
        void onRefresh();

        boolean enableRefresh();
    }
}
