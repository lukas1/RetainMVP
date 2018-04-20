package com.retainmvp

interface PresenterFactory<View, StoredState, out P : Presenter<View, StoredState>> {
    fun create(): P
}