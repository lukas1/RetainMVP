package com.retainmvp.retainmvp

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity

object Presenters {
    private const val TAG_PRESENTER_HOLDER_FRAGMENT = "presenter_holder"

    fun <P : Presenter<V, S>, V, S> getPresenterForActivity(
            activity: AppCompatActivity,
            presenterFactory: PresenterFactory<V, S, P>,
            storedStateConverter: StoredStateConverter<S>,
            view: V,
            launchingIntent: Intent,
            bundle: Bundle?
    ): P = with(getHolderFragment<P, V, S>(activity.supportFragmentManager)) {
        setup(presenterFactory, view, storedStateConverter, launchingIntent, bundle)
        activity.application.registerActivityLifecycleCallbacks(
                RetainMVPLIfeCycleCallbacksListener(activity, storedStateConverter, presenter)
        )
        return@with presenter
    }

    @Suppress("UNCHECKED_CAST")
    private fun <P : Presenter<V, S>, V, S> getHolderFragment(
            fragmentManager: FragmentManager
    ): PresenterHolderFragment<P, V, S> =
            (fragmentManager.findFragmentByTag(TAG_PRESENTER_HOLDER_FRAGMENT) as? PresenterHolderFragment<P, V, S>)
                    ?: PresenterHolderFragment<P, V, S>().apply {
                        fragmentManager
                                .beginTransaction()
                                .add(this, TAG_PRESENTER_HOLDER_FRAGMENT)
                                .commit()
                    }
}