package com.retainmvp

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment

internal class PresenterHolderFragment<P : Presenter<V, S>, V, S> : Fragment() {
    internal val presenter: P
        get() = nullablePresenter ?: throw PresenterMissingException("Presenter missing")

    private var nullablePresenter: P? = null
    private var view: V? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    internal fun setup(
            presenterFactory: PresenterFactory<V, S, P>,
            view: V,
            storedStateConverter: StoredStateConverter<S>,
            launchingIntent: Intent,
            bundle: Bundle?
    ) {
        nullablePresenter = nullablePresenter ?: presenterFactory.create()
        this.view = view
        presenter.attachStoredState(storedStateFromBundleOrLaunchingIntent(
                storedStateConverter, launchingIntent, bundle
        ))
    }

    private fun storedStateFromBundleOrLaunchingIntent(
            storedStateConverter: StoredStateConverter<S>,
            launchingIntent: Intent,
            bundle: Bundle?
    ): S? = (bundle ?: launchingIntent.extras)?.let(storedStateConverter::convertToStoredState)

    override fun onStart() {
        super.onStart()

        view?.let(presenter::attachView)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView()
    }
}