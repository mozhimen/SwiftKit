package com.swiftmk.library.helper

import android.app.Activity

object ActivityCollector {
    private val activities=ArrayList<Activity>()

    fun addActivity(activity: Activity){
        activities.add(activity)
    }

    fun removeActivity(activity: Activity){
        activities.remove(activity)
    }

    /**
     * 销毁所有的Activity
     * 进程销毁:android.os.Process.killProcess(android.os.Process.myPid())
     */
    fun finishAll(){
        for (activity in activities){
            if(!activity.isFinishing){
                activity.finish()
            }
        }
        activities.clear()
    }
}