import android.os.Bundle
import android.support.v4.app.Fragment

class PresenterHolderFragment<P : Presenter<V, S>, V, S> : Fragment() {
    val presenter: P
        get() = nullablePresenter ?: throw PresenterMissingException("Presenter missing")

    private var nullablePresenter: P? = null
    private var view: V? = null
    private var storedState: S? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    fun setup(presenterFactory: PresenterFactory<V, S, P>, view: V, storedState: S?) {
        nullablePresenter = nullablePresenter ?: presenterFactory.create()
        this.view = view
        this.storedState = storedState ?: presenter.defaultStoredState
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