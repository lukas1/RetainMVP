abstract class Presenter<View, StoredState> {
    protected var view: View? = null
        private set
    protected var storedState: StoredState = defaultStoredState
        private set

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

    protected abstract fun updateView(view: View)

    protected fun onViewDetached() = {}
}