package com.retainmvp

import android.os.Bundle

interface StoredStateConverter<StoredState> {
    fun convertToStoredState(bundle: Bundle): StoredState
    fun convertStoredStateToBundle(bundle: Bundle, storedState: StoredState): Bundle
}