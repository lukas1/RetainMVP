package com.retainmvp.retainmvp

import android.os.Bundle
import android.support.v4.app.Fragment

internal class PresenterHolderFragment<P : Presenter<V, S>, V, S> : Fragment() {
    internal val presenter: P
        get() = nullablePresenter ?: throw PresenterMissingException("Presenter missing")

    private var nullablePresenter: P? = null
    private var view: V? = null
    private var storedState: S? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    internal fun setup(
            presenterFactory: PresenterFactory<V, S, P>,
            view: V,
            storedStateConverter: StoredStateConverter<S>,
            bundle: Bundle?
    ) {
        nullablePresenter = nullablePresenter ?: presenterFactory.create()
        this.view = view
        this.storedState = bundle?.let(storedStateConverter::convertToStoredState) ?: presenter.defaultStoredState
    }

    override fun onStart() {
        super.onStart()

        view?.let { view ->
            storedState?.let { storedState ->
                presenter.attachView(view, storedState)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView()
    }
}