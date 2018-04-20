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
            bundle: Bundle?
    ): P = getPresenter(
            activity.supportFragmentManager, presenterFactory, storedStateConverter, view, bundle
    )

    private fun <P : Presenter<V, S>, V, S> getHolderFragment(
            fragmentManager: FragmentManager
    ): PresenterHolderFragment<P, V, S> =
            (fragmentManager.findFragmentByTag(
                    TAG_PRESENTER_HOLDER_FRAGMENT
            ) as? PresenterHolderFragment<P, V, S>) ?: PresenterHolderFragment<P, V, S>().apply {
                fragmentManager
                        .beginTransaction()
                        .add(this, TAG_PRESENTER_HOLDER_FRAGMENT)
                        .commit()
            }

    private fun <P : Presenter<V, S>, V, S> getPresenter(
            fragmentManager: FragmentManager,
            presenterFactory: PresenterFactory<V, S, P>,
            storedStateConverter: StoredStateConverter<S>,
            view: V,
            bundle: Bundle?
    ): P = getHolderFragment<P, V, S>(fragmentManager).let {
        it.setup(presenterFactory, view, storedStateConverter, bundle)
        it.presenter
    }
}