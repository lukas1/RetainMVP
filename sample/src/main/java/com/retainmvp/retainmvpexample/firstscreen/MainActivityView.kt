package com.retainmvp.retainmvpexample.firstscreen

interface MainActivityView {
    fun setCounterValue(counterValue: Int)
    fun startNewActivity(counterValue: Int)
}