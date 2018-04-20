package com.retainmvp.retainmvpexample.firstscreen

import android.os.Bundle
import com.retainmvp.retainmvp.StoredStateConverter

private const val COUNTER_VALUE = "COUNTER_VALUE"

class MainActivityStateConverter : StoredStateConverter<MainActivityStoredState> {
    override fun convertToStoredState(bundle: Bundle) = MainActivityStoredState(
            bundle.getInt(COUNTER_VALUE, 0)
    )

    override fun convertStoredStateToBundle(bundle: Bundle, storedState: MainActivityStoredState) =
            bundle.apply { putInt(COUNTER_VALUE, storedState.counterValue) }
}