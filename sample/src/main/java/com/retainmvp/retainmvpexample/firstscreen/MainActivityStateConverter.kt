package com.retainmvp.retainmvpexample.firstscreen

import android.os.Bundle
import com.retainmvp.retainmvp.StoredStateConverter

const val MainActivityBundleCounterValueKey = "MainActivityBundleCounterValueKey"

class MainActivityStateConverter : StoredStateConverter<MainActivityStoredState> {
    override fun convertToStoredState(bundle: Bundle) = MainActivityStoredState(
            bundle.getInt(MainActivityBundleCounterValueKey, 0)
    )

    override fun convertStoredStateToBundle(bundle: Bundle, storedState: MainActivityStoredState) =
            bundle.apply { putInt(MainActivityBundleCounterValueKey, storedState.counterValue) }
}