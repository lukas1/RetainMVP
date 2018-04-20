package com.retainmvp.retainmvp

import android.os.Bundle

interface StoredStateConverter<out StoredState> {
    fun convertToStoredState(bundle: Bundle): StoredState
}