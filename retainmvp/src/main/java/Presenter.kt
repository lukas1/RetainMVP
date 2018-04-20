package com.retainmvp.retainmvp

abstract class Presenter<View, StoredState> {
    protected var view: View? = null
        private set
    var storedState: StoredState = defaultStoredState
        protected set

    protected abstract val defaultStoredState: StoredState

    internal fun attachStoredState(storedState: StoredState?) {
        this.storedState = storedState ?: defaultStoredState
    }

    fun attachView(view: View) {
        this.view = view
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