import android.os.Bundle

/**
 * Created by lukas on 20/04/2018.
 */
interface StoredStateConverter<out StoredState> {
    fun convertToStoredState(bundle: Bundle): StoredState
}