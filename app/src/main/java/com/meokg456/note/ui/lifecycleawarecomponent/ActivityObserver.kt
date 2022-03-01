package com.meokg456.note.ui.lifecycleawarecomponent

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class ActivityObserver : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        Log.d("Activity Lifecycle", "Created")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        Log.d("Activity Lifecycle", "Destroyed")
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        Log.d("Activity Lifecycle", "Paused")
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        Log.d("Activity Lifecycle", "Resumed")
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Log.d("Activity Lifecycle", "Started")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Log.d("Activity Lifecycle", "Stopped")
    }
}