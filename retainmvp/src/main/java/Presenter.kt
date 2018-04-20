package com.retainmvp.retainmvp

abstract class Presenter<View, StoredState> {
    protected var view: View? = null
        private set
    protected var storedState: StoredState = defaultStoredState

    abstract val defaultStoredState: StoredState

    fun attachView(view: View, storedState: StoredState) {
        this.view = view
        this.storedState = storedState
        updateView(view)
    }

    fun detachView() {
        view = null
        onViewDetached()
    }

    fun updateView() {
        view?.let(::updateView)
    }

    protected abstract fun updateView(view: View)

    protected fun onViewDetached() = {}
}