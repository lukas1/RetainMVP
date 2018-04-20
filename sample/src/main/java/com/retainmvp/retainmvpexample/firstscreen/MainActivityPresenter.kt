package com.retainmvp.retainmvpexample.firstscreen

import com.retainmvp.retainmvp.Presenter
import com.retainmvp.retainmvp.PresenterFactory

class MainActivityPresenter : Presenter<MainActivityView, MainActivityStoredState>() {
    companion object {
        val factory = object : PresenterFactory<MainActivityView, MainActivityStoredState, MainActivityPresenter> {
            override fun create() = MainActivityPresenter()
        }
    }

    override val defaultStoredState: MainActivityStoredState
        get() = MainActivityStoredState()

    override fun updateView(view: MainActivityView) {
        view.setCounterValue(storedState.counterValue)
    }

    fun onBumpCounterButtonPressed() {
        storedState = storedState.copy(counterValue = storedState.counterValue + 1)
        updateView()
    }
}