package com.retainmvp.retainmvpexample.firstscreen

import android.os.Bundle
import com.retainmvp.retainmvp.StoredStateConverter

/**
 * Created by lukas on 20/04/2018.
 */
private const val COUNTER_VALUE = "COUNTER_VALUE"

class MainActivityStateConverter : StoredStateConverter<MainActivityStoredState> {
    override fun convertToStoredState(bundle: Bundle) = MainActivityStoredState(
        bundle.getInt(COUNTER_VALUE, 0)
    )
}