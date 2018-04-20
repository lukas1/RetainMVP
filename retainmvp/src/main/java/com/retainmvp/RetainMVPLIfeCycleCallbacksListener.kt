package com.retainmvp

import android.app.Activity
import android.app.Application
import android.os.Bundle

class RetainMVPLIfeCycleCallbacksListener<View, StoredState>(
        private val activity: Activity,
        private val storedStateConverter: StoredStateConverter<StoredState>,
        private val presenter: Presenter<View, StoredState>
) : Application.ActivityLifecycleCallbacks {
    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityResumed(activity: Activity?) {}

    override fun onActivityStarted(activity: Activity?) {}

    override fun onActivityDestroyed(activity: Activity?) {
        if (this.activity == activity) {
            activity.application.unregisterActivityLifecycleCallbacks(this)
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity?, savedInstanceState: Bundle?) {
        if (this.activity == activity) {
            savedInstanceState?.let {
                storedStateConverter.convertStoredStateToBundle(it, presenter.storedState)
            }
        }
    }

    override fun onActivityStopped(activity: Activity?) {}

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {}
}